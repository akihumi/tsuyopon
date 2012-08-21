
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// モンスターを保存するためのアクティビティ
public class SaveMonster extends Activity {

    private ImageView save_image_view;
    private TextView save_strength_view, save_quickness_view, save_power_view,
            save_stomach_view, save_kind_view, save_luck_view, save_life_view,
            save_text_view;
    private Button data1, data2, data3, data4, data5,
            data6, data7, data8, data9, data10;
    private Monster tsuyopon;
    private byte[] blob;
    private TypedArray num;
    private ArrayList<String> id_list;

    private ArrayList<Integer> databutton_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);

        save_image_view = (ImageView) findViewById(R.id.save_image_view);
        save_text_view = (TextView) findViewById(R.id.save_text_view);
        save_strength_view = (TextView) findViewById(R.id.save_hp_view);
        save_quickness_view = (TextView) findViewById(R.id.save_spd_view);
        save_power_view = (TextView) findViewById(R.id.save_atk_view);
        save_kind_view = (TextView) findViewById(R.id.save_kind_view);
        save_stomach_view = (TextView) findViewById(R.id.save_satomach_view);
        save_luck_view = (TextView) findViewById(R.id.save_luck_view);
        save_life_view = (TextView) findViewById(R.id.save_age_view);
        data1 = (Button) findViewById(R.id.data1);
        data2 = (Button) findViewById(R.id.data2);
        data3 = (Button) findViewById(R.id.data3);
        data4 = (Button) findViewById(R.id.data4);
        data5 = (Button) findViewById(R.id.data5);
        data6 = (Button) findViewById(R.id.data6);
        data7 = (Button) findViewById(R.id.data7);
        data8 = (Button) findViewById(R.id.data8);
        data9 = (Button) findViewById(R.id.data9);
        data10 = (Button) findViewById(R.id.data10);
        // インテントのデータを取得
        tsuyopon = (Monster) getIntent().getSerializableExtra("monster");
        HashMap<String, Integer> status = tsuyopon.getStatusList();
        //                Bundle extras = getIntent().getExtras();
        //                blob = extras.getByteArray("image");
        //                  ビットマップに変換
        //                Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        TypedArray images = getResources().obtainTypedArray(R.array.monster_image);
        Drawable draw = images.getDrawable(getIntent().getExtras().getInt("image"));
        save_image_view.setImageDrawable(draw);
        save_strength_view.setText(status.get(Monster.STATUS_STRENGTH).toString());
        save_power_view.setText(status.get(Monster.STATUS_POWER).toString());
        save_quickness_view.setText(status.get(Monster.STATUS_QUICKNESS).toString());
        save_kind_view.setText(status.get(Monster.STATUS_KIND).toString());
        save_stomach_view.setText(status.get(Monster.STATUS_STOMACH_GAUGE).toString());
        save_luck_view.setText(status.get(Monster.STATUS_LUCK).toString());
        save_life_view.setText(status.get(Monster.STATUS_LIFE).toString());
        save_text_view.setText(R.string.save_text);

        // 保存しているモンスターの名前を表示
        id_list = DBoperation.getId(getApplicationContext());

        num = getResources().obtainTypedArray(R.array.data);
        databutton_index = new ArrayList<Integer>();
        int i = 0;
        for (String s : id_list) {
            databutton_index.add(num.getResourceId(i, 0));
            Monster mon = DBoperation.getMonster(getApplicationContext(), s);
            Button saved_button = (Button) findViewById(num.getResourceId(i++, 0));
            saved_button.setText(mon.getName());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.alert_title)
                    .setMessage(R.string.alert_message)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), Title.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
        final int index = databutton_index.indexOf(v.getId());
        Button button = (Button) findViewById(v.getId());
        String no_data = getResources().getText(R.string.no_data).toString();
        if (!button.getText().toString().equals(no_data)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.alert_title)
                    .setMessage(R.string.rewrite)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // データの保存されてるボタンを押したら上書きされたようにみせる
                                    DBoperation.delete(getApplicationContext(), id_list.get(index));
                                    DBoperation.insert(getApplicationContext(),
                                            getIntent().getExtras().getInt("image"),
                                            tsuyopon);
                                    Toast.makeText(getApplicationContext(),
                                            R.string.save_toast_text,
                                            Toast.LENGTH_SHORT)
                                            .show();
                                    Intent i = new Intent(getApplicationContext(), Title.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
        } else {
            DBoperation.insert(getApplicationContext(),
                    getIntent().getExtras().getInt("image"),
                    tsuyopon);
            Toast.makeText(getApplicationContext(),
                    R.string.save_toast_text,
                    Toast.LENGTH_SHORT)
                    .show();
            Intent i = new Intent(getApplicationContext(), Title.class);
            startActivity(i);
            finish();
        }
    }
}

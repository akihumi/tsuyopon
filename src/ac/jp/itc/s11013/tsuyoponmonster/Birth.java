
package ac.jp.itc.s11013.tsuyoponmonster;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Birth extends Activity {
    private Button input_name;
    private ImageView monster_image;
    private TextView birth_text;
    private Drawable draw;
    private LayoutInflater inflater;
    private View view;
    private Monster tsuyopon;
    private String monster_name;
    // 同じダイアログは2回作れないっぽいのでオブジェクト保持します
    private AlertDialog name_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birth);

        // ダイアログ表示用のxmlからIDを取得するための変数
        inflater = LayoutInflater.from(this.getBaseContext());
        view = inflater.inflate(R.layout.birth_alert, null);
        input_name = (Button) findViewById(R.id.birth_monstername);
        monster_image = (ImageView) findViewById(R.id.birth_imageView);
        birth_text = (TextView) findViewById(R.id.dummy_birth_text_view);
        // 画像をランダムで取得
        TypedArray images = getResources().obtainTypedArray(R.array.monster_image);
        Random rand = new Random();
        draw = images.getDrawable(rand.nextInt(14));
        monster_image.setImageDrawable(draw);

        birth_text.setText("モンスターが生まれました。 名前をつけてくだしあ");
        // 名前を設定するダイアログを作っておく
        name_alert = new AlertDialog.Builder(this)
                .setTitle("名前を入力してね")
                .setMessage("名前は最大5文字です")
                .setView(view)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        EditText edit = (EditText) view
                                .findViewById(R.id.birth_alert_edittext);
                        input_name.setText(edit.getText().toString());
                    }
                }).create();
        name_alert.setCancelable(true);

    }

    //ボタンを押した時の処理
    public void click(View v) {
        switch (v.getId()) {
            case R.id.birth_monstername:
                name_alert.show();
                break;
            case R.id.birth_yes:
                monster_name = input_name.getText().toString();
                tsuyopon = new Monster(monster_name);
                Intent i = new Intent(Birth.this, BringUp.class);
                i.putExtra("monster", tsuyopon);

                // drawをいったんBitmapに変換,つかわない
                Bitmap bitmap = ((BitmapDrawable) draw).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                i.putExtra("image", b);
                // BringUpアクティビティに放り投げる
                startActivity(i);
                break;
        }
    }
}

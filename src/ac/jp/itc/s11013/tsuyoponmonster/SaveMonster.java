
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveMonster extends Activity {

    private ImageView save_image_view;
    private TextView save_strength_view, save_quickness_view, save_power_view,
            save_stomach_view, save_kind_view, save_luck_view, save_life_view,
            save_text_view;
    private Button data1, data2, data3, data4, data5,
            data6, data7, data8, data9, data10;
    private Monster tsuyopon;

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
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        tsuyopon = (Monster) getIntent().getSerializableExtra("monster");
        HashMap<String, Integer> status = tsuyopon.getStatusList();
        save_image_view.setImageBitmap(bmp);
        save_strength_view.setText(status.get(Monster.STATUS_STRENGTH).toString());
        save_power_view.setText(status.get(Monster.STATUS_POWER).toString());
        save_quickness_view.setText(status.get(Monster.STATUS_QUICKNESS).toString());
        save_kind_view.setText(status.get(Monster.STATUS_KIND).toString());
        save_stomach_view.setText(status.get(Monster.STATUS_STOMACH_GAUGE).toString());
        save_luck_view.setText(status.get(Monster.STATUS_LUCK).toString());
        save_life_view.setText(status.get(Monster.STATUS_LIFE));
        

    }
    public void onClick(View v){
        
    }

}

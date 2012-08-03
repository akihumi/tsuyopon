package ac.jp.itc.s11013.tsuyoponmonster;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Dead extends Activity {

    private Monster tsuyopon;
    private Bitmap bmp;
    private Button dead_yes, dead_no;
    private TextView dead_text;
    private ImageView dead_image;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dead);
        // 初期化
        dead_yes = (Button) findViewById(R.id.dead_yes);
        dead_no = (Button) findViewById(R.id.dead_no);
        dead_text = (TextView) findViewById(R.id.dead_textview);
        dead_image = (ImageView) findViewById(R.id.dead_monster_image);
        // インテントのデータを取得
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("image");
        bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        tsuyopon = (Monster) getIntent().getSerializableExtra("monster");

        dead_image.setImageBitmap(bmp);
        dead_text.setText(String.format("%sはお腹が空きすぎて逃げ出しました...\n%<sのデータを保存しますか？", tsuyopon.getName()));
        if(tsuyopon.getStatusList().get(Monster.STATUS_LIFE) >= Monster.STATUS_MAX){
            dead_text.setText(String.format("%sは死にました...\n%<sのデータを保存しますか？", tsuyopon.getName()));
        }
    }
    public void click(View v){
        switch(v.getId()){
            case R.id.dead_yes:
                break;
            case R.id.dead_no:
                break;
    }
    

}
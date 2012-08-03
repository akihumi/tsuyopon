
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Title extends Activity {

    private Button toStart, toContinue, toBattle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        Random rand = new Random();
        // array.xmlからタイトルに表示する画像をランダムで取ってくる
        TypedArray images = getResources().obtainTypedArray(R.array.monster_image);
        Drawable draw = images.getDrawable(rand.nextInt(14));
        ImageView image = (ImageView) findViewById(R.id.titleMonsterIcon);
        image.setImageDrawable(draw);
        toStart = (Button) findViewById(R.id.toStart);
        toContinue = (Button) findViewById(R.id.toContinue);
        toBattle = (Button) findViewById(R.id.toBattle);
    }

    // 押されたボタンによって飛ばす場所をかえる
    public void click(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.toStart:
                i = new Intent(this, Birth.class);
                startActivity(i);
                break;
            case R.id.toContinue:
                i = new Intent(this, BringUp.class);
                startActivity(i);
                break;
            case R.id.toBattle:
                i = new Intent(this, PreparationForBattle.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.title, menu);
        return true;
    }

}

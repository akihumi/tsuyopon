
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Title extends Activity {

    private boolean flag;
    private Button toStart, toContinue, toBattle;
    public static final String PREF_KEY = "preferences_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        flag = true;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (flag) {
                Toast.makeText(getApplicationContext(), R.string.exit_app, Toast.LENGTH_SHORT)
                        .show();
                flag = false;
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    // 押されたボタンによって飛ばす場所をかえる
    public void click(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.toStart:
                i = new Intent(this, BirthAnime.class);
                startActivity(i);
                finish();
                break;
            case R.id.toContinue:
                SharedPreferences pref = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
                if(pref.getString("id", "0").equals("0")){
                    Toast.makeText(getApplicationContext(), R.string.no_continue, Toast.LENGTH_SHORT).show();
                    break;
                }
                Monster tsuyopon = new Monster(pref.getString("id", "0"), pref.getString("name", "hoge"),
                        pref.getInt(Monster.STATUS_STRENGTH, 0), pref.getInt(Monster.STATUS_POWER, 0),
                        pref.getInt(Monster.STATUS_QUICKNESS, 0), pref.getInt(Monster.STATUS_KIND, 0),
                        pref.getInt(Monster.STATUS_STOMACH_GAUGE, 0), pref.getInt(Monster.STATUS_LUCK, 0),
                        pref.getInt(Monster.STATUS_LIFE, 0));
                i = new Intent(this, BringUp.class);
                i.putExtra("monster", tsuyopon);
                i.putExtra("image", pref.getInt("image", 0));
                
                startActivity(i);
                finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_end:
                finish();
                break;
            case R.id.menu_monster:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

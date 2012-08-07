
package ac.jp.itc.s11013.tsuyoponmonster;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BringUp extends Activity {

    private Monster tsuyopon;
    private HashMap<String, Integer> status;
    private Button bring_running, bring_weight_training, bring_doping,
            bring_praise, bring_meal, bring_sleeping;
    private ImageView bring_monster_image;
    private String monster_name;
    private TextView strength, power, quickness, kind, stomach_gauge, luck, life,
            strength_change, power_change, quickness_change, kind_change,
            stomach_gauge_change, luck_change, life_change, bring_textview,
            bring_monster_name;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bring_up);
        // 画像を取得
        Bundle extras = getIntent().getExtras();
        byte[] b = extras.getByteArray("image");

        bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
        // モンスタークラスのインスタンス
        tsuyopon = (Monster) getIntent().getSerializableExtra("monster");
        // モンスターの名前と画像を設定
        bring_monster_image = (ImageView) findViewById(R.id.bring_monster_image);
        bring_monster_name = (TextView) findViewById(R.id.bring_monster_name);
        // ボタンの設定
        bring_running = (Button) findViewById(R.id.bring_running);
        bring_weight_training = (Button) findViewById(R.id.bring_weight_training);
        bring_doping = (Button) findViewById(R.id.bring_doping);
        bring_praise = (Button) findViewById(R.id.bring_praise);
        bring_meal = (Button) findViewById(R.id.bring_meal);
        bring_sleeping = (Button) findViewById(R.id.bring_sleeping);
        // ステータス表示の設定
        strength = (TextView) findViewById(R.id.strength);
        power = (TextView) findViewById(R.id.power);
        quickness = (TextView) findViewById(R.id.quickness);
        kind = (TextView) findViewById(R.id.kind);
        stomach_gauge = (TextView) findViewById(R.id.stomach_gauge);
        luck = (TextView) findViewById(R.id.luck);
        life = (TextView) findViewById(R.id.life);
        // ステータスの変化値をだすやつ
        strength_change = (TextView) findViewById(R.id.strength_change);
        power_change = (TextView) findViewById(R.id.power_change);
        quickness_change = (TextView) findViewById(R.id.quickness_change);
        kind_change = (TextView) findViewById(R.id.kind_change);
        luck_change = (TextView) findViewById(R.id.luck_change);
        stomach_gauge_change = (TextView) findViewById(R.id.stomach_gauge_change);
        // いらなかった
        //        luck_change = (TextView) findViewById(R.id.luck_change);
        life_change = (TextView) findViewById(R.id.life_change);
        // メッセージ出す奴
        bring_textview = (TextView) findViewById(R.id.bring_textview);
        // モンスターの画像と名前を入れる
        //        bring_monster_image.setImageDrawable(tsuyopon.getImage());
        bring_monster_image.setImageBitmap(bmp);
        bring_monster_name.setText(new StringBuilder("もんすたー名: ")
                .append(tsuyopon.getName()).toString());
        // tsuyoponからステータス表示の数値を取得
        status = tsuyopon.getStatusList();
        updateStatus(status);
        changeViewReset();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.alert_title)
                    .setMessage(R.string.save_and_title)
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), Title.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    // ボタンが押された時の処理
    public void bringClickButton(View v) {
        HashMap<String, Integer> changed = null;
        StringBuilder message = new StringBuilder(tsuyopon.getName());
        // 変化前のステータスを取得
        status = tsuyopon.getStatusList();
        switch (v.getId()) {
            case R.id.bring_running:
                changeViewReset();
                tsuyopon.running();
                // ステータスの変化と変化量を再描画
                quickness_change.setTextColor(Color.MAGENTA);
                strength_change.setTextColor(Color.CYAN);
                stomach_gauge_change.setTextColor(Color.CYAN);
                changeStatus(changed, status);
                bring_textview.setText(message.append("を走らせた！").toString());
                break;
            case R.id.bring_weight_training:
                changeViewReset();
                tsuyopon.weightTraining();
                power_change.setTextColor(Color.MAGENTA);
                quickness_change.setTextColor(Color.CYAN);
                stomach_gauge_change.setTextColor(Color.CYAN);
                changeStatus(changed, status);
                bring_textview.setText(message.append("を筋トレをさせた！").toString());
                break;
            case R.id.bring_sleeping:
                changeViewReset();
                tsuyopon.sleeping();
                strength_change.setTextColor(Color.MAGENTA);
                power_change.setTextColor(Color.CYAN);
                stomach_gauge_change.setTextColor(Color.CYAN);
                changeStatus(changed, status);
                bring_textview.setText(message.append("を眠らせた！").toString());
                break;
            case R.id.bring_doping:
                changeViewReset();
                tsuyopon.doping();
                strength_change.setTextColor(Color.MAGENTA);
                power_change.setTextColor(Color.MAGENTA);
                quickness_change.setTextColor(Color.MAGENTA);
                kind_change.setTextColor(Color.MAGENTA);
                stomach_gauge_change.setTextColor(Color.MAGENTA);
                changeStatus(changed, status);
                bring_textview.setText(message.append("にドーピングを使用した！").toString());
                break;
            case R.id.bring_praise:
                changeViewReset();
                tsuyopon.praise();
                kind_change.setTextColor(Color.MAGENTA);
                stomach_gauge_change.setTextColor(Color.CYAN);
                changeStatus(changed, status);
                bring_textview.setText(message.append("をほめた！").toString());
                break;
            case R.id.bring_meal:
                changeViewReset();
                tsuyopon.meal();
                stomach_gauge_change.setTextColor(Color.MAGENTA);
                kind_change.setTextColor(Color.MAGENTA);
                changeStatus(changed, status);
                bring_textview.setText(message.append("にエサをあげた！").toString());
                break;
        }
        if (tsuyopon.isDeath()) {
            Intent i = new Intent(this, Dead.class);
            // バイト配列に変換する
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            i.putExtra("image", b);
            i.putExtra("monster", tsuyopon);
            startActivity(i);
            finish();
        }
    }

    // とりあえず全部更新
    private void changeStatus(HashMap<String, Integer> changed, HashMap<String, Integer> status) {
        // 変化後のステータスを取得
        changed = tsuyopon.getStatusList();
        // ステータスの表示
        updateStatus(changed);
        // 符号付きでステータス変化量を表示, 変化がなければなにも表示しない
        strength_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_STRENGTH)
                        - status.get(Monster.STATUS_STRENGTH))
                .toString().replace("+0", " "));

        power_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_POWER)
                        - status.get(Monster.STATUS_POWER))
                .toString().replace("+0", " "));

        quickness_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_QUICKNESS)
                        - status.get(Monster.STATUS_QUICKNESS))
                .toString().replace("+0", " "));

        kind_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_KIND)
                        - status.get(Monster.STATUS_KIND))
                .toString().replace("+0", " "));

        stomach_gauge_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_STOMACH_GAUGE)
                        - status.get(Monster.STATUS_STOMACH_GAUGE))
                .toString().replace("+0", " "));

        luck_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_LUCK)
                        - status.get(Monster.STATUS_LUCK)).toString().replace("+0", " "));

        life_change.setText(String.format("%1$+d",
                changed.get(Monster.STATUS_LIFE)
                        - status.get(Monster.STATUS_LIFE))
                .toString().replace("+0", " "));

    }

    private void changeViewReset() {
        strength_change.setText("  ");
        power_change.setText("  ");
        quickness_change.setText("  ");
        kind_change.setText("  ");
        stomach_gauge_change.setText("  ");
        life_change.setText("  ");
        luck_change.setText("  ");
    }

    private void updateStatus(HashMap<String, Integer> changed) {
        strength.setText(String.format("%3d", changed.get(Monster.STATUS_STRENGTH)).toString());
        power.setText(String.format("%3d", changed.get(Monster.STATUS_POWER)).toString());
        quickness.setText(String.format("%3d", changed.get(Monster.STATUS_QUICKNESS)).toString());
        kind.setText(String.format("%3d", changed.get(Monster.STATUS_KIND)).toString());
        stomach_gauge.setText(String.format("%3d", changed.get(Monster.STATUS_STOMACH_GAUGE))
                .toString());
        life.setText(String.format("%3d", changed.get(Monster.STATUS_LIFE)).toString());
        luck.setText(String.format("%3d", changed.get(Monster.STATUS_LUCK)).toString());

    }

}


package ac.jp.itc.s11013.tsuyoponmonster;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import android.graphics.drawable.Drawable;

public class Monster implements Serializable{
    /**
     * しりあるばーじょんうーあいでぃー
     */
    private static final long serialVersionUID = -7112787500781858079L;
    public static final String STATUS_STRENGTH = "strength",
            STATUS_POWER = "power",
            STATUS_QUICKNESS = "quickness",
            STATUS_KIND = "kind",
            STATUS_STOMACH_GAUGE = "stomach_gauge",
            STATUS_LUCK = "luck",
            STATUS_LIFE = "life";
    public static final int STATUS_MAX = 100, STATUS_MIN = 0;
    private int strength, power, quickness, kind,
            stomach_gauge, luck, life;
    private Random rand;
    private String monster_name;
//    private Drawable monster_image;
    private boolean death_flag;

    public Monster(String name) {
        // 名前が入力されていなければ初期化。。。つかうかどうかわからない
                if (name.equals("") || name == null) {
                    name = "ななしポン";
                }
        death_flag = false;
        monster_name = name;
        rand = new Random();
        // 攻撃、すばやさ、HPは20~29の間で初期化する。運は1~100の間でする
        strength = rand.nextInt(10) + 20;
        power = rand.nextInt(10) + 20;
        quickness = rand.nextInt(10) + 20;
        luck = rand.nextInt(100) + 1;
        // なつき度=>30, まんぷく度=>50,じゅみょう=>0で初期は固定
        life = 0;
        kind = 30;
        stomach_gauge = 50;
        // モンスターの画像を決定する
//        monster_image = image;

    }

    public HashMap<String, Integer> getStatusList() {
        HashMap<String, Integer> status = new HashMap<String, Integer>();
        status.put(STATUS_STRENGTH, strength);
        status.put(STATUS_POWER, power);
        status.put(STATUS_QUICKNESS, quickness);
        status.put(STATUS_KIND, kind);
        status.put(STATUS_STOMACH_GAUGE, stomach_gauge);
        status.put(STATUS_LUCK, luck);
        status.put(STATUS_LIFE, life);
        return status;
    }

    public String getName() {
        return monster_name;
    }

//    public Drawable getImage() {
//        return monster_image;
//    }

    public void running() {
        quickness = (quickness + 5 >= STATUS_MAX) ? STATUS_MAX : quickness + 5;
        strength = (strength - 2 <= STATUS_MIN) ? STATUS_MIN : strength - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge + 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    public void weightTraining() {
        power = (power + 5 >= STATUS_MAX) ? STATUS_MAX : power + 5;
        quickness = (quickness + 2 <= STATUS_MIN) ? STATUS_MIN : quickness - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge + 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    public void sleeping() {
        strength = (strength + 5 >= STATUS_MAX) ? STATUS_MAX : strength + 5;
        power = (power - 2 <= STATUS_MIN) ? STATUS_MIN : power - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge + 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    public void meal() {
        stomach_gauge = (stomach_gauge + 20 >= STATUS_MAX) ? STATUS_MAX : stomach_gauge + 20;
        kind = (kind + 5 >= STATUS_MAX) ? STATUS_MAX : kind + 5;
        life = (life + 5 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    public void praise() {
        kind = (kind + 20 >= STATUS_MAX) ? STATUS_MAX : kind + 20;
        stomach_gauge = (stomach_gauge + 5 <= STATUS_MIN) ? STATUS_MIN :stomach_gauge - 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    public void doping() {
        switch (rand.nextInt(5)) {
            case 0:
                strength = (strength + 20 >= STATUS_MAX) ? STATUS_MAX : strength + 20;
                break;
            case 1:
                quickness = (quickness + 20 >= STATUS_MAX) ? STATUS_MAX : quickness + 20;
                break;
            case 2:
                power = (power + 20 >= STATUS_MAX) ? STATUS_MAX : power + 20;
                break;
            case 3:
                kind = (kind + 20 >= STATUS_MAX) ? STATUS_MAX : kind + 20;
                break;
            case 4:
                stomach_gauge = (stomach_gauge + 20 >= STATUS_MAX) ? STATUS_MAX
                        : stomach_gauge + 20;
                break;
        }
        int diff = rand.nextInt(16) + 5;
        life = (life + diff >= STATUS_MAX) ? STATUS_MAX : life + diff;
        setDeathFlag();
    }

    public boolean isDeath() {
        return death_flag;
    }

    private void setDeathFlag() {
        if (life >= STATUS_MAX || stomach_gauge <= STATUS_MIN) {
            death_flag = true;
        }
    }

}

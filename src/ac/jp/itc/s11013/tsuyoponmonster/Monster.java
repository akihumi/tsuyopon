
package ac.jp.itc.s11013.tsuyoponmonster;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

// モンスターネームとステータスを保持するクラス.
// intentにputExtraするためSerializableクラスを実装してる
public class Monster implements Serializable {
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
    private String id;

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
        // なつき度=>10, まんぷく度=>50,じゅみょう=>0で初期は固定
        life = 0;
        kind = 10;
        stomach_gauge = 50;
        int tmp = strength + power + quickness + luck + rand.nextInt(1000);
        id = getHash(new StringBuilder(name).append(tmp).toString());
    }
    // 
    public Monster(String id, String name, int strength, int power, int quickness,
            int kind, int stomach_gauge, int luck, int life){
        this.id = id;
        this.monster_name = name;
        this.strength = strength;
        this.power = power;
        this.quickness = quickness;
        this.kind = kind;
        this.stomach_gauge = stomach_gauge;
        this.luck = luck;
        this.life = life;
        death_flag = false;
        rand = new Random();
    }

    // ステータスをHashmapで返す
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

    public String getId() {
        return id;
    }

    // 走る（速＋５、体ー１〜２, なつき - 2, 満腹度−５, 寿命＋2）
    public void running() {
        quickness = (quickness + 5 >= STATUS_MAX) ? STATUS_MAX : quickness + 5;
        strength = (strength - 2 <= STATUS_MIN) ? STATUS_MIN : strength - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge - 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        kind = (kind - 2 <= STATUS_MIN) ? STATUS_MIN : kind - 2;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    // 筋トレ（攻＋５、速ー１〜２, なつき - 2, 満腹度−５, 寿命＋2）
    public void weightTraining() {
        power = (power + 5 >= STATUS_MAX) ? STATUS_MAX : power + 5;
        quickness = (quickness + 2 <= STATUS_MIN) ? STATUS_MIN : quickness - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge - 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        kind = (kind - 2 <= STATUS_MIN) ? STATUS_MIN : kind - 2;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    // 寝る（体＋５、攻ー１〜２, なつき - 2, 満腹度−５, 寿命＋2）
    public void sleeping() {
        strength = (strength + 5 >= STATUS_MAX) ? STATUS_MAX : strength + 5;
        power = (power - 2 <= STATUS_MIN) ? STATUS_MIN : power - (rand.nextInt(2) + 1);
        stomach_gauge = (stomach_gauge - 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        kind = (kind - 2 <= STATUS_MIN) ? STATUS_MIN : kind - 2;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    // 食事（満腹度＋２５、なつき度＋５, 寿命＋2）
    public void meal() {
        stomach_gauge = (stomach_gauge + 25 >= STATUS_MAX) ? STATUS_MAX : stomach_gauge + 25;
        kind = (kind + 5 >= STATUS_MAX) ? STATUS_MAX : kind + 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    // 褒める（なつき度＋２０, 満腹度−５, 寿命＋2）
    public void praise() {
        kind = (kind + 20 >= STATUS_MAX) ? STATUS_MAX : kind + 20;
        stomach_gauge = (stomach_gauge - 5 <= STATUS_MIN) ? STATUS_MIN : stomach_gauge - 5;
        life = (life + 2 >= STATUS_MAX) ? STATUS_MAX : life + 2;
        setDeathFlag();
    }

    // ドーピング（ランダム(攻 | 体 | 速 | 運)＋２０、寿命＋５〜２０）
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

    // idを作るメソッド
    private String getHash(String org) {
        // 引数・アルゴリズム指定が無い場合は計算しない
        if (org == null) {
            return null;
        }
        // 初期化
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.reset();
        md.update(org.getBytes());
        byte[] hash = md.digest();
        // ハッシュを16進数文字列に変換
        StringBuffer sb = new StringBuffer();
        int cnt = hash.length;
        for (int i = 0; i < cnt; i++) {
            sb.append(Integer.toHexString((hash[i] >> 4) & 0x0F));
            sb.append(Integer.toHexString(hash[i] & 0x0F));
        }
        return sb.toString();
    }

}

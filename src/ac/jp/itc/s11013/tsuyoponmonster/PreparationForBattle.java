
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class PreparationForBattle extends Activity {

    private ImageView monster_image;
    private TextView pfb_strength_view, pfb_quickness_view, pfb_power_view,
            pfb_stomach_view, pfb_kind_view, pfb_luck_view, pfb_life_view;
    private Spinner select_monster;
    private Monster[] list;
    private TypedArray images;
    private ArrayList<String> id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparationforbattle);
        select_monster = (Spinner) findViewById(R.id.monsterselect1);
        monster_image = (ImageView) findViewById(R.id.mpicture);
        pfb_strength_view = (TextView) findViewById(R.id.pfb_hp_view);
        pfb_quickness_view = (TextView) findViewById(R.id.pfb_spd_view);
        pfb_power_view = (TextView) findViewById(R.id.pfb_atk_view);
        pfb_kind_view = (TextView) findViewById(R.id.pfb_kind_view);
        pfb_stomach_view = (TextView) findViewById(R.id.pfb_satomach_view);
        pfb_luck_view = (TextView) findViewById(R.id.pfb_luck_view);
        pfb_life_view = (TextView) findViewById(R.id.pfb_age_view);

        images = getResources().obtainTypedArray(R.array.monster_image);

        id_list = DBoperation.getId(getApplicationContext());
        list = new Monster[id_list.size()];
        String[] monsterlist = new String[id_list.size()];
        int i = 0;
        for (String s : id_list) {
            list[i] = DBoperation.getMonster(getApplicationContext(), s);
            monsterlist[i] = list[i++].getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, monsterlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_monster.setAdapter(adapter);
        select_monster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                HashMap<String, Integer> status = list[position].getStatusList();
                Drawable draw = images.getDrawable(
                        DBoperation.getImage(
                                getApplicationContext(), id_list.get(position)));
                monster_image.setImageDrawable(draw);
                pfb_strength_view.setText(status.get(Monster.STATUS_STRENGTH).toString());
                pfb_power_view.setText(status.get(Monster.STATUS_POWER).toString());
                pfb_quickness_view.setText(status.get(Monster.STATUS_QUICKNESS).toString());
                pfb_kind_view.setText(status.get(Monster.STATUS_KIND).toString());
                pfb_stomach_view.setText(status.get(Monster.STATUS_STOMACH_GAUGE).toString());
                pfb_luck_view.setText(status.get(Monster.STATUS_LUCK).toString());
                pfb_life_view.setText(status.get(Monster.STATUS_LIFE).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}

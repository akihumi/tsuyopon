
package ac.jp.itc.s11013.tsuyoponmonster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PreparationForBattle extends Activity {

    private static final int REQUEST_ENABLE_BT = 0;
    private ImageView monster_image;
    private TextView pfb_strength_view, pfb_quickness_view, pfb_power_view,
            pfb_stomach_view, pfb_kind_view, pfb_luck_view, pfb_life_view,
            address_disp;
    private Spinner select_monster, paired_device;
    private ArrayList<Monster> monster_list;
    private TypedArray images;
    private ArrayList<String> id_list;
    private Button register_button, socket_button, server_button;
    private BluetoothAdapter bluetooth_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparationforbattle);
        // Bluetoothが使える端末か調べる。
        bluetooth_adapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetooth_adapter == null){
            // トーストでBluetoothが使えないことを表示した後、タイトルに戻す。
            
        }else if(!bluetooth_adapter.isEnabled()){
            // bluetooth機能がoffならダイヤログを表示
            Intent bt_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bt_intent, REQUEST_ENABLE_BT);
        }
        
        
        select_monster = (Spinner) findViewById(R.id.monsterselect1);
        paired_device = (Spinner) findViewById(R.id.pared_device);
        monster_image = (ImageView) findViewById(R.id.mpicture);
        register_button = (Button) findViewById(R.id.register_button);
        socket_button =  (Button) findViewById(R.id.sanka);
        server_button =  (Button) findViewById(R.id.bosyu);
        address_disp = (TextView) findViewById(R.id.address_disp);
        pfb_strength_view = (TextView) findViewById(R.id.pfb_hp_view);
        pfb_quickness_view = (TextView) findViewById(R.id.pfb_spd_view);
        pfb_power_view = (TextView) findViewById(R.id.pfb_atk_view);
        pfb_kind_view = (TextView) findViewById(R.id.pfb_kind_view);
        pfb_stomach_view = (TextView) findViewById(R.id.pfb_satomach_view);
        pfb_luck_view = (TextView) findViewById(R.id.pfb_luck_view);
        pfb_life_view = (TextView) findViewById(R.id.pfb_age_view);

        // モンスター画像の配列を取得
        images = getResources().obtainTypedArray(R.array.monster_image);

        // /*めんどくさいけどスピナーに表示する文字の上手い見つけ方がわかんなかったので…*/
        // 保存モンスターのid一覧を取得
        // モンスター選択スピナーに登録するアダプターの生成
        // ローカル変数にしたほうがいい？
        ArrayAdapter<String> monster_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        monster_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_list = DBoperation.getId(getApplicationContext());
        monster_list = DBoperation.getMonsters(getApplicationContext(), id_list);
        for (Monster monster : monster_list) {
            // 保存モンスターを取得
            monster_adapter.add(monster.getName());
        }
        // 繋がってる相手一覧を表示する、スピナーのあだぷたーを生成
        ArrayAdapter<String> device_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        device_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Set<BluetoothDevice> paired_devices = bluetooth_adapter.getBondedDevices(); 
        //　デバイスの名前を取得
        for(BluetoothDevice device: paired_devices){
            device_adapter.add(new StringBuilder(device.getName()).append("  ").append(device.getAddress()).toString());
        }
        select_monster.setAdapter(monster_adapter);
        paired_device.setAdapter(device_adapter);
        paired_device.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                address_disp.setText(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // スピナーにリスナーを登録
        select_monster.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                HashMap<String, Integer> status = monster_list.get(position).getStatusList();
                Drawable draw = images.getDrawable(
                        DBoperation.getImage(
                                getApplicationContext(), id_list.get(position).toString()));
                // モンスター画像とステータスを更新
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

    @Override
    protected void onResume() {
        ArrayAdapter<String> device_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item);
        device_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Set<BluetoothDevice> paired_devices = bluetooth_adapter.getBondedDevices(); 
        //　デバイスの名前を取得
        for(BluetoothDevice device: paired_devices){
            device_adapter.add(new StringBuilder(device.getName()).append("  ").append(device.getAddress()).toString());
        }
        paired_device.setAdapter(device_adapter);

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            Toast.makeText(getApplicationContext(), R.string.cancel_bt, Toast.LENGTH_SHORT).show();
        }
    }
    public void click_button(View v){
        switch(v.getId()){
            case R.id.register_button:
                Intent i = new Intent(getApplicationContext(), DeviceActivityList.class);
                startActivity(i);
                break;
            case R.id.sanka:
                // 通信の実装、むりぽヽ(´ー｀)ノ。
                break;
            case R.id.bosyu:
                break;
        }
    }
}

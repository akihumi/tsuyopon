
package ac.jp.itc.s11013.tsuyoponmonster;

import java.lang.reflect.Method;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DeviceActivityList extends Activity {

    private BluetoothAdapter bt_adapter;
    private ArrayAdapter<String> new_device_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list);

        // AndroidManifest.xmlに記述してもいいかも
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver, filter);
        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, filter);
        filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        this.registerReceiver(receiver, filter);
        
        
        bt_adapter = BluetoothAdapter.getDefaultAdapter();
        doDiscovery();
        new_device_adapter = new ArrayAdapter<String>(this, R.layout.device_name);
        ListView new_devices_list = (ListView) findViewById(R.id.new_devices);
        new_devices_list.setAdapter(new_device_adapter);
        new_devices_list.setOnItemClickListener(deviceClickListener);
        //        Intent intent = new Intent();
        //        intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
        //
        //        // Set result and finish this Activity
        //        setResult(Activity.RESULT_OK, intent);
        //        finish();
        Button ensure = (Button) findViewById(R.id.ensure);
        ensure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_adapter.cancelDiscovery();
                // 他の端末から検出できるようになるぽ
                if (bt_adapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    Intent discoverableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    startActivity(discoverableIntent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (bt_adapter != null) {
            bt_adapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(receiver);
    }

    private void doDiscovery() {
        // Indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // If we're already discovering, stop it
        if (bt_adapter.isDiscovering()) {
            bt_adapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        bt_adapter.startDiscovery();
    }

    protected boolean execPairing(String bluetoothAddress, String pinCode) throws Exception {

        // Bluetoothアダプタ取得
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

        // BluetoothデバイスをMACアドレスから取得
        BluetoothDevice device = adapter.getRemoteDevice(bluetoothAddress);

        // ペアリング開始処理呼び出し
        Method createBond = device.getClass().getMethod("createBond", new Class[] {});
        Boolean result = (Boolean) createBond.invoke(device);
        if (!result.booleanValue()) {
            return false;
        }

        // PINコードをUTF8に変換
        Method convertPinToBytes = BluetoothDevice.class.getMethod("convertPinToBytes",
                new Class[] {
                    String.class
                });
        byte[] pinCodes = (byte[]) convertPinToBytes.invoke(BluetoothDevice.class, pinCode);

        // PINコード登録
        Method setPin = device.getClass().getMethod("setPin", new Class[] {
            byte[].class
        });
        result = (Boolean) setPin.invoke(device, pinCodes);
        if (!result.booleanValue()) {
            return false;
        }

        return true;
    }
    private OnItemClickListener deviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            bt_adapter.cancelDiscovery();
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            try {
                execPairing(address, "0000");
            } catch (Exception e) {}
        }
    };

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    new_device_adapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle(R.string.select_device);
                findViewById(R.id.title_new_devices).setVisibility(View.INVISIBLE);
                if (new_device_adapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    new_device_adapter.add(noDevices);
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
                finish();
            }
        }
    };
}

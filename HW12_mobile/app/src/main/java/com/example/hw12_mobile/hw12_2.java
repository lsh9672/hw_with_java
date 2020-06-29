package com.example.hw12_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class hw12_2 extends AppCompatActivity {
    TextView battery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw12_2);

        battery = (TextView)findViewById(R.id.battery);

    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(br);//화면이 보일떄만 받겠다는 의미

    }
    protected void onResume(){
        super.onResume();
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);//해당액션을 intentFilter에 넣음
        registerReceiver(br,iFilter);//등록(iFilter= 어떤정보를 받고싶은지 결정 ),

    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(action.equals(intent.ACTION_BATTERY_CHANGED)){
                int remain= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
                battery.setText("현재 충전량 : " + remain + "\n");

                int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED,0);
                switch(plug){
                    case 0:
                        battery.append("전원 연결 : 안됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        battery.append("전원 연결 : 어댑터 연결됨");
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        battery.append("전원 연결 : usb 연결됨");
                        break;
                }

            }
        }
    };
}

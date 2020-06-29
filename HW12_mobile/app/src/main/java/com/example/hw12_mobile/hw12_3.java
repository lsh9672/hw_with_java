package com.example.hw12_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class hw12_3 extends AppCompatActivity {
    Button btn_call;
    TextView text_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw12_3);

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CALL_LOG},MODE_PRIVATE);
        btn_call=(Button)findViewById(R.id.btn_call);
        text_call=(TextView)findViewById(R.id.text_call);

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_call.setText(getCallHistory());
            }
        });
    }
    public String getCallHistory(){ //현재 통화기록
        String[] callSet = new String[] {CallLog.Calls.DATE,CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};
        //cursor는 db처럼 열에 여러가지 데이터가 있고 이것이 행으로 존재하는것
        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, callSet,null,null,null);

        if(c.getCount()==0) return "통화기록 없음";

        StringBuffer callBuff = new StringBuffer(); //스트링버퍼를 만들어서 채워넣음(여러개가 있으면)
        callBuff.append("\n날짜 : 구분 : 전화번호 : 통화시간\n\n");
        c.moveToFirst();//커서의 처음으로 감
        do{
            long callDate = c.getLong(0);
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = datePattern.format(new Date(callDate));
            callBuff.append(date_str + " : ");
            if(c.getInt(1)==CallLog.Calls.INCOMING_TYPE) callBuff.append("착신 : ");
            else callBuff.append("발신 : ");

            callBuff.append(c.getString(2) +":");
            callBuff.append(c.getString(3) + "초\n");
        }while(c.moveToNext());

        c.close();
        return callBuff.toString();
    }
}

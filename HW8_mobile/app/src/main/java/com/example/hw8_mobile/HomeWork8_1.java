package com.example.hw8_mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class HomeWork8_1 extends AppCompatActivity {

    DatePicker date_picker;
    EditText text_edit,change_passwd,input_passwd;
    Button btn_write;
    String fileName;
    Switch btn_switch;
    View dialogview1,dialogview2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw8_1);

        setTitle("HW8-1");

        date_picker=(DatePicker)findViewById(R.id.date_picker);
        text_edit=(EditText)findViewById(R.id.text_edit);
        btn_write=(Button)findViewById(R.id.btn_write);
        btn_switch=(Switch)findViewById(R.id.btn_switch);

        btn_switch.setChecked(switch_current_load());
        if(btn_switch.isChecked()==false) {
            fileName = Integer.toString(date_picker.getYear()) + "_"
                    + Integer.toString(date_picker.getMonth()+1) + "_"
                    + Integer.toString(date_picker.getDayOfMonth()) + ".txt";
            String str = readDiary(fileName);
            text_edit.setText(str);
            btn_write.setEnabled(true);
            text_edit.setFocusableInTouchMode(true);
            text_edit.setFocusable(true);
        }
        else{
            text_edit.setText("");
            text_edit.setHint("일기 없음");
            btn_write.setEnabled(false);
            text_edit.setClickable(false);
            text_edit.setFocusable(false);
        }

        //btn_switch.setChecked(sharedPreferencesLoad());
        btn_switch.setOnClickListener(new CompoundButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreferencesSave(isChecked);
                if(btn_switch.isChecked()==true) {//(on)
                    btn_write.setEnabled(false);
                    text_edit.setClickable(false);
                    text_edit.setFocusable(false);

                    dialog1create();

                }
                else {//false일때(off)
                    btn_write.setEnabled(true);
                    text_edit.setFocusableInTouchMode(true);
                    text_edit.setFocusable(true);
                    dialog2create();

                }
                switch_current_save(btn_switch.isChecked());
            }

        });
        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        date_picker.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(btn_switch.isChecked()==false) {
                    fileName = Integer.toString(year) + "_"
                            + Integer.toString(monthOfYear + 1) + "_"
                            + Integer.toString(dayOfMonth) + ".txt";
                    String str = readDiary(fileName);
                        text_edit.setText(str);
                }
                else{
                    text_edit.setText("");
                    text_edit.setHint("일기 없음");
                }
            }
        });

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(btn_switch.isChecked()){
                        Toast.makeText(getApplication(),"ReadOnly mode",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FileOutputStream outFs = openFileOutput(fileName, MODE_PRIVATE);
                        String str = text_edit.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    btn_write.setText("수정하기");
                    Toast.makeText(getApplication(), fileName+"이 저장됨", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                    Toast.makeText(getApplication(),"error"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    String readDiary(String fName){
        String diaryStr = null;
        FileInputStream inFs;
        try{
            inFs = openFileInput(fName);
            byte[] txt = new byte[1000];
            inFs.read(txt);
            inFs.close();
            diaryStr =(new String(txt)).trim();
            btn_write.setText("수정하기");
        }catch (IOException e){
            text_edit.setHint("일기 없음");
            btn_write.setText("새로 저장");
        }
        return diaryStr;
    }
    public void sharePreferencesSave(String input_edit1){
            SharedPreferences settings = getSharedPreferences("passwordSave", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("password", input_edit1);
            editor.commit();
    }
    public boolean sharePreferencesLoad(String input_edit2){
        SharedPreferences settings = getSharedPreferences("passwordSave",MODE_PRIVATE);
        String str2 = settings.getString("password","");
        if(input_edit2.equals(str2.trim())){
            return true;
        }
        else
            return  false;
    }

    public void dialog1create(){
        AlertDialog.Builder dlg1 = new AlertDialog.Builder(HomeWork8_1.this);
        dialogview1 = (View)View.inflate(HomeWork8_1.this,R.layout.passwdchange_dialog,null);
        change_passwd = dialogview1.findViewById(R.id.change_passwd);
        dlg1.setIcon(R.drawable.icon_lock);
        dlg1.setView(dialogview1);
        dlg1.setTitle("암호를 변경하시겠습니까?");
        dlg1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharePreferencesSave(change_passwd.getText().toString());
                text_edit.setText("");
                text_edit.setHint("일기 없음");
                Toast.makeText(getApplicationContext(), "변경완료", Toast.LENGTH_SHORT).show();
            }
        });
        dlg1.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_switch.setChecked(false);
                Toast.makeText(getApplicationContext(),"취소됨",Toast.LENGTH_SHORT).show();
            }
        });
        dlg1.setNeutralButton("기존 패스워드 사용", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_switch.setChecked(true);
                text_edit.setText("");
                text_edit.setHint("일기 없음");
                Toast.makeText(getApplicationContext(),"잠김",Toast.LENGTH_SHORT).show();
            }
        });
        dlg1.show();

    }

    public void dialog2create(){//false일때
        AlertDialog.Builder dlg2 = new AlertDialog.Builder(HomeWork8_1.this);
        dialogview2=(View)View.inflate(HomeWork8_1.this,R.layout.passwdinput_dialog,null);
        input_passwd = dialogview2.findViewById(R.id.input_passwd);
        dlg2.setIcon(R.drawable.icon_key);
        dlg2.setView(dialogview2);
        dlg2.setTitle("암호를 입력하세요");
        dlg2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(sharePreferencesLoad(input_passwd.getText().toString().trim())) {
                    if(btn_switch.isChecked()==false) {
                        fileName = Integer.toString(date_picker.getYear()) + "_"
                                + Integer.toString(date_picker.getMonth()+1) + "_"
                                + Integer.toString(date_picker.getDayOfMonth()) + ".txt";
                        String str = readDiary(fileName);
                        text_edit.setText(str);
                    }
                    else{
                        text_edit.setText("");
                        text_edit.setHint("일기 없음");
                    }

                    Toast.makeText(getApplicationContext(), "잠금해제", Toast.LENGTH_SHORT).show();
                }
                else {
                    btn_switch.setChecked(true);
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dlg2.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_switch.setChecked(true);
                Toast.makeText(getApplicationContext(),"취소됨",Toast.LENGTH_SHORT).show();
            }
        });

        dlg2.show();
    }

    public void switch_current_save(boolean checking){
        SharedPreferences settings = getSharedPreferences("switchCheck", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("check", String.valueOf(checking));
        editor.commit();
    }

    public boolean switch_current_load(){
        SharedPreferences settings = getSharedPreferences("switchCheck",MODE_PRIVATE);
        String str2 = settings.getString("check","").trim();
            return Boolean.valueOf(str2).booleanValue();
    }

}

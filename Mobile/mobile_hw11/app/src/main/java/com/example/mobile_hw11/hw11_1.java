package com.example.mobile_hw11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class hw11_1 extends AppCompatActivity {
    Button btn_reset,btn_input,btn_update,btn_fix,btn_del;
    EditText input_name,input_number,group_name_result,group_number_result;
    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw11_1);

        btn_reset= (Button)findViewById(R.id.btn_reset);
        btn_input= (Button)findViewById(R.id.btn_input);
        btn_update= (Button)findViewById(R.id.btn_update);
        btn_fix= (Button)findViewById(R.id.btn_fix);
        btn_del= (Button)findViewById(R.id.btn_del);

        input_name = (EditText)findViewById(R.id.input_name);
        input_number = (EditText)findViewById(R.id.input_number);

        group_name_result = (EditText)findViewById(R.id.group_name_result);
        group_number_result = (EditText)findViewById(R.id.group_number_result);

        myHelper = new myDBHelper(this);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();//DB를 가져옴
                myHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
                btn_update.callOnClick();
                Toast.makeText(getApplication(),"데이터 초기화",Toast.LENGTH_SHORT).show();
            }
        }); //초기화

        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO groupTBL VALUES( '"+ input_name.getText().toString() +"',"+ input_number.getText().toString() + ");");
                sqlDB.close();
                btn_update.callOnClick();
                Toast.makeText(getApplication(),"입력됨",Toast.LENGTH_SHORT).show();
            }
        }); //입력

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

                String strNames= "그룹이름" + "\r\n" + "--------" + "\r\n";
                String strNumbers = "인원" +  "\r\n" +  "--------" + "\r\n";
                while(cursor.moveToNext()){
                    strNames+=cursor.getString(0)+ "\r\n";
                    strNumbers+=cursor.getString(1)+ "\r\n";
                }

                group_name_result.setText(strNames);
                group_number_result.setText(strNumbers);

                cursor.close();
                sqlDB.close();
                Toast.makeText(getApplication(),"조회완료",Toast.LENGTH_SHORT).show();
            }
        }); //조회

        btn_fix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("UPDATE groupTBL SET gNumber = '"+ input_number.getText().toString() +"' WHERE gName='"+ input_name.getText().toString() +"' ;");
                sqlDB.close();
                btn_update.callOnClick();
                Toast.makeText(getApplication(),"수정완료",Toast.LENGTH_SHORT).show();
            }
        });//수정

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DELETE FROM groupTBL WHERE gName = '"+input_name.getText().toString()+"';");
                sqlDB.close();
                btn_update.callOnClick();
                Toast.makeText(getApplication(),"삭제완료",Toast.LENGTH_SHORT).show();
            }
        });//삭제

    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context){super(context, "groupDB",null,1);}

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL(gName CHAR(20) PRIMARY KEY,gNumber INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}

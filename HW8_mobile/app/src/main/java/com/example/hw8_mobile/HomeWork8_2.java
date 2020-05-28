package com.example.hw8_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class HomeWork8_2 extends AppCompatActivity{
    Button btn_prev,btn_next;
    myPictureView pictureview;
    File[] imageFiles;
    String imageFname;//
    TextView image_count;
    int curNum;// 사진의 현재의 인덱스 저장

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw8_2);

        setTitle("HW8-2");

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);//권한주기
        btn_prev= (Button)findViewById(R.id.btn_prev);
        btn_next=(Button)findViewById(R.id.btn_next);
        pictureview = (myPictureView) findViewById(R.id.pictureview);
        image_count = (TextView)findViewById(R.id.image_count);


        imageFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();//listFiles이 Pictures의 하위파일/파일경로를 포함한 파일 명을 저장
        imageFname = imageFiles[0].toString();//파일이름중에 첫번째것을 string으로 변환
        pictureview.imagePath=imageFname; //myPictureView class의 imagePath변수에 파일 이름(경로 포함)을 넣음
        image_count.setText("1/"+imageFiles.length);
        curNum=0;
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum==0){
                    curNum=4;
                    imageFname = imageFiles[curNum].toString();
                    pictureview.imagePath=imageFname;
                    pictureview.invalidate();//myPictureView에서 설명한것 처럼 invalidate호출시 onDraw()를 실행
                }
                else{
                    curNum--;
                    imageFname = imageFiles[curNum].toString();
                    pictureview.imagePath=imageFname;
                    pictureview.invalidate();
                }
                int toast_string1= curNum+1;
                image_count.setText(toast_string1+"/"+imageFiles.length);
                Toast.makeText(getApplicationContext(),toast_string1+"번 그림입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum==(imageFiles.length-1)){
                    curNum=0;
                    imageFname=imageFiles[curNum].toString();
                    pictureview.imagePath=imageFname;
                    pictureview.invalidate();
                }
                else{
                    curNum++;
                    imageFname=imageFiles[curNum].toString();
                    pictureview.imagePath=imageFname;
                    pictureview.invalidate();
                }
                int toast_string2= curNum+1;
                image_count.setText(toast_string2+"/"+imageFiles.length);
                Toast.makeText(getApplicationContext(),toast_string2+"번 그림입니다.",Toast.LENGTH_SHORT).show();
            }

        });

    }
}
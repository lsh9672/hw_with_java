package com.example.button_widget;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class third extends AppCompatActivity {
    RadioGroup rgroup;
    ImageView image;
    CheckBox check_1, check_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT); // 파라메터를 만듦

        LinearLayout baseLayout = new LinearLayout(this);//레이아웃을 실제로 만드는 부분
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setBackgroundColor(Color.rgb(0,255,0));
        setContentView(baseLayout, params);

        Button btn = new Button(this);
        btn.setText("BUTTON");
        btn.setBackgroundColor(Color.MAGENTA);
        baseLayout.addView(btn);
        btn.getLayoutParams().width=LinearLayout.LayoutParams.WRAP_CONTENT;
        btn.getLayoutParams().height=100;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"코드로 생성한 버튼입니다", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

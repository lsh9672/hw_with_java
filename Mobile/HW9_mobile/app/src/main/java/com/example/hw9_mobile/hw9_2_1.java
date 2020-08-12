package com.example.hw9_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class hw9_2_1 extends AppCompatActivity {
    ImageView btn_image1,btn_image2,btn_image3;
    TextView count_text1,count_text2,count_text3;
    int count1=0,count2=0,count3=0;
    Button btn_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw9_2_1);

        count_text1 = (TextView)findViewById(R.id.count_text1);
        count_text2 = (TextView)findViewById(R.id.count_text2);
        count_text3 = (TextView)findViewById(R.id.count_text3);

        count_text1.setText(String.valueOf(count1));
        count_text2.setText(String.valueOf(count2));
        count_text3.setText(String.valueOf(count3));

        btn_image1=(ImageView)findViewById(R.id.btn_image1);
        btn_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1++;
                count_text1.setText(String.valueOf(count1));
            }
        });
        btn_image2=(ImageView)findViewById(R.id.btn_image2);
        btn_image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2++;
                count_text2.setText(String.valueOf(count2));
            }
        });
        btn_image3=(ImageView)findViewById(R.id.btn_image3);
        btn_image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count3++;
                count_text3.setText(String.valueOf(count3));
            }
        });

        btn_result=(Button)findViewById(R.id.btn_result);
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hw9_2_1.this, hw9_2_2.class);
                intent.putExtra("text1",Integer.parseInt(count_text1.getText().toString()));
                intent.putExtra("text2",Integer.parseInt(count_text2.getText().toString()));
                intent.putExtra("text3",Integer.parseInt(count_text3.getText().toString()));

                startActivityForResult(intent, 12);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode==RESULT_OK){
            count1 =data.getIntExtra("final_result1",0);
            count2 =data.getIntExtra("final_result2",0);
            count3 =data.getIntExtra("final_result3",0);
            count_text1.setText(String.valueOf(count1));
            count_text2.setText(String.valueOf(count2));
            count_text3.setText(String.valueOf(count3));
        }


    }
}


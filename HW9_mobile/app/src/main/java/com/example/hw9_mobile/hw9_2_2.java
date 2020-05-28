package com.example.hw9_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class hw9_2_2 extends AppCompatActivity {
    RatingBar rat1,rat2,rat3;
    TextView rat_text1,rat_text2,rat_text3;
    Button btn_back;
    int result1=0,result2=0,result3=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw9_2_2);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getIntExtra("text1",0)>5){
                result1=5;
            }
            else {
                result1 = intent.getIntExtra("text1", 0);
            }
            if(intent.getIntExtra("text2",0)>5){
                result2=5;
            }
            else {
                result2 = intent.getIntExtra("text2", 0);
            }
            if(intent.getIntExtra("text3",0)>5){
                result3=5;
            }
            else {
                result3 = intent.getIntExtra("text3", 0);
            }

        }
        rat_text1 = (TextView)findViewById(R.id.rat_text1);
        rat_text2 = (TextView)findViewById(R.id.rat_text2);
        rat_text3=(TextView)findViewById(R.id.rat_text3);

        rat1 = (RatingBar)findViewById(R.id.rat1);
        rat1.setProgress(result1);
        rat1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                result1=(int)rating;
            }
        });
        rat2 = (RatingBar)findViewById(R.id.rat2);
        rat2.setProgress(result2);
        rat2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                result2=(int)rating;
            }
        });
        rat3 = (RatingBar)findViewById(R.id.rat3);
        rat3.setProgress(result3);
        rat3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                result3=(int)rating;
            }
        });
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(hw9_2_2.this,hw9_2_1.class);
                intent.putExtra("final_result1",result1);
                intent.putExtra("final_result2",result2);
                intent.putExtra("final_result3",result3);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}

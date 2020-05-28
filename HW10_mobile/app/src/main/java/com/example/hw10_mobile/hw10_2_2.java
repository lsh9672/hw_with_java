package com.example.hw10_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class hw10_2_2 extends AppCompatActivity {
    TextView fruit_name, fruit_price, fruit_origin,fruit_time;
    ImageView fruit_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw10_2_2_layout);

        Intent intent = getIntent();

        fruit_name = (TextView)findViewById(R.id.fruit_name);
        fruit_price=(TextView)findViewById(R.id.fruit_price);
        fruit_origin=(TextView)findViewById(R.id.fruit_origin);
        fruit_image = (ImageView)findViewById(R.id.fruit_image);
        fruit_time=(TextView)findViewById(R.id.fruit_time);



        if(intent != null){
            fruit_name.setText(intent.getStringExtra("name"));
            fruit_image.setImageResource(intent.getIntExtra("image",0));
            fruit_price.setText(intent.getStringExtra("price"));
            fruit_origin.setText(intent.getStringExtra("origin"));
            fruit_time.setText(intent.getStringExtra("time"));
        }


    }
}

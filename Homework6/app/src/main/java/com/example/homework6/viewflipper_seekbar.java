package com.example.homework6;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class viewflipper_seekbar extends AppCompatActivity {
    ViewFlipper flipper;
    RatingBar ratingbar;
    String [] picture_name = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_widget_viewflipper);

        final String[] picture_name={"첫번째 사진", "두번째 사진","세번째 사진","네번쨰 사진"};
        flipper =(ViewFlipper)findViewById(R.id.flipper);
        ratingbar= (RatingBar)findViewById(R.id.ratingbar);

        ImageView image3 = new ImageView(this);
        image3.setImageResource(R.drawable.ic_image3);
        flipper.addView(image3);

        ImageView image4 = new ImageView(this);
        image4.setImageResource(R.drawable.ic_image4);
        flipper.addView(image4);


        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(getApplicationContext(),picture_name[flipper.getDisplayedChild()]+": 별점"+rating,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void nextbtn(View v){
        flipper.showNext();
    }
    public void previousbtn(View v){
        flipper.showPrevious();

    }
}
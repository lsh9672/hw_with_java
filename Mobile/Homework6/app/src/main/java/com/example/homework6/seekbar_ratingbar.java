package com.example.homework6;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class seekbar_ratingbar extends AppCompatActivity {
    SeekBar seek;
    RatingBar rating;
    TextView seek_text, rating_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_widget_seekbar);
        seek = (SeekBar) findViewById(R.id.seek);
        rating = (RatingBar) findViewById(R.id.rating);
        seek_text = (TextView)findViewById(R.id.seek_text);
        rating_text = (TextView)findViewById(R.id.rating_text);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                seek.setProgress((int) (rating * 20));
                rating_text.setText(new String().format("rating: %2.1f",rating));
            }
        });
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seek_text.setText(new String().format("seekbar Progress: %d",progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
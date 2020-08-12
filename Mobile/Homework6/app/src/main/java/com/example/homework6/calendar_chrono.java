package com.example.homework6;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class calendar_chrono extends AppCompatActivity {
    CalendarView calendar;
    Chronometer chronometer;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advanced_widget_calendar);

        calendar = (CalendarView)findViewById(R.id.calendar);
        chronometer=(Chronometer)findViewById(R.id.chronometer);
        text=(TextView)findViewById(R.id.text);

        SimpleDateFormat formatyear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatmonth = new SimpleDateFormat("M");
        SimpleDateFormat formatday = new SimpleDateFormat("dd");
        Date mdate = new Date(calendar.getDate());

        String year=formatyear.format(mdate);
        String month=formatmonth.format(mdate);
        String day=formatday.format(mdate);
        text.setText("year: "+year+" month: "+month+" day: "+day);


        CalendarView.OnDateChangeListener mDataChangeListener = new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                text.setText(new String().format("year: %d month: %d, day: %d",year,month+1,dayOfMonth));
            }
        };
        calendar.setOnDateChangeListener(mDataChangeListener);
    }

    public void chronoStart(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setTextColor(Color.rgb(0,0,0));
        chronometer.start();
    }
    public void chronoStop(View v){
        chronometer.stop();
        chronometer.setTextColor(Color.rgb(255,0,0));
    }

}
package com.example.hw10_mobile;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class hw10_1 extends AppCompatActivity{
    Spinner spinner;
    ImageView spinner_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw10_1_layout);

        spinner = (Spinner)findViewById(R.id.spinner);
        spinner_image=(ImageView)findViewById(R.id.spinner_image);

        final String[]  movie_name = getResources().getStringArray(R.array.movie_name);
        final int[] image_resource = {R.drawable.movie_image1,R.drawable.movie_image2,R.drawable.movie_image3};
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.movie_name , android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_image.setImageResource(image_resource[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner.setAdapter(spinnerAdapter);
    }
}

package com.example.button_widget;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class second extends AppCompatActivity {
    RadioGroup rgroup;
    ImageView image;
    CheckBox check_1,check_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        check_1 = (CheckBox)findViewById(R.id.check_1);
        check_2 = (CheckBox)findViewById(R.id.check_2);

        check_1.setOnCheckedChangeListener(mCheckListener);
        check_2.setOnCheckedChangeListener(mCheckListener);

        image =(ImageView)findViewById(R.id.image);
        image.setImageResource(R.drawable.v);

        rgroup =(RadioGroup)findViewById(R.id.rgroup);
        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                StringBuilder mStr = new StringBuilder();

                if(checkedId == R.id.radio_1){
                    mStr.append("MAN selected");
                    image.setImageResource(R.drawable.v);
                }
                else{
                    mStr.append("WOMAN selected");
                    image.setImageResource(R.drawable.chae);
                }

            }
        });
    }
    CompoundButton.OnCheckedChangeListener mCheckListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            StringBuilder mStr = new StringBuilder();

            if(buttonView.getId() == R.id.check_1)
                mStr.append(check_1.getText().toString());
            else
                mStr.append(check_2.getText().toString());

            if(isChecked)
                mStr.append(" "+ "checked");
            else
                mStr.append(" "+ "Unchecked");

            Toast.makeText(getApplicationContext(),mStr,Toast.LENGTH_SHORT).show();
        }
    };
}

package com.example.button_widget;

import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RadioGroup group_scale,group_name;
    RadioButton btn_name1,btn_name2,btn_name3,btn_scale1,btn_scale2,btn_scale3,btn_scale4;
    ImageView picture;
    CheckBox btn_twice,btn_bts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework4_4);

        group_scale = (RadioGroup)findViewById(R.id.group_scale);
        group_name = (RadioGroup)findViewById(R.id.group_name);

        btn_name1 = (RadioButton)findViewById(R.id.btn_name1);
        btn_name2 = (RadioButton)findViewById(R.id.btn_name2);
        btn_name3 = (RadioButton)findViewById(R.id.btn_name3);
        btn_scale1 = (RadioButton)findViewById(R.id.btn_scale1);
        btn_scale2 = (RadioButton)findViewById(R.id.btn_scale2);
        btn_scale3 = (RadioButton)findViewById(R.id.btn_scale3);
        btn_scale4 = (RadioButton)findViewById(R.id.btn_scale4);

        picture = (ImageView)findViewById(R.id.picture);

        btn_twice = (CheckBox)findViewById(R.id.btn_twice);
        btn_bts = (CheckBox)findViewById(R.id.btn_bts);

        btn_twice.setOnCheckedChangeListener(mcheck);
        btn_bts.setOnCheckedChangeListener(mcheck);


        picture.setImageResource(R.drawable.anonymous);

        group_scale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.btn_scale1:
                        picture.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        break;
                    case R.id.btn_scale2:
                        picture.setScaleType(ImageView.ScaleType.MATRIX);
                        Matrix mat = picture.getImageMatrix();
                        float scale = 2.0f;
                        mat.setScale(scale,scale);
                        picture.setImageMatrix(mat);
                        break;
                    case R.id.btn_scale3:
                        picture.setScaleType(ImageView.ScaleType.FIT_XY);
                        break;
                    case R.id.btn_scale4:
                        picture.setScaleType(ImageView.ScaleType.CENTER);
                        break;
                }
            }
        });

    }
    CompoundButton.OnCheckedChangeListener mcheck = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if((buttonView.getId() == R.id.btn_twice) &&(isChecked==true)){
                btn_name1.setText("채영");
                btn_name2.setText("사나");
                btn_name3.setText("나연");
                btn_bts.setChecked(false);
                group_name.clearCheck();
                group_scale.clearCheck();


                group_name.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.btn_name1:
                                picture.setImageResource(R.drawable.chae);
                                break;
                            case R.id.btn_name2:
                                picture.setImageResource(R.drawable.sa);
                                break;
                            case R.id.btn_name3:
                                picture.setImageResource(R.drawable.na);
                                break;
                        }

                    }
                });

            }
            else if((buttonView.getId() == R.id.btn_bts)&&(isChecked==true)){
                btn_name1.setText("정국");
                btn_name2.setText("태형");
                btn_name3.setText("지민");
                btn_twice.setChecked(false);
                group_name.clearCheck();
                group_scale.clearCheck();

                group_name.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.btn_name1:
                                picture.setImageResource(R.drawable.jung);
                                break;
                            case R.id.btn_name2:
                                picture.setImageResource(R.drawable.tae);
                                break;
                            case R.id.btn_name3:
                                picture.setImageResource(R.drawable.ji);
                                break;
                        }

                    }
                });

            }
            else if((btn_twice.isChecked()==false) &&(btn_bts.isChecked()==false)){
                btn_name1.setText("name1");
                btn_name2.setText("name2");
                btn_name3.setText("name3");
                group_name.clearCheck();
                group_scale.clearCheck();

                group_name.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch(checkedId){
                            case R.id.btn_name1:
                                picture.setImageResource(R.drawable.anonymous);
                                break;
                            case R.id.btn_name2:
                                picture.setImageResource(R.drawable.anonymous);
                                break;
                            case R.id.btn_name3:
                                picture.setImageResource(R.drawable.anonymous);
                                break;
                        }

                    }
                });

            }

            }

    };
}

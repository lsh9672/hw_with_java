package com.example.button200330;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_add,btn_sub,btn_mul,btn_div,btn_cancel,btn_cal,btn_left,btn_right;
    TextView result_text;
    String result_temp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework3_1);

        setTitle("계산기");



        result_text = (TextView)findViewById(R.id.result_text);

        btn_left = (Button)findViewById(R.id.btn_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_left.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });
        btn_right=(Button)findViewById(R.id.btn_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_right.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_1=(Button)findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_1.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });
        btn_2=(Button)findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_2.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });
        btn_3=(Button)findViewById(R.id.btn_3);
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_3.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });

        btn_4=(Button)findViewById(R.id.btn_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_4.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });

        btn_5=(Button)findViewById(R.id.btn_5);
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_5.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_6=(Button)findViewById(R.id.btn_6);
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_6.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });

        btn_7=(Button)findViewById(R.id.btn_7);
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_7.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });

        btn_8=(Button)findViewById(R.id.btn_8);
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_8.getText().toString());
                result_temp = result_text.getText().toString();

            }
        });

        btn_9=(Button)findViewById(R.id.btn_9);
        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_9.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_0=(Button)findViewById(R.id.btn_0);
        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_0.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_add=(Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_add.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_sub=(Button)findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_sub.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });

        btn_mul=(Button)findViewById(R.id.btn_mul);
        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_mul.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });
        btn_div=(Button)findViewById(R.id.btn_div);
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText(result_temp+btn_div.getText().toString());
                result_temp = result_text.getText().toString();
            }
        });
        btn_cancel=(Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result_text.setText("");
                result_temp = result_text.getText().toString();
                
            }
        });
        btn_cal=(Button)findViewById(R.id.btn_cal);
        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression e = new Expression(result_text.getText().toString());
                String test = Double.toString(e.calculate());
                if(test.equals("NaN")) {
                result_text.setText("Error");
                result_temp = "";
                }
                else {
                    int pp = (int) e.calculate();
                    result_text.setText(result_temp + btn_cal.getText().toString() + pp);
                    result_temp = "";
                }

            }
        });

    }
}

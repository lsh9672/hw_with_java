package com.example.button200330;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

public class SubActivity extends AppCompatActivity {
    EditText input;
    Button btn_cal;
    String math;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework3_2);

        setTitle("계산기");

        input = (EditText)findViewById(R.id.input);
        btn_cal = (Button)findViewById(R.id.btn_cal);
        btn_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                math = input.getText().toString();
                Expression e = new Expression(math);
                input.setText(e.getExpressionString()+"="+e.calculate());
            }
        });




    }
}

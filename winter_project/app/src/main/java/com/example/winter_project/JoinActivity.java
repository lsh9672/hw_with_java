package com.example.winter_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JoinActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText join_id;
    private EditText join_passwd;
    private EditText join_passwd_check;
    EditText join_name;
    Button join_btn;
    String checking,password2,id2,name2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);

        auth = FirebaseAuth.getInstance();
        join_id= (EditText)findViewById(R.id.join_id);
        join_passwd= (EditText)findViewById(R.id.join_passwd);
        join_passwd_check= (EditText)findViewById(R.id.join_passwd_check);
        join_name= (EditText)findViewById(R.id.join_name);
        join_btn = (Button)findViewById(R.id.join_btn);


        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checking=join_passwd_check.getText().toString();
                password2=join_passwd.getText().toString();
                id2= join_id.getText().toString();
                name2 = join_name.getText().toString();
                createUser(id2, password2,checking,name2);
            }
        });
    }

    private void createUser(final String email, final String password, final String check, final String name) {
        if((email.length()>0)&&(password.length()>0)&&(check.length()>0)&&(name.length()>0)) {
            if (password.equals(check)) {
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = auth.getCurrentUser();
                                    Toast.makeText(JoinActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    if (task.getException() != null)
                                        Toast.makeText(JoinActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않음", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(JoinActivity.this, "닉네임또는 이메일 또는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
    }

}
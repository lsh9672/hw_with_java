package com.example.winter_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class picture_management extends AppCompatActivity {
    ImageView m_picture,m_btn_back,btn_google_map;
    TextView m_camera_time,m_car_kind, m_camera_location3,m_camera_location4,unknown,dialog_layout_cancel_btn;
    EditText memo;
    FirebaseDatabase database;
    FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private String a1,a2,a3,a4,a5,a6,delete_name_storage;
    User2 user2= new User2();
    Dialog dialog;
    Button dialog_layout_check_btn,dialog_layout_edit_btn,m_btn_save,m_btn_delete,btn_report;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_management);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},MODE_PRIVATE);
        //time_unknown = (TextView)findViewById(R.id.time_unknown);
        m_picture = (ImageView)findViewById(R.id.m_picture);
        m_camera_time=(TextView)findViewById(R.id.m_camera_time);
        m_car_kind=(TextView)findViewById(R.id.m_car_kind);
        m_camera_location3=(TextView)findViewById(R.id.m_camera_location3);//위도
        m_camera_location4=(TextView)findViewById(R.id.m_camera_location4);//경도
        unknown= (TextView)findViewById(R.id.unknown); // 알수없음
        memo =(EditText)findViewById(R.id.memo);
        m_btn_back=(ImageView)findViewById(R.id.m_btn_back);
        m_btn_save = (Button)findViewById(R.id.m_btn_save); //저장하기(파이어베이스 업로드)
        btn_google_map=(ImageView)findViewById(R.id.btn_google_map);
        m_btn_delete = (Button)findViewById(R.id.m_btn_delete);
        btn_report=(Button)findViewById(R.id.btn_report);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();


        Intent intent = getIntent();
        final String uni_name = intent.getStringExtra("unique_name");
        final String ggla = intent.getStringExtra("select_la");
        final String gglo = intent.getStringExtra("select_lo");

        databaseReference= database.getReference().child(auth.getCurrentUser().getUid()).child(uni_name);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           user2 = dataSnapshot.getValue(User2.class);
            a1 = user2.imageUrl;
            a2 = user2.capture_time;
            a3= user2.car;
            a4 = user2.latitude;
            a5 = user2.longtitude;
            a6 = user2.picture_memo;
            delete_name_storage  = user2.image_delete_name;
            Glide.with(getApplicationContext()).load(a1).into(m_picture);
            m_camera_time.setText(a2);
            m_car_kind.setText(a3);
            m_camera_location3.setText(a4);
            m_camera_location4.setText(a5);
            memo.setText(a6);

            if((a2.equals("")) || (a2.equals("알수없음"))){
                m_camera_time.setText("알수없음");

            }

            if(!(a4.equals(""))&& !(a5.equals(""))) {
                unknown.setText("");
            }
            else {
                unknown.setText("알수없음");
            }

            if((ggla!=null)&&(gglo!=null) ){
                m_camera_location3.setText(ggla);
                m_camera_location4.setText(gglo);
                unknown.setText("");
            }
         }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        m_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataupload();

            }
        });
        m_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_database(uni_name);
                finish();

            }
        });


        m_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_google_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(picture_management.this);//다이얼로그 생성
                dialog.setContentView(R.layout.dialog_layout);//다이얼로그 화면 구성

                dialog_layout_check_btn = (Button)dialog.findViewById(R.id.dialog_layout_check_btn);
                dialog_layout_check_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((unknown.getText().equals("알수없음"))){
                            Toast.makeText(getApplicationContext(),"위치를 알수없음", Toast.LENGTH_SHORT).show();
                        }
                        else if(!(m_camera_location3.getText().equals("")) && !(m_camera_location4.getText().equals(""))) {
                            Intent intent = new Intent(picture_management.this, ggmap.class);
                            intent.putExtra("unique_name", uni_name);
                            intent.putExtra("at", m_camera_location3.getText().toString());
                            intent.putExtra("long", m_camera_location4.getText().toString());
                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });

                dialog_layout_edit_btn = (Button)dialog.findViewById(R.id.dialog_layout_edit_btn);
                dialog_layout_edit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(picture_management.this, ggmap_edit.class);
                        intent.putExtra("unique_name",uni_name);
                        startActivity(intent);

                    }
                });
                dialog_layout_cancel_btn = (TextView)dialog.findViewById(R.id.dialog_layout_cancel_btn);
                dialog_layout_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sms_text="\n촬영시간 : " + m_camera_time.getText().toString() +
                        "\n차량종류 : "+m_car_kind.getText().toString() +"\n위도 : "
                        +m_camera_location3.getText().toString()+"\n경도"
                        +m_camera_location4.getText().toString();
                try{
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("110",null,sms_text,null, null);
                    Toast.makeText(getApplicationContext(), "SMS 전송완료!", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"SMS 전송실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.btn_menu:
                return true;

            case R.id.btn_logout:
                auth.signOut();
                LoginManager.getInstance().logOut();
                finish();
                Intent intent = new Intent(picture_management.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dataupload() {
        user2.picture_memo =  memo.getText().toString();
        user2.latitude = m_camera_location3.getText().toString();
        user2.longtitude = m_camera_location4.getText().toString();
        databaseReference.setValue(user2);

    }

    public void delete_database(final String unique){
        storage.getReference().child("images").child(delete_name_storage).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                database.getReference().child(auth.getCurrentUser().getUid()).child(unique).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {//디비에서는 거의 실패할일이 없어서 쓰지 않음
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "데이터 삭제 성공", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}

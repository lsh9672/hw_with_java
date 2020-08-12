package com.example.winter_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Core extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseStorage storage;

    TextView top_name;
    private BottomNavigationView bottomNavigationView;
    private AnalyticFragment af;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private AnalyticFragment analytic_fragment;
    private CameraFragment camera_fragment;
    private ManagementFragment management_fragment;
    public String imageFilePath;
    public Uri photoUri;
    private static final int REQUEST_IMAGE_CAPTURE=672;
    String format_date;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {//생명주기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            checkPermission();
        }// 위치정보 사용을 위한 권한 주기


        auth= FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다")//카메라 권한 팝업을 알려줄때 어떤 메시지를 띄울지
                .setDeniedMessage("거부됨")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();


        top_name = (TextView)findViewById(R.id.top_name);
        bottomNavigationView=findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.bottom_menu_camera:
                        setFragment(0);
                        break;
                    case R.id.bottom_menu_analytic:
                        setFragment(1);
                        break;
                    case R.id.bottom_menu_management:
                        setFragment(2);
                        break;
                }
                return true;
            }
        });
        camera_fragment= new CameraFragment();
        analytic_fragment= new AnalyticFragment();
        management_fragment = new ManagementFragment();
        setFragment(1);//괄호안에 첫화면을 무엇으로 지정해줄것인지 선택
        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_analytic);

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
                Intent intent = new Intent(Core.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //프래그먼트 교체가 일어나는 실행문
    private void setFragment(int n){
        fm = getSupportFragmentManager();
        ft=fm.beginTransaction();
        switch(n){
            case 0:
                //촬영버튼을 눌렀을때 실행되는부분
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//이미지를 촬영하는 기본카메라  띄우는 구문

                if(intent.resolveActivity(getPackageManager())!=null){
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (IOException e){

                    }
                    if(photoFile!=null){
                        photoUri= (Uri) FileProvider.getUriForFile(getApplicationContext(),getPackageName(),photoFile);
                        bottomNavigationView.setSelectedItemId(R.id.bottom_menu_analytic);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }

                }


                break;
            case 1:
                ft.replace(R.id.core_change,analytic_fragment);
                ft.commit(); //commit은 보통 저장을 의미
                break;
            case 2:
                ft.replace(R.id.core_change,management_fragment);
                ft.commit();
                break;

        }
    }//commit은 보통 저장을 의미

    //사진촬영을 위한 부분

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
        String imageFileName ="TEST_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif=null;
            try{
                exif= new ExifInterface(imageFilePath);
            }catch(IOException e){
                e.printStackTrace();
            }
            int exifOrientation;
            int exifDegree;

            if(exif !=null){
                exifOrientation= exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);

            }else{
                exifDegree=0;
            }

            long now = System.currentTimeMillis();
            Date cur_date = new Date(now);
            SimpleDateFormat format_info = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            format_date = format_info.format(cur_date);


            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location == null){

                            }
                           else{

                               double current_lat = location.getLatitude();
                               double current_lng = location.getLongitude();
                                ((TextView)findViewById(R.id.camera_location3)).setText(String.valueOf(current_lat));
                                ((TextView)findViewById(R.id.camera_location4)).setText(String.valueOf(current_lng));
                                if(!String.valueOf(current_lat).equals("") && !String.valueOf(current_lng).equals("") ){
                                    ((TextView)findViewById(R.id.fragment_unknown)).setText("");

                                }
                            }
                        }

                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("error==================",e.toString());
                    ((TextView)findViewById(R.id.camera_location3)).setText("");
                    ((TextView)findViewById(R.id.camera_location4)).setText("");
                    ((TextView)findViewById(R.id.fragment_unknown)).setText("알수없음");
                }
            });

            ((ImageView)findViewById(R.id.camera_img)).setImageBitmap(rotate(bitmap,exifDegree));
            bottomNavigationView.setSelectedItemId(R.id.bottom_menu_analytic);
            ((TextView)findViewById(R.id.camera_time)).setText(format_date);
            ((TextView)findViewById(R.id.uri_text)).setText(photoUri.toString());
            ((TextView)findViewById(R.id.car_kind)).setText("");
            ((TextView)findViewById(R.id.car_acc)).setText("");

        }
    }

    private int exifOrientationToDegress(int exifOrientation){
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90){
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180){
            return 180;
        }else if(exifOrientation==ExifInterface.ORIENTATION_ROTATE_270){
            return 270;
        }
        return 0;
    }



    private Bitmap rotate(Bitmap bitmap,float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }


    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {//허용이 됬을때 일어나는 액션
            Toast.makeText(getApplicationContext(), "허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {//거절됬을떄 일어나는 액션
            Toast.makeText(getApplicationContext(), "거절됨", Toast.LENGTH_SHORT).show();
        }
    };


    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }//위치정보사용하기 위해 권한주기



}



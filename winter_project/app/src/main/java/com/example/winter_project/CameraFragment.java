package com.example.winter_project;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CameraFragment extends Fragment {
    private View view;
    private String imageFilePath;
    private Uri photoUri;
    private static final int REQUEST_IMAGE_CAPTURE=672;
    Button camera_check_btn,camera_on_btn;
    ImageView camera_fragment_img;
    String format_date;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.camera_fragment,container,false);

        TedPermission.with(getActivity().getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다")//카메라 권한 팝업을 알려줄때 어떤 메시지를 띄울지
                .setDeniedMessage("거부됨")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();
        camera_fragment_img = (ImageView) view.findViewById(R.id.camera_fragment_img);
        camera_check_btn = (Button)view.findViewById(R.id.camera_check_btn);
        camera_on_btn = (Button)view.findViewById(R.id.camera_on_btn);





        camera_on_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //촬영버튼을 눌렀을때 실행되는부분
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//이미지를 촬영하는 기본카메라  띄우는 구문

                if(intent.resolveActivity(getActivity().getPackageManager())!=null){
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    }catch (IOException e){

                    }
                    if(photoFile!=null){
                        photoUri= (Uri) FileProvider.getUriForFile(getActivity().getApplicationContext(),getActivity().getPackageName(),photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                    }

                }


            }
        });

        camera_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),format_date,Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }


    //사진촬영을 위한 부분

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
        String imageFileName ="TEST_"+timeStamp+"_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_IMAGE_CAPTURE&&resultCode==getActivity().RESULT_OK){
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
            SimpleDateFormat format_info = new SimpleDateFormat("yyyy MM dd HH:mm");
            format_date = format_info.format(cur_date);

            camera_fragment_img.setImageBitmap(rotate(bitmap,exifDegree));

            /*Intent intent99  = new Intent(getActivity(), TestAct.class);
            intent99.putExtra("phurl",imageFilePath);
            intent99.putExtra("locat",format_date);
            startActivity(intent99);*/
            Bundle bundle = new Bundle();
            bundle.putString("phurl", imageFilePath);
            bundle.putString("locat",format_date);



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
            Toast.makeText(getActivity().getApplicationContext(), "허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {//거절됬을떄 일어나는 액션
            Toast.makeText(getActivity().getApplicationContext(), "거절됨", Toast.LENGTH_SHORT).show();
        }
    };
}



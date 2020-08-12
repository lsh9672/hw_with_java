package com.example.winter_project;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelDataType;
import com.google.firebase.ml.custom.FirebaseModelInputOutputOptions;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.ml.custom.FirebaseModelInterpreter;
import com.google.firebase.ml.custom.FirebaseModelInterpreterOptions;
import com.google.firebase.ml.custom.FirebaseModelOutputs;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AnalyticFragment extends Fragment {
    private View view;
    private static final int REQUEST_CODE=1;
    ImageView camera_img;
    FirebaseStorage storage;
    Button btn_image_analytic,btn_save_analytic;
    TextView camera_time,car_kind,camera_location3,camera_location4,car_acc,fragment_unknown,uri_text;
    private Uri d1;
    int checking=0;
    private String imagePath;
    private DatabaseReference mdb;
    private FirebaseDatabase database;
    FirebaseAuth auth;
    //FirebaseRemoteModel cloudSource;
    FirebaseCustomRemoteModel remoteModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.analytic_fragment,container,false);


        mdb= FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        fragment_unknown=(TextView)view.findViewById(R.id.fragment_unknown);
        camera_time=(TextView)view.findViewById(R.id.camera_time);
        storage= FirebaseStorage.getInstance();
        camera_location3=(TextView)view.findViewById(R.id.camera_location3);
        camera_location4=(TextView)view.findViewById(R.id.camera_location4);
        car_kind=(TextView)view.findViewById(R.id.car_kind);
        car_acc=(TextView)view.findViewById(R.id.car_acc);
        uri_text=(TextView)view.findViewById(R.id.uri_text);


        camera_img = (ImageView)view.findViewById(R.id.camera_img);
        camera_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
        btn_image_analytic=(Button)view.findViewById(R.id.btn_image_analytic);
        btn_image_analytic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d1 = Uri.parse(uri_text.getText().toString());
                try {
                    image_convert();
                } catch (FirebaseMLException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_save_analytic=(Button)view.findViewById(R.id.btn_save_analytic);
        btn_save_analytic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileupload();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==RESULT_OK){
               try{
                    InputStream in = getActivity().getContentResolver().openInputStream((data.getData()));
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    uri_text.setText(data.getData().toString());
                    camera_img.setImageBitmap(img);
                    car_acc.setText("");
                    car_kind.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
              // imagePath=getPath(d1);


            }
        }
        camera_time.setText("알수없음");
        camera_location3.setText("");
        camera_location4.setText("");
        fragment_unknown.setText("알수없음");

    }

    private InputStream getResources(String s){
        return null;
    }
    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(index);
    }

    public void fileupload(){
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        StorageReference storageRef = storage.getReference(); //firebase 스토리지 주소
        //Uri file = Uri.fromFile(new File(getPath(d1)));
        final StorageReference riversRef = storageRef.child("images/"+date.toString());// 스토리지에 child안에 이름으로 올라감
        UploadTask uploadTask = riversRef.putFile(d1);//스토리지에 업로드 하는 부분
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }//업로드 실패시 오류출력

                // Continue with the task to get the download URL
                return riversRef.getDownloadUrl();//이미지를 가져오기위해서 Url주소를 가져옴
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String downloadUrl = downloadUri.toString();
                    Toast.makeText(getActivity(), "업로드 성공",Toast.LENGTH_SHORT).show();

                    User user_make = new User();
                    user_make.imageUrl = downloadUrl;
                    user_make.capture_time = camera_time.getText().toString();
                    user_make.car= car_kind.getText().toString();
                    user_make.latitude=camera_location3.getText().toString();
                    user_make.longtitude=camera_location4.getText().toString();
                    user_make.userid = auth.getCurrentUser().getEmail();
                    user_make.image_delete_name =date.toString();
                    database.getReference().child(auth.getCurrentUser().getUid()).push().setValue(user_make);
                } else {
                    Toast.makeText(getActivity(), "업로드 실패",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void image_convert() throws FirebaseMLException {

        //ML kit custom을 이용해서 모델을 연결
        remoteModel = new FirebaseCustomRemoteModel.Builder("car").build();
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .requireWifi()
                .build();
        FirebaseModelManager.getInstance().download(remoteModel,conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });


        FirebaseModelInterpreter interpreter;
            FirebaseModelInterpreterOptions options =
                    new FirebaseModelInterpreterOptions.Builder(remoteModel).build();
            interpreter = FirebaseModelInterpreter.getInstance(options);



        //입력과 출력 구성
            FirebaseModelInputOutputOptions inputOutputOptions =
                    new FirebaseModelInputOutputOptions.Builder()
                            .setInputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 224, 224, 3})
                            .setOutputFormat(0, FirebaseModelDataType.FLOAT32, new int[]{1, 6})
                            .build();


        //입력 값을 변환하는 부분
        BitmapDrawable bi = (BitmapDrawable)((ImageView)view.findViewById(R.id.camera_img)).getDrawable();
        Bitmap bitmap = bi.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true);

        int batchNum = 0;
        float[][][][] input = new float[1][224][224][3];
        for (int x = 0; x < 224; x++) {
            for (int y = 0; y < 224; y++) {
                int pixel = bitmap.getPixel(x, y);
                // Normalize channel values to [-1.0, 1.0]. This requirement varies by
                // model. For example, some models might require values to be normalized
                // to the range [0.0, 1.0] instead.
                input[batchNum][x][y][0] = (Color.red(pixel) - 127) / 128.0f;
                input[batchNum][x][y][1] = (Color.green(pixel) - 127) / 128.0f;
                input[batchNum][x][y][2] = (Color.blue(pixel) - 127) / 128.0f;
            }
        }

        FirebaseModelInputs inputs = new FirebaseModelInputs.Builder()
                .add(input)  // add() as many input arrays as your model requires
                .build();
        interpreter.run(inputs, inputOutputOptions)
                .addOnSuccessListener(
                        new OnSuccessListener<FirebaseModelOutputs>() {
                            @Override
                            public void onSuccess(FirebaseModelOutputs result) {
                                float[][] output = result.getOutput(0);
                                float[] probabilities = output[0];
                                try {
                                    result_ar(probabilities);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
    }
    private void result_ar(float[] probabilities) throws IOException {
        AssetManager am = getContext().getAssets();
        String [] car_name=new String[probabilities.length];
        double max_prob=0.0;
        int prob_index=0;
        int accuracy;
        BufferedReader reader = new BufferedReader(new InputStreamReader(am.open("image_label.txt")));
        for (int i = 0; i < probabilities.length; i++) {
            String label = reader.readLine();
            car_name[i]=label;
            if(max_prob<=probabilities[i]){
                max_prob = probabilities[i];
                prob_index=i;
            }
            Log.i("MLKit", String.format("%s: %1.4f",label, probabilities[i]));
        }
       accuracy=(int)(probabilities[prob_index]*100);

       car_kind.setText(car_name[prob_index]);
       car_acc.setText("("+accuracy+"%"+")");
    }

}

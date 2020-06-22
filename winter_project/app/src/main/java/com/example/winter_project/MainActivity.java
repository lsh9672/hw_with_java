package com.example.winter_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth auth;//파이어 베이스 인증 객체
    private SignInButton btn_google_login;
    private GoogleApiClient googleApiClient;//구글 API클라이언트 객체
    private static final int REQ_SIGN_GOOGLE= 100;//구글 로그인 결과 코드
    private CallbackManager mCallbackManager; //페이스북 로그인을 위한 콜백 매니저
    private FirebaseAuth.AuthStateListener mAuthListener;
    private LoginButton loginButton;

    Button login_join_btn,login_btn;
    String id1,password1;// 로그인부분의 이메일과 패스워드를 문자열로 받을 변수명
    EditText login_id,login_pw;// 로그인 부분의 이메일과 패스워드를 입력하는 부분


    @Override
    protected void onCreate(Bundle savedInstanceState) { //앱이 실행될때 처음 수행되는 곳
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
        auth = FirebaseAuth.getInstance();//파이어베이스 인증 객체 초기
        btn_google_login=findViewById(R.id.btn_google_login);
        btn_google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test1","test1");
                Intent intent =Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                Log.e("test2","test2");
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
                Log.e("test3","test3");
            }
        });
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton =(LoginButton) findViewById(R.id.btn_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(MainActivity.this,Core.class);
                    startActivity(intent);
                    finish();
                }else{

                }
            }
        };


        login_join_btn=(Button)findViewById(R.id.login_join_btn);
        login_join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        login_id=(EditText)findViewById(R.id.login_id);
        login_pw=(EditText)findViewById(R.id.login_pw);
        login_btn = (Button)findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id1=login_id.getText().toString();
                password1=login_pw.getText().toString();
                loginUser(id1,password1);
            }
        });


    }
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "로그인 실패",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }

                });
    }//페이스북 로그인
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 로그인 인증을 요청했을때 결과 값을 되돌려 받는곳
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SIGN_GOOGLE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess() == true){ //true를 생략해도됨, 인증결과가 성공적이면
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 구글로그인 정보를 담고 있습니다(닉네임,프로필사진Url,이메일 주소등)
                resultLogin(account);//로그인 결과 값 출력 수행하라는 메소드

            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {//첫번째 인자로 엑티비티가 들어가야됨(여기서는 this)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//로그인에 실제로 성공했는지 물어보는 곳
                        if(task.isSuccessful()==true){//로그인이 성공했으면
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Core.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{//로그인이 실패 했으면
                            Toast.makeText(MainActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }//구글 로그인
    private void loginUser(String email,String password){
        if((email.length()>0)&&(password.length()>0)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = auth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException().toString()!=null) {
                                    Toast.makeText(MainActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        }else{
            Toast.makeText(MainActivity.this, "이메일 또는 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();

        }
    }//이메일로그인

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onStart(){
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener !=null){
            auth.removeAuthStateListener(mAuthListener);

        }

    }

}




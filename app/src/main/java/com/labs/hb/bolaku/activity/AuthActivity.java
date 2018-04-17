package com.labs.hb.bolaku.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.labs.hb.bolaku.MainActivity;
import com.labs.hb.bolaku.R;
import com.labs.hb.bolaku.util.FacebookSDKMethods;

import java.util.HashMap;

/**
 * Created by BwX on 3/29/2018.
 */

public class AuthActivity extends AppCompatActivity {

    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreateBtn;


    private Toolbar mToolbar;

    private DatabaseReference mDatabase;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;
    LoginButton facebookLogInButton;


    Button facebookLoginButtonDelegate;

    CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrasi_activity);

        //Toolbar Set
       // mToolbar = (Toolbar) findViewById(R.id.);
        //setSupportActionBar(mToolbar);
       // getSupportActionBar().setTitle("Create Account");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRegProgress = new ProgressDialog(this);



        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields


        facebookLogInButton=(LoginButton) findViewById(R.id.facebook_login_button);
        facebookLoginButtonDelegate=(Button) findViewById(R.id.facebook_login_button_delegate);


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                mRegProgress.dismiss();
                if (firebaseUser != null) {
                    Intent mainIntent = new Intent(AuthActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        mCallbackManager = CallbackManager.Factory.create();
        facebookLogInButton.setReadPermissions("public_profile");
        facebookLoginButtonDelegate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                initFacebookAuthentication();
            }
        });
        facebookLogInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user==null) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }else{
                    Intent mainIntent = new Intent(AuthActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }

        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
    private void handleFacebookAccessToken(final AccessToken token) {
        showProcessProgressDialog("Tunggu Sebentar!");
        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this,"Error", Toast.LENGTH_LONG).show();
                        }else {
                            //FirebaseAuth.getInstance();
                            FacebookSDKMethods.getUserInfoFromFacebook(token, AuthActivity.this);


                            dismissProgressDialog();
                        }
                    }
                });
    }




    private void register_user(final String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("status", "Hi Chat kuy.");
                    userMap.put("image", "default");
                    userMap.put("thumb_image","default");
                    if(!TextUtils.isEmpty(device_token)) {
                        userMap.put("device_token", device_token);
                    }else{
                        userMap.put("device_token", "user_token ");
                    }
                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                //mRegProgress.dismiss();

//                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
//                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(mainIntent);
//                                finish();

                            }

                        }
                    });


                } else {

                    mRegProgress.hide();
                    Toast.makeText(AuthActivity.this, "Cannot Sign in. Please check the form and try again.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private void initFacebookAuthentication() {

        facebookLogInButton.performClick();
    }
    private void showProcessProgressDialog(String progressMessage) {
        mRegProgress.setMessage(progressMessage);
        if (!mRegProgress.isShowing()) {
            mRegProgress.show();
        }
    }

    private void dismissProgressDialog() {
        if (mRegProgress.isShowing()) {
            mRegProgress.dismiss();
        }
    }

}

package com.labs.hb.bolaku.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.labs.hb.bolaku.model.Users;


import org.json.JSONObject;


import java.util.HashMap;




/**
 * Created by airjaw on 2/12/17.
 */

// bitmap BasicImageDownloader example code here:
// https://github.com/vad-zuev/ImageDownloader/blob/master/app/src/main/java/com/so/example/activities/ImageActivity.java

public class FacebookSDKMethods {
    private static final String TAG = "FacebookSDKMethods";

    private static final FacebookSDKMethods instance = new FacebookSDKMethods();
    public static FacebookSDKMethods getInstance() {
        return instance;
    }
    public static String emailUser;
    private static  DatabaseReference mUserDatabase;
    private static DatabaseReference mDatabase;
    private static FirebaseFirestore firebaseFirestore;

    public  static void getUserInfoFromFacebook(AccessToken accessToken, final Context context) {

        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        FacebookRequestError error = response.getError();
                        if (error == null) {
                            Log.i("JSON", object.toString());
                            try {

                                String gender = object.getString("gender");
                                String firstName = object.getString("first_name");
                                String facebookID = object.getString("id");
                                uploadFBUserInfo(context,firstName,gender);
                            }


                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        else {
                            Log.i("FacebookSDK", "Error code: " + error.getErrorCode());
                            Log.i("FacebookSDK", "Error message: " + error.getErrorMessage());
                        }


                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, gender, picture, birthday, first_name, last_name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private  static void uploadFBUserInfo(final Context context,String display_name, String gender) {
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference mRoot= FirebaseDatabase.getInstance().getReference();
        //userFacebookInfoRef
        DatabaseReference userIDRef = mRoot.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mUserDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("device_token").setValue(deviceToken);
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        String device_token = FirebaseInstanceId.getInstance().getToken();
        Users users=new Users();
        users.setName(display_name);
        users.setToken_device(device_token);
        users.setGender(gender);
        firebaseFirestore.collection("users").document(uid)
                .set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });



        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("name", display_name);
        userMap.put("status", "Hi Chat kuy.");
        userMap.put("image", "default");
        userMap.put("thumb_image", "default");
        userMap.put("device_token", device_token);
        userMap.put("gender",gender);


    }


}

package com.labs.hb.bolaku.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.labs.hb.bolaku.model.Users;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by BwX on 4/4/2018.
 */

public class CommonUtil {
    public static String getId(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String path = uri.getPath();
        String idStr = path.substring(path.lastIndexOf('/') + 1);
        return idStr;
    }
    public  static Users getUserById(String userId, FirebaseFirestore firebaseFirestore){
        final Users[] users = {null};
            firebaseFirestore.collection("users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                         users[0] = doc.toObject(Users.class);
                    }
                }
            });
            return users[0];
    }
}

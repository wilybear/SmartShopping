package com.example.smartshopping.model;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class AppRepository {

    private Application application;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> loggedOutMutableLiveData;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    public AppRepository(Application application) {
        this.application = application;
        firebaseAuth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
        loggedOutMutableLiveData = new MutableLiveData<>();
        db = FirebaseFirestore.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutMutableLiveData.postValue(false);
        }
    }

    public void signUp(String email, String password, String birthday,String gender) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                            if(firebaseAuth.getCurrentUser().getEmail() != null) {
                                Map<String, Object> user = new HashMap<>();
                                user.put("birthday", birthday);
                                user.put("gender",gender );
                               db.collection("User").document(firebaseAuth.getCurrentUser().getEmail()).set(user)
                                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {
                                               Toast.makeText(application,"Saved!",Toast.LENGTH_LONG).show();
                                           }
                                       })
                                       .addOnFailureListener(new OnFailureListener() {
                                           @Override
                                           public void onFailure(@NonNull Exception e) {
                                               Log.w("fb", "Error adding document", e);
                                           }
                                       });
                            }else{
                                Toast.makeText(application, "CloudStore failed ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(application, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    public void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getExecutor(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                        } else {
                            Toast.makeText(application, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void logOut(){
        firebaseAuth.signOut();
        loggedOutMutableLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutMutableLiveData() {
        return loggedOutMutableLiveData;
    }

    private Executor getExecutor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            return application.getMainExecutor();
        }else{
            Context context = application.getApplicationContext();
            return ContextCompat.getMainExecutor(context);
        }
    }
}

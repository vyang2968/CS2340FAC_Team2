package com.example.sprintproject.database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthService {
    private static final String TAG = "AuthService";
    private FirebaseAuth auth;
    private FirebaseUser user;
    private static AuthService instance;

    private AuthService() {
        auth = FirebaseAuth.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }

        return instance;
    }

    public void createUser(String email, String password, Activity activity) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUser:success");
                            setUser(auth.getCurrentUser());
                        } else {
                            Log.d(TAG, "createUser:failed", task.getException());
                        }
                    }
                });
    }

    public void signInUser(String email, String password, Activity activity) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUser:success");
                            setUser(auth.getCurrentUser());
                        } else {
                            Log.d(TAG, "createUser:failed", task.getException());
                        }
                    }
                });    }

    public void logOutUser() {
        auth.signOut();
        Log.d(TAG, "user logged out");
    }

    private void setUser(FirebaseUser user) {
        this.user = user;
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public boolean isUserLoggedIn() {
        return getUser() != null;
    }
}

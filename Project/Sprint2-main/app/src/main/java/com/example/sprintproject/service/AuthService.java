package com.example.sprintproject.service;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.security.auth.callback.Callback;

public class AuthService {
    private static final String TAG = "AuthService";
    private FirebaseAuth auth;
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

    public Task<AuthResult> registerUser(String email, String password, DataCallback<FirebaseUser> callback) {
        return auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "registerUser:success");
                        callback.onSuccess(getUser());
                    } else {
                        Log.d(TAG, "registerUser:failed", task.getException());
                        callback.onError(task.getException());
                    }
                });
    }

    public Task<AuthResult> logInUser(String email, String password, DataCallback<FirebaseUser> callback) {
        return auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "login:success");
                        callback.onSuccess(getUser());
                    } else {
                        Log.d(TAG, "login:failed", task.getException());
                        callback.onError(task.getException());
                    }
                });
    }

    public void logOutUser() {
        auth.signOut();
        Log.d(TAG, "user logged out");
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public boolean isUserLoggedIn() {
        return getUser() != null;
    }
}

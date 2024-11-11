package com.example.sprintproject.service;


import android.util.Log;



import com.example.sprintproject.model.User;
import com.example.sprintproject.utils.DataCallback;

import com.google.android.gms.tasks.Task;

import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class AuthService {
    private static final String TAG = "AuthService";
    private final FirebaseAuth auth;
    private static AuthService instance;
    private final UserService userService;

    private AuthService() {
        auth = FirebaseAuth.getInstance();
        userService = UserService.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }

        return instance;
    }

    public Task<AuthResult> registerUser(
            String email,
            String password,
            DataCallback<FirebaseUser> callback) {
        return auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "registerUser:success");
                        setCurrentUser();
                        callback.onSuccess(getUser());
                    } else {
                        Log.d(TAG, "registerUser:failed", task.getException());
                        callback.onError(task.getException());
                    }
                });
    }

    public Task<AuthResult> logInUser(
            String email,
            String password,
            DataCallback<FirebaseUser> callback) {
        return auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "login:success");
                        setCurrentUser();
                        callback.onSuccess(getUser());
                    } else {
                        Log.d(TAG, "login:failed", task.getException());
                        callback.onError(task.getException());
                    }
                });
    }

    public void setCurrentUser() {
        Log.i(TAG, "setting current user...");
        String userId = getUser().getUid();
        userService.getUser(userId, new DataCallback<User>() {
            @Override
            public void onSuccess(User result) {
                userService.setCurrentUser(result);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, e.toString());
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

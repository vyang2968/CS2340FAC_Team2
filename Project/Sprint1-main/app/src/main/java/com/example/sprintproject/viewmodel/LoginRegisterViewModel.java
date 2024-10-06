package com.example.sprintproject.viewmodel;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.database.AuthService;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    private MutableLiveData<String> errorMsg;
    private boolean emailValid;
    private boolean passwordValid;

    public LoginViewModel() {
        this.errorMsg = new MutableLiveData<>();
        this.errorMsg.setValue("");
        this.emailValid = false;
        this.passwordValid = false;
    }

    public boolean login(String email, String password, Activity activity) {
        AuthService authService = AuthService.getInstance();

        return authService.logInUser(email, password, activity);
    }

    public void setEmailValid(boolean emailValid) {
        Log.i(TAG, "emailValid:" + emailValid);
        this.emailValid = emailValid;
    }

    public void setPasswordValid(boolean passwordValid) {
        Log.i(TAG, "passwordValid:" + passwordValid);
        this.passwordValid = passwordValid;
    }

    public boolean canBeSubmitted() {
        Log.i(TAG, "canBeSubmitted:" + String.valueOf(emailValid && passwordValid));
        return emailValid && passwordValid;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg.setValue(errorMsg);
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
}

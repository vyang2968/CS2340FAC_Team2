package com.example.sprintproject.viewmodel;

import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.LiveData;

import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AuthViewModel {
    private static final String TAG = "LoginViewModel";
    private AuthService authService;
    private UserService userService;

    public LoginViewModel() {
        super();
        this.errorMsg.setValue("");
        this.authSuccess.setValue(false);
        this.authService = AuthService.getInstance();
        this.userService = UserService.getInstance();
    }

    public void login(String email, String password) {
        if (canBeSubmitted()) {
            authService.logInUser(email, password, new DataCallback<FirebaseUser>() {
                @Override
                public void onSuccess(FirebaseUser result) {
                    logInfo("login:success");
                    authSuccess.setValue(true);
                }

                @Override
                public void onError(Exception e) {
                    logInfo("login:failed");
                    authSuccess.setValue(false);
                    errorMsg.setValue("Login failed. Please check your email and password.");
                }
            });
        } else {
            errorMsg.setValue("Please fix errors and try again");
        }
    }

    public boolean validatePassword(EditText passwordInput) {
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Field cannot be empty");
        } else {
            passwordInput.setError(null);
        }

        passwordValid = passwordInput.getError() == null;
        logInfo("passwordValid:" + passwordValid);

        return passwordValid;
    }

    public boolean canBeSubmitted() {
        logInfo("canBeSubmitted:" + String.valueOf(emailValid && passwordValid));
        return emailValid && passwordValid;
    }

    public LiveData<Boolean> getAuthSuccess() {
        return authSuccess;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

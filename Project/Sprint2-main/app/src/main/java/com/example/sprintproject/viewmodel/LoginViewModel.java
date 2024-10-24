package com.example.sprintproject.viewmodel;

import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    private MutableLiveData<Boolean> authSuccess;
    private MutableLiveData<String> errorMsg;
    private boolean emailValid;
    private boolean passwordValid;
    private AuthService authService;
    private UserService userService;

    public LoginViewModel() {
        this.errorMsg = new MutableLiveData<>();
        this.errorMsg.setValue("");
        this.authSuccess = new MutableLiveData<>();
        this.authSuccess.setValue(false);
        this.emailValid = false;
        this.passwordValid = false;
        this.authService = AuthService.getInstance();
        this.userService = UserService.getInstance();
    }

    public void login(String email, String password) {
        if (canBeSubmitted()) {
            authService.logInUser(email, password, new DataCallback<FirebaseUser>() {
                @Override
                public void onSuccess(FirebaseUser result) {
                    Log.i(TAG, "login:success");
                    authSuccess.setValue(true);
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "login:failed");
                    authSuccess.setValue(false);
                    errorMsg.setValue("Login failed. Please check your email and password.");
                }
            });
        } else {
            errorMsg.setValue("Please fix errors and try again");
        }
    }

    public boolean validateEmail(EditText emailInput) {
        String email = emailInput.getText().toString().trim();
        if (email.isEmpty()) {
            emailInput.setError("Field cannot be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Field needs to be a valid email");
        } else {
            emailInput.setError(null);
        }

        emailValid = emailInput.getError() == null;
        Log.i(TAG, "emailValid:" + emailValid);

        return emailValid;
    }

    public boolean validatePassword(EditText passwordInput) {
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Field cannot be empty");
        } else {
            passwordInput.setError(null);
        }

        passwordValid = passwordInput.getError() == null;
        Log.i(TAG, "passwordValid:" + passwordValid);

        return passwordValid;
    }

    public boolean canBeSubmitted() {
        Log.i(TAG, "canBeSubmitted:" + (emailValid && passwordValid));
        return emailValid && passwordValid;
    }

    public LiveData<Boolean> getAuthSuccess() {
        return authSuccess;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
}

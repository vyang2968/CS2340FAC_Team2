package com.example.sprintproject.viewmodel;

import android.util.Patterns;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public abstract class AuthViewModel extends ViewModel implements LogSource {
    protected MutableLiveData<Boolean> authSuccess;
    protected MutableLiveData<String> errorMsg;
    protected boolean emailValid;
    protected boolean passwordValid;

    public AuthViewModel() {
        this.authSuccess = new MutableLiveData<>();
        this.errorMsg = new MutableLiveData<>();
        this.emailValid = false;
        this.passwordValid = false;
    }

    public boolean validateEmail(EditText emailInput) {
        String email = emailInput.getText().toString();

        try {
            validateEmailStringOrThrow(email);

            emailInput.setError(null);
        } catch (IllegalArgumentException e) {

            emailInput.setError(e.getMessage());
        }

        emailValid = emailInput.getError() == null;
        logInfo("emailValid:" + emailValid);

        return emailValid;
    }

    public static void validateEmailStringOrThrow(String emailInput) {
        String email = emailInput.trim();

        if (email.isEmpty()) {
            throw new IllegalArgumentException("Field cannot be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new IllegalArgumentException("Field needs to be a valid email");
        }
    }
}

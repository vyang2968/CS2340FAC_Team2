package com.example.sprintproject.viewmodel;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sprintproject.model.User;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends AuthViewModel {
    private static final String TAG = "RegisterViewModel";
    private final MutableLiveData<Boolean> authSuccess;
    private final MutableLiveData<Boolean> createSuccess;
    private final MutableLiveData<Boolean> proceed;
    private final MutableLiveData<String> errorMsg;
    private final boolean emailValid;
    private boolean passwordValid;
    private boolean passwordsMatch;
    private final AuthService authService;
    private final UserService userService;

    public RegisterViewModel() {
        this.errorMsg = new MutableLiveData<>();
        this.errorMsg.setValue("");
        this.authSuccess = new MutableLiveData<>();
        this.authSuccess.setValue(false);
        this.createSuccess = new MutableLiveData<>();
        this.createSuccess.setValue(false);
        this.proceed = new MutableLiveData<>();
        this.proceed.setValue(false);
        this.emailValid = false;
        this.passwordValid = false;
        this.authService = AuthService.getInstance();
        this.userService = UserService.getInstance();
    }

    public void register(String email, String password) {
        if (canBeSubmitted()) {
            authService.registerUser(email, password, new DataCallback<FirebaseUser>() {
                @Override
                public void onSuccess(FirebaseUser result) {
                    logInfo("register:success");
                    authSuccess.setValue(true);
                }
                @Override
                public void onError(Exception e) {
                    logInfo("register:failed");
                    authSuccess.setValue(false);
                    errorMsg.setValue("Registration failed. Please try again");
                }
            });
        }
    }

    public void createUser() {
        if (authService.isUserLoggedIn()) {
            FirebaseUser currUser = authService.getUser();
            User user = new User();

            user.setId(currUser.getUid());
            user.setEmail(currUser.getEmail());
            userService.addUser(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    logInfo("createUser:success");
                    createSuccess.setValue(true);
                } else {
                    logInfo("createUser:fail");
                    createSuccess.setValue(false);
                }
            });
        }
    }

    public void approveProceed() {
        if (authSuccess.getValue() && createSuccess.getValue()) {
            proceed.setValue(true);
        }
    }

    public boolean validatePassword(EditText passwordInput) {
        String password = passwordInput.getText().toString();

        if (password.isEmpty()) {
            passwordInput.setError("Field cannot be empty");
        } else if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
        } else {
            passwordInput.setError(null);
        }

        passwordValid = passwordInput.getError() == null;
        logInfo("passwordValid:" + passwordValid);

        return passwordValid;
    }

    public boolean validatePasswordsMatch(EditText passwordInput, EditText confirmPasswordInput) {
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (!password.equals(confirmPassword)) {
            passwordInput.setError("Passwords do not match");
            confirmPasswordInput.setError("Passwords do not match");
        } else {
            passwordInput.setError(null);
            confirmPasswordInput.setError(null);
        }

        passwordsMatch = (passwordInput.getError() == null
                            && confirmPasswordInput.getError() == null);
        return passwordsMatch;
    }

    public boolean canBeSubmitted() {
        logInfo("canBeSubmitted:" + (emailValid && passwordValid && passwordsMatch));
        return emailValid && passwordValid && passwordsMatch;
    }
    
    public LiveData<Boolean> getProceed() {
        return proceed;
    }

    public LiveData<Boolean> getAuthSuccess() {
        return authSuccess;
    }

    public LiveData<Boolean> getCreateSuccess() {
        return createSuccess;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

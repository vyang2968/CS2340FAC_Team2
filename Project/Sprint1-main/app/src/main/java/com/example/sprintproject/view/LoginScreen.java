package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;


import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.database.AuthService;
import com.example.sprintproject.databinding.ActivityLoginScreenBinding;
import com.example.sprintproject.viewmodel.LoginRegisterViewModel;


public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText emailInput;
    EditText passwordInput;

    Button backButton;
    Button loginButton;
    Button registerButton;

    TextView loginErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        EdgeToEdge.enable(this);

        // TODO: remove in production
        AuthService.getInstance().logOutUser();

        ActivityLoginScreenBinding binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailInput = findViewById(R.id.email_textbox);
        passwordInput = findViewById(R.id.password_textbox);

        backButton = findViewById(R.id.back_button);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        loginErrorText = findViewById(R.id.error_text);

        LoginRegisterViewModel loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setVariable(BR.loginViewModel, loginRegisterViewModel);
        binding.setLifecycleOwner(this);

        backButton.setOnClickListener(view -> {
            Log.i(TAG, "backButton:clicked");
            Intent intent = new Intent(LoginScreen.this, WelcomeScreen.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(view -> {
            Log.i(TAG, "registerButton:clicked");
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            Log.i(TAG, "loginButton:clicked");
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            if (email.isEmpty()) {
                Log.d(TAG, "email:empty");
                emailInput.setError("Field cannot be empty");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Log.d(TAG, "email:invalid");
                emailInput.setError("Field needs to be a valid email");
            } else {
                Log.i(TAG, "email:valid");
                emailInput.setError(null);
            }

            if (password.isEmpty()) {
                Log.d(TAG, "password:empty");
                passwordInput.setError("Field cannot be empty");
            } else {
                Log.i(TAG, "password:valid");
                passwordInput.setError(null);
            }

            loginRegisterViewModel.setEmailValid(emailInput.getError() == null);
            loginRegisterViewModel.setPasswordValid(passwordInput.getError() == null);

            if (!AuthService.getInstance().isUserLoggedIn()) {
                if (loginRegisterViewModel.canBeSubmitted()) {
                    loginErrorText.setVisibility(View.GONE);

                    loginRegisterViewModel.login(email, password, this);
                } else {
                    loginRegisterViewModel.setErrorMsg("Please fix fields before submitting");
                    loginErrorText.setVisibility(View.VISIBLE);
                }
            }

            if (AuthService.getInstance().isUserLoggedIn()) {
                Log.i(TAG, "login:successful");
                loginErrorText.setVisibility(View.GONE);
                // TODO: replace Home.class with actual home page
//                Intent intent = new Intent(LoginScreen.this, Home.class);
//                startActivity(intent);
            } /* else {
                Log.i(TAG, "login:unsuccessful");
                loginRegisterViewModel.setErrorMsg("Username or password is incorrect");
                loginErrorText.setVisibility(View.VISIBLE);
            } */
        });
    }
}
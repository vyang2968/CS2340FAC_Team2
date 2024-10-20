package com.example.sprintproject.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;
import com.example.sprintproject.database.AuthService;
import com.example.sprintproject.databinding.ActivityCreateAccountScreenBinding;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;


import com.example.sprintproject.BR;
import com.example.sprintproject.viewmodel.LoginRegisterViewModel;


public class RegisterScreen extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText emailInput;
    private EditText passwordInput;

    private Button backButton;
    private Button registerButton;
    private Button loginButton;

    private TextView loginErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        EdgeToEdge.enable(this);

        AuthService.getInstance().logOutUser();

        ActivityCreateAccountScreenBinding binding =
                ActivityCreateAccountScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailInput = findViewById(R.id.email_textbox);
        passwordInput = findViewById(R.id.password_textbox);

        backButton = findViewById(R.id.back_button);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        loginErrorText = findViewById(R.id.error_text);

        LoginRegisterViewModel loginRegisterViewModel =
                new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        binding.setVariable(BR.loginViewModel, loginRegisterViewModel);
        binding.setLifecycleOwner(this);

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterScreen.this, WelcomeScreen.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(view -> {
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

                    if (!loginRegisterViewModel.login(email, password, this)) {
                        loginRegisterViewModel.create(email, password, this);
                    } else {
                        Log.i(TAG, "User already signed up");
                    }

                } else {
                    loginRegisterViewModel.setErrorMsg("Please fix fields before submitting");
                    loginErrorText.setVisibility(View.VISIBLE);
                }
            }

            if (AuthService.getInstance().isUserLoggedIn()) {
                Log.i(TAG, "create:successful");
                loginErrorText.setVisibility(View.GONE);
                Intent intent = new Intent(RegisterScreen.this, LogisticsScreen.class);
                startActivity(intent);
            }
        });
    }
}

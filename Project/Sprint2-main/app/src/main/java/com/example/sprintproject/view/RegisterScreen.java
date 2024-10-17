package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityRegisterBinding;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.databinding.ActivityLoginScreenBinding;
import com.example.sprintproject.viewmodel.RegisterViewModel;


public class RegisterScreen extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmInput;

    private Button backButton;
    private Button loginButton;
    private Button registerButton;

    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EdgeToEdge.enable(this);

        ActivityRegisterBinding binding =
                ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailInput = findViewById(R.id.email_textbox);
        passwordInput = findViewById(R.id.password_textbox);
        confirmInput = findViewById(R.id.confirm_password_textbox);

        backButton = findViewById(R.id.back_button);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        errorText = findViewById(R.id.error_text);

        RegisterViewModel registerViewModel =
                new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setVariable(BR.registerViewModel, registerViewModel);
        binding.setLifecycleOwner(this);

        registerViewModel.getAuthSuccess().observe(this, authSuccess -> {
            if (authSuccess) {
                errorText.setVisibility(View.GONE);
                registerViewModel.createUser();
            } else {
                errorText.setVisibility(View.VISIBLE);
            }
        });

        registerViewModel.getCreateSuccess().observe(this, createSuccess -> {
            if (createSuccess) {
                errorText.setVisibility(View.GONE);
            } else {
                errorText.setVisibility(View.VISIBLE);
            }
        });

        registerViewModel.getProceed().observe(this, authSuccess -> {
            if (authSuccess) {
                errorText.setVisibility(View.GONE);
                startActivity(new Intent(this, LogisticsScreen.class));
                finish();
            } else {
                errorText.setVisibility(View.VISIBLE);
            }
        });

        registerViewModel.getErrorMsg().observe(this, error -> {
            if (!error.isEmpty()) {
                errorText.setVisibility(View.VISIBLE);
            } else {
                errorText.setVisibility(View.GONE);
            }
        });

        if (AuthService.getInstance().isUserLoggedIn()) {
            startActivity(new Intent(RegisterScreen.this, LogisticsScreen.class));
        }

        backButton.setOnClickListener(view -> {
            Log.i(TAG, "backButton:clicked");
            Intent intent = new Intent(RegisterScreen.this, WelcomeScreen.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(view -> {
            Log.i(TAG, "registerButton:clicked");

            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            registerViewModel.validateEmail(emailInput);
            registerViewModel.validatePassword(passwordInput);
            registerViewModel.validatePassword(confirmInput);
            registerViewModel.validatePasswordsMatch(passwordInput, confirmInput);

            registerViewModel.register(email, password);
        });

        loginButton.setOnClickListener(view -> {
            Log.i(TAG, "loginButton:clicked");
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });
    }
}
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
import android.widget.TextView;


import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.databinding.ActivityLoginScreenBinding;
import com.example.sprintproject.viewmodel.LoginViewModel;


public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText emailInput;
    private EditText passwordInput;

    private Button backButton;
    private Button loginButton;
    private Button registerButton;

    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        EdgeToEdge.enable(this);

        ActivityLoginScreenBinding binding =
                ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailInput = findViewById(R.id.email_textbox);
        passwordInput = findViewById(R.id.password_textbox);

        backButton = findViewById(R.id.back_button);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        errorText = findViewById(R.id.error_text);

        LoginViewModel loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setVariable(BR.loginViewModel, loginViewModel);
        binding.setLifecycleOwner(this);

        loginViewModel.getAuthSuccess().observe(this, authSuccess -> {
            if (authSuccess) {
                errorText.setVisibility(View.GONE);
                startActivity(new Intent(LoginScreen.this, LogisticsScreen.class));
                finish();
            } else {
                errorText.setVisibility(View.VISIBLE);
            }
        });

        loginViewModel.getErrorMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (!error.isEmpty()) {
                    errorText.setVisibility(View.VISIBLE);
                } else {
                    errorText.setVisibility(View.GONE);
                }
            }
        });

        if (AuthService.getInstance().isUserLoggedIn()) {
            startActivity(new Intent(LoginScreen.this, LogisticsScreen.class));
        }

        backButton.setOnClickListener(view -> {
            Log.i(TAG, "backButton:clicked");
            Intent intent = new Intent(LoginScreen.this, WelcomeScreen.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            Log.i(TAG, "loginButton:clicked");

            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            loginViewModel.validateEmail(emailInput);
            loginViewModel.validatePassword(passwordInput);

            loginViewModel.login(email, password);
        });

        registerButton.setOnClickListener(view -> {
            Log.i(TAG, "registerButton:clicked");
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
        });
    }
}


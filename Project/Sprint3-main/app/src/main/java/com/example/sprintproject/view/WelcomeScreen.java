package com.example.sprintproject.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.sprintproject.R;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.viewmodel.WelcomeViewModel;

public class WelcomeScreen extends AppCompatActivity {
    private WelcomeViewModel welcomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        welcomeViewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
        TextView welcomeTextSmall = findViewById(R.id.welcome_text_small);
        Button startButton = findViewById(R.id.start_button);
        Button quitButton = findViewById(R.id.quit_button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeViewModel.onStartClicked();
                Intent intent = new Intent(WelcomeScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeViewModel.onQuitClicked();

                finish();
            }
        });
    }
}
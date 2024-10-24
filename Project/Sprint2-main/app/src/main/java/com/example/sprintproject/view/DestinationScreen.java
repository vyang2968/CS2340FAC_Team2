package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityLoginScreenBinding;
import com.example.sprintproject.viewmodel.DestinationViewModel;
import com.example.sprintproject.viewmodel.LoginViewModel;

public class DestinationScreen extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        EdgeToEdge.enable(this);

        ActivityDestinationScreen binding =
                ActivityDestinationScreen.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);
        binding.setVariable(BR.loginViewModel, destinationViewModel);
        binding.setLifecycleOwner(this);

        ToggleButton logOpenButton = findViewById(0);
        ToggleButton calculateOpenButton = findViewById(0);

        // log area components
        LinearLayout logArea = findViewById(0);
        Button cancelButton = findViewById(0);
        Button submitButton = findViewById(0);
        EditText locationInput = findViewById(0);
        EditText startDateInput = findViewById(0);
        EditText endDateInput = findViewById(0);
        TextView errorDisplay = findViewById(0);

        // calculate area components
        LinearLayout calcArea = findViewById(0);
        Button calculateButton = findViewById(0);
        EditText userStartDateInput = findViewById(0);
        EditText endStartDateInput = findViewById(0);
        EditText durationInput = findViewById(0);

        // result area components
        LinearLayout resultArea = findViewById(0);
        Button resetButton = findViewById(0);

        // populate destinations


        // logOpenButton clicked
        logOpenButton.setOnClickListener(view -> {
            if (logOpenButton.isChecked()) {
                calcArea.setVisibility(View.VISIBLE);

                cancelButton.setOnClickListener(view1 -> {
                    logOpenButton.setChecked(false);
                });

                submitButton.setOnClickListener(view2 -> {
                    destinationViewModel.setLocation(locationInput);
                    destinationViewModel.setStartDate(startDateInput);
                    destinationViewModel.setEndDate(endDateInput);

                    boolean success = destinationViewModel.addDestination();

                    if (success) {
                        errorDisplay.setVisibility(View.GONE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Successfully submitted!");
                        builder.create();
                    } else {
                        errorDisplay.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                calcArea.setVisibility(View.GONE);
            }
        });

        calculateOpenButton.setOnClickListener(view -> {

        });
    }
}
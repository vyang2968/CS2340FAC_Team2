package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.viewmodel.DestinationViewModel;

import java.util.Date;
import java.util.List;

public class DestinationScreen extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);
        EdgeToEdge.enable(this);

//        ActivityDestinationScreen binding =
//                ActivityDestinationScreen.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        setupNavBar();

        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);
//        binding.setVariable(BR.destViewModel, destinationViewModel);
//        binding.setLifecycleOwner(this);

        ToggleButton logOpenButton = findViewById(R.id.logOpenButton);
        ToggleButton calculateOpenButton = findViewById(R.id.calculateOpenButton);

        // log area components
        LinearLayout logArea = findViewById(R.id.logArea);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button submitButton = findViewById(R.id.submitButton);
        EditText locationInput = findViewById(R.id.locationInput);
        EditText destStartDateInput = findViewById(R.id.destStartDateInput);
        EditText destEndDateInput = findViewById(R.id.destEndDateInput);
        TextView logErrorDisplay = findViewById(R.id.logErrorDisplay);

        // calculate area components
        LinearLayout calcArea = findViewById(R.id.calcArea);
        Button calculateButton = findViewById(R.id.calculateButton);
        EditText userStartDateInput = findViewById(R.id.userStartDateInput);
        EditText userEndDateInput = findViewById(R.id.userEndDateInput);
        EditText durationInput = findViewById(R.id.durationInput);
        TextView calcErrorDisplay = findViewById(R.id.calcErrorDisplay);

        // result area components
        LinearLayout resultArea = findViewById(R.id.resultArea);
        Button resetButton = findViewById(R.id.resetButton);

        // populate destinations
        LinearLayout destArea = findViewById(R.id.destArea);
        List<Destination> destinations = destinationViewModel.getDestinations();

        // submitted observer
        destinationViewModel.getSubmitted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean bool) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DestinationScreen.this);
                builder.setMessage("Successfully submitted!");
                builder.create();

                if (bool) {
                    builder.show();
                }
            }
        });

        // logOpenButton clicked
        logOpenButton.setOnClickListener(view -> {
            if (logOpenButton.isChecked()) {
                logArea.setVisibility(View.VISIBLE);

                cancelButton.setOnClickListener(view1 -> {
                    logOpenButton.setChecked(false);
                });

                submitButton.setOnClickListener(view2 -> {
                    destinationViewModel.setLocation(locationInput);
                    destinationViewModel.setDestinationStartDate(destStartDateInput);
                    destinationViewModel.setDestinationEndDate(destEndDateInput);

                    boolean success = destinationViewModel.addDestination();
                    destinationViewModel.setSubmitted(success);

                    if (success) {
                        logErrorDisplay.setVisibility(View.GONE);
                    } else {
                        logErrorDisplay.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                logArea.setVisibility(View.GONE);
            }
        });

        calculateOpenButton.setOnClickListener(view -> {
            if (calculateOpenButton.isChecked()) {
                calcArea.setVisibility(View.VISIBLE);

                calculateButton.setOnClickListener(view1 -> {
                    Date start = destinationViewModel.setUserStartDate(userStartDateInput);
                    Date end = destinationViewModel.setUserEndDate(userEndDateInput);
                    long duration = destinationViewModel.dateDifference(start, end);

                    destinationViewModel.setDuration(durationInput, duration);

                    boolean success = destinationViewModel.updateUser();
                    if (success) {
                        calcErrorDisplay.setVisibility(View.GONE);
                    } else {
                        calcErrorDisplay.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                calcArea.setVisibility(View.GONE);
            }
        });
    }
}
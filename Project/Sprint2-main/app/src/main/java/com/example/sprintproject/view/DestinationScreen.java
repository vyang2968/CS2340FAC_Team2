package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

        ToggleButton logOpenButton = findViewById(0);
        ToggleButton calculateOpenButton = findViewById(0);

        // log area components
        LinearLayout logArea = findViewById(0);
        Button cancelButton = findViewById(0);
        Button submitButton = findViewById(0);
        EditText locationInput = findViewById(0);
        EditText destStartDateInput = findViewById(0);
        EditText destEndDateInput = findViewById(0);
        TextView logErrorDisplay = findViewById(0);

        // calculate area components
        LinearLayout calcArea = findViewById(0);
        Button calculateButton = findViewById(0);
        EditText userStartDateInput = findViewById(0);
        EditText userEndDateInput = findViewById(0);
        EditText durationInput = findViewById(0);
        TextView calcErrorDisplay = findViewById(0);

        // TODO: populate destinations
        LinearLayout destArea = findViewById(0);
        List<Destination> destinations = destinationViewModel.getDestinations();

        // submitted observer
        destinationViewModel.getSubmitted().observe(this, bool -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DestinationScreen.this);
            builder.setMessage("Successfully submitted!");
            builder.create();

            if (bool) {
                builder.show();
            }
        });

        // logOpenButton clicked
        logOpenButton.setOnClickListener(view -> {
            if (calculateOpenButton.isChecked()) {
                calcArea.setVisibility(View.GONE);
            }

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
            if (logOpenButton.isChecked()) {
                logArea.setVisibility(View.GONE);
            }

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

                        calcArea.setVisibility(View.GONE);
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
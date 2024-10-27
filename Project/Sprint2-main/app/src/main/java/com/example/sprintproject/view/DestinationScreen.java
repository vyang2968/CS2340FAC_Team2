package com.example.sprintproject.view;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityDestinationBinding;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.viewmodel.DestinationViewModel;

import java.util.Date;
import java.util.List;

public class DestinationScreen extends NavBarScreen {
    public final String TAG = "DestinationScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityDestinationBinding binding = ActivityDestinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);
        binding.setVariable(BR.destViewModel, destinationViewModel);
        binding.setLifecycleOwner(this);

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

        // TODO: populate destinations
        LinearLayout destArea = findViewById(R.id.destArea);
        destinationViewModel.queryForDestinations();
        destinationViewModel.getDestinations().observe(this, destinations -> {
            if (!destinations.isEmpty()) {
                Log.i(TAG, "populating destinations...");
                for (int i = 0; i < 5; i++) {
                    String location = "";
                    String allocatedDays = "";
                    if (i < destinations.size()) {
                        Destination destination = destinations.get(i);
                        location = destination.getDestination();
                        allocatedDays = String.valueOf(destination.getDurationInDays());
                    } else {
                        location = "Destination";
                        allocatedDays = "XX";
                    }

                    LinearLayout row = new LinearLayout(this);
                    row.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    param.setMargins(0, 10, 0, 10);
                    row.setLayoutParams(param);
                    row.setBackgroundColor(Color.parseColor("#E0E0E0"));


                    TextView locationText = new TextView(this);
                    locationText.setText(location);
                    locationText.setTextSize(18);
                    locationText.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f));
                    locationText.setGravity(Gravity.START);

                    TextView allocatedDaysText = new TextView(this);
                    allocatedDaysText.setText(allocatedDays + " days planned");
                    allocatedDaysText.setTextSize(18);
                    allocatedDaysText.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f));
                    allocatedDaysText.setGravity(Gravity.END);

                    row.addView(locationText);
                    row.addView(allocatedDaysText);

                    destArea.addView(row);

                    Log.i(TAG, String.format("%s, %s days", location, allocatedDays));
                    Log.i(TAG, String.valueOf(destArea.getChildCount()));
                }
            } else {
                Log.i(TAG, "empty");
            }
        });

        // submitted observer
        destinationViewModel.getSubmitted().observe(this, bool -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DestinationScreen.this);
            builder.setMessage("Successfully submitted!");
            builder.create();

            if (bool) {
                builder.show();
            }
        });

        // updateUser success observer
        destinationViewModel.getAddDestSuccess().observe(this, bool -> {
            if (bool) {
                logErrorDisplay.setVisibility(View.GONE);
                logArea.setVisibility(View.GONE);

                destStartDateInput.setText("");
                destEndDateInput.setText("");
                locationInput.setText("");
            } else {
                logErrorDisplay.setVisibility(View.VISIBLE);
            }
        });

        // logOpenButton clicked
        logOpenButton.setOnClickListener(view -> {
            Log.i(TAG, "logOpenButton:clicked");
            if (calculateOpenButton.isChecked()) {
                calcArea.setVisibility(View.GONE);
                calculateOpenButton.setChecked(false);
            }

            if (logOpenButton.isChecked()) {
                logArea.setVisibility(View.VISIBLE);

                cancelButton.setOnClickListener(view1 -> {
                    logErrorDisplay.setVisibility(View.GONE);
                    logArea.setVisibility(View.GONE);

                    destStartDateInput.setText("");
                    destEndDateInput.setText("");
                    locationInput.setText("");
                });

                submitButton.setOnClickListener(view2 -> {
                    destinationViewModel.setLocation(locationInput);
                    destinationViewModel.setDestinationStartDate(destStartDateInput);
                    destinationViewModel.setDestinationEndDate(destEndDateInput);

                    destinationViewModel.addDestination();
                });
            } else {
                logArea.setVisibility(View.GONE);
            }
        });

        // updateUser success observer
        destinationViewModel.getUpdateUserSuccess().observe(this, bool -> {
            if (bool) {
                calcErrorDisplay.setVisibility(View.GONE);
                calcArea.setVisibility(View.GONE);

                userStartDateInput.setText("");
                userEndDateInput.setText("");
                durationInput.setText("");
            } else {
                calcErrorDisplay.setVisibility(View.VISIBLE);
            }
        });

        calculateOpenButton.setOnClickListener(view -> {
            Log.i(TAG, "calculateOpenButton:clicked");

            if (logOpenButton.isChecked()) {
                logArea.setVisibility(View.GONE);
                logOpenButton.setChecked(false);
            }

            if (calculateOpenButton.isChecked()) {
                calcArea.setVisibility(View.VISIBLE);

                calculateButton.setOnClickListener(view1 -> {
                    Log.i(TAG, "calcButton clicked");

                    Date start = destinationViewModel.setUserStartDate(userStartDateInput);
                    Date end = destinationViewModel.setUserEndDate(userEndDateInput);
                    long duration = destinationViewModel.dateDifference(start, end);

                    if (duration >= 0) {
                        destinationViewModel.setDuration(durationInput, duration);
                    }

                    destinationViewModel.updateUser();
                });
            } else {
                calcArea.setVisibility(View.GONE);
            }
        });
    }
}
package com.example.sprintproject.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityDestinationBinding;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.viewmodel.DestinationViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class DestinationScreen extends NavBarScreen {
    private static final String TAG = "DestinationScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityDestinationBinding binding =
                ActivityDestinationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        DestinationViewModel destinationViewModel =
                new ViewModelProvider(this).get(DestinationViewModel.class);
        binding.setVariable(BR.destViewModel, destinationViewModel);
        binding.setLifecycleOwner(this);

        ToggleButton logOpenButton = findViewById(R.id.logOpenButton);
        ToggleButton calculateOpenButton = findViewById(R.id.calculateOpenButton);

        LinearLayout logArea = findViewById(R.id.logArea);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button submitButton = findViewById(R.id.submitButton);
        EditText locationInput = findViewById(R.id.locationInput);
        EditText destStartDateInput = findViewById(R.id.destStartDateInput);
        EditText destEndDateInput = findViewById(R.id.destEndDateInput);
        TextView logErrorDisplay = findViewById(R.id.logErrorDisplay);

        LinearLayout calcArea = findViewById(R.id.calcArea);
        Button calculateButton = findViewById(R.id.calculateButton);
        EditText userStartDateInput = findViewById(R.id.userStartDateInput);
        EditText userEndDateInput = findViewById(R.id.userEndDateInput);
        EditText durationInput = findViewById(R.id.durationInput);
        TextView calcErrorDisplay = findViewById(R.id.calcErrorDisplay);

        LinearLayout destArea = findViewById(R.id.destArea);

        destinationViewModel.queryForDestinations();
        observeAndPopulateDestinations(destArea, destinationViewModel.getDestinations());

        observeAndShowDialog(destinationViewModel.getSubmitted());

        destinationViewModel.getAddDestSuccess().observe(this, bool -> {
            if (bool) {
                resetArea(
                        logErrorDisplay,
                        logArea,
                        destStartDateInput, destEndDateInput, locationInput
                );
            } else {
                logErrorDisplay.setVisibility(View.VISIBLE);
            }
        });

        logOpenButton.setOnClickListener(view -> {
            Log.i(TAG, "logOpenButton:clicked");
            if (calculateOpenButton.isChecked()) {
                calcArea.setVisibility(View.GONE);
                calculateOpenButton.setChecked(false);
            }

            if (logOpenButton.isChecked()) {
                logArea.setVisibility(View.VISIBLE);
                cancelButton.setOnClickListener(view1 -> {
                    resetArea(
                            logErrorDisplay,
                            logArea,
                            destStartDateInput, destEndDateInput, locationInput
                    );
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
                resetArea(
                        calcErrorDisplay,
                        calcArea,
                        userStartDateInput, userEndDateInput, durationInput
                );
            } else {
                calcErrorDisplay.setVisibility(View.VISIBLE);
            }
        });

        destinationViewModel.getUserHasAtLeastTwoValues().observe(this, bool -> {
            if (bool) {
                if (!destinationViewModel.getUserStartDateHasValue()) {
                    handleMissingStartDate(destinationViewModel,
                            userStartDateInput, userEndDateInput, durationInput
                    );
                } else if (!destinationViewModel.getUserEndDateHasValue()) {
                    handleMissingEndDate(
                            destinationViewModel,
                            userStartDateInput, userEndDateInput, durationInput
                    );
                } else if (!destinationViewModel.getUserDurationHasValue()) {
                    handleMissingDuration(
                            destinationViewModel,
                            userStartDateInput, userEndDateInput, durationInput
                    );
                } else {
                    handleNoMissing(
                            destinationViewModel,
                            userStartDateInput, userEndDateInput, durationInput
                    );
                }
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
                    validateAndSetValue(
                            userStartDateInput, "userStartDateInput",
                            destinationViewModel::setUserStartDateHasValue
                    );
                    validateAndSetValue(
                            userEndDateInput, "userEndDateInput",
                            destinationViewModel::setUserEndDateHasValue
                    );
                    validateAndSetValue(
                            durationInput, "durationInput",
                            destinationViewModel::setUserDurationHasValue
                    );
                    destinationViewModel.calculateHasTwoValues();
                    destinationViewModel.updateUser();
                });
            } else {
                calcArea.setVisibility(View.GONE);
            }
        });
    }

    private void observeAndPopulateDestinations(LinearLayout area,
                                                LiveData<List<Destination>> destinationsData) {
        destinationsData.observe(this, destinations -> {
            area.removeAllViews();
            if (!destinations.isEmpty()) {
                Log.i(TAG, "populating destinations...");
                for (int i = 0; i < 5; i++) {
                    String location;
                    String allocatedDays;
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
                    String message = String.format("%s days planned", allocatedDays);
                    allocatedDaysText.setText(message);
                    allocatedDaysText.setTextSize(18);
                    allocatedDaysText.setLayoutParams(new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f));
                    allocatedDaysText.setGravity(Gravity.END);

                    row.addView(locationText);
                    row.addView(allocatedDaysText);

                    area.addView(row);

                    Log.i(TAG, String.format("%s, %s days", location, allocatedDays));
                }
            } else {
                Log.i(TAG, "empty");
            }
        });
    }

    private void observeAndShowDialog(LiveData<Boolean> submitted) {
        submitted.observe(this, bool -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(DestinationScreen.this);
            builder.setMessage("Successfully submitted!");
            builder.create();

            if (bool) {
                builder.show();
            }
        });
    }

    private String calculateAndFormatDate(Date begin, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(begin);
        calendar.add(Calendar.DAY_OF_YEAR, amount);

        Date other = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        return formatter.format(other);
    }

    private void handleMissingStartDate(
            DestinationViewModel destinationViewModel,
            EditText userStartDateInput,
            EditText userEndDateInput,
            EditText durationInput) {
        Log.i(TAG, "missing user start date");
        Date end = destinationViewModel.setUserEndDate(userEndDateInput);
        destinationViewModel.setDuration(durationInput);
        long duration = Long.parseLong(durationInput.getText().toString());

        userStartDateInput.setText(calculateAndFormatDate(end, -((int) duration)));
        destinationViewModel.setUserStartDate(userStartDateInput);
    }

    private void handleMissingEndDate(
            DestinationViewModel destinationViewModel,
            EditText userStartDateInput,
            EditText userEndDateInput,
            EditText durationInput) {
        Log.i(TAG, "missing user end date");
        Date start = destinationViewModel.setUserStartDate(userStartDateInput);
        destinationViewModel.setDuration(durationInput);
        long duration = Long.parseLong(durationInput.getText().toString());

        userEndDateInput.setText(calculateAndFormatDate(start, (int) duration));
        destinationViewModel.setUserEndDate(userEndDateInput);
    }

    private void handleMissingDuration(
            DestinationViewModel destinationViewModel,
            EditText userStartDateInput,
            EditText userEndDateInput,
            EditText durationInput) {
        Log.i(TAG, "missing user destination date");
        Date start = destinationViewModel.setUserStartDate(userStartDateInput);
        Date end = destinationViewModel.setUserEndDate(userEndDateInput);
        long duration = destinationViewModel.dateDifference(start, end);

        if (durationInput.getText().toString().isEmpty()) {
            durationInput.setText(String.valueOf(duration));
        }

        destinationViewModel.setDuration(durationInput);
    }

    private void handleNoMissing(
            DestinationViewModel destinationViewModel,
            EditText userStartDateInput,
            EditText userEndDateInput,
            EditText durationInput) {
        Log.i(TAG, "has all values");
        Date start = destinationViewModel.setUserStartDate(userStartDateInput);
        Date end = destinationViewModel.setUserEndDate(userEndDateInput);
        long duration = destinationViewModel.dateDifference(start, end);

        if (Long.parseLong(durationInput.getText().toString()) != duration) {
            durationInput.setText(String.valueOf(duration));
            String errorMessage = String.format(
                    Locale.US,
                    "duration should be %d",
                    duration);
            durationInput.setError(errorMessage);
        } else {
            durationInput.setError(null);
        }

        destinationViewModel.setDuration(durationInput);
    }

    private void validateAndSetValue(EditText input, String message, Consumer<Boolean> method) {
        boolean hasValue = !input.getText().toString().isEmpty();
        method.accept(hasValue);
        Log.i(TAG, String.format(
                "%s%s",
                message,
                (hasValue ? "has value" : "does not have value")));
    }

    private void resetArea(TextView errorDisplay, LinearLayout area, EditText... inputs) {
        errorDisplay.setVisibility(View.GONE);
        area.setVisibility(View.GONE);

        for (EditText input : inputs) {
            input.setText("");
        }
    }
}
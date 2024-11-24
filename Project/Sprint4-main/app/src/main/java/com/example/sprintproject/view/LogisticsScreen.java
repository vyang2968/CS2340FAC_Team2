package com.example.sprintproject.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityLogisticsScreenBinding;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;
import com.example.sprintproject.viewmodel.LogisticsViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogisticsScreen extends NavBarScreen {
    private static final String TAG = "LogisticsScreen";
    private LogisticsViewModel logisticsViewModel;
    private PieChart pieChart;
    private Button visualizeButton;
    private ProgressBar loadingIndicator;
    private TextView errorText;
    private TextView totalDaysText;
    private Button inviteButton;
    private EditText inviteInput;
    private AlertDialog.Builder builder;
    private Button logOutButton;
    private Button addTripButton;
    private AlertDialog.Builder tripPrompt;
    private ToggleButton selectableButton;
    private GridLayout tripsLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLogisticsScreenBinding binding =
                ActivityLogisticsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();
        initializeViews();
        setupViewModel(binding);
        setupObservers();
        setupPieChart();
        setupButtonListeners();
    }

    private void initializeViews() {
        pieChart = findViewById(R.id.daysChart);
        visualizeButton = findViewById(R.id.visualizeButton);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        errorText = findViewById(R.id.errorText);
        totalDaysText = findViewById(R.id.totalDaysText);
        inviteButton = findViewById(R.id.inviteButton);
        inviteInput = findViewById(R.id.inviteInput);
        builder = new AlertDialog.Builder(this);
        logOutButton = findViewById(R.id.logOutButton);
        addTripButton = findViewById(R.id.add_trip_button);
        tripPrompt = new AlertDialog.Builder(this);
        selectableButton = findViewById(R.id.selectable_button);
        tripsLayout = findViewById(R.id.all_trips);
    }

    private void setupViewModel(ActivityLogisticsScreenBinding binding) {
        logisticsViewModel = new ViewModelProvider(this).get(LogisticsViewModel.class);
        binding.setVariable(BR.logisticsViewModel, logisticsViewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupPieChart() {
        if (pieChart != null) {
            Description description = new Description();
            description.setText("Trip Days Distribution");
            description.setTextSize(16f);

            pieChart.setDescription(description);
            pieChart.setUsePercentValues(true);
            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleRadius(0f);
            pieChart.setTransparentCircleRadius(0f);
            pieChart.setRotationEnabled(true);

            Legend legend = pieChart.getLegend();
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            legend.setOrientation(Legend.LegendOrientation.VERTICAL);

            pieChart.setVisibility(View.GONE);
        }
    }

    private void setupButtonListeners() {
        if (visualizeButton != null) {
            visualizeButton.setOnClickListener(v -> {
                loadingIndicator.setVisibility(View.VISIBLE);
                logisticsViewModel.setHasError(false);
                updateChartData();
                pieChart.setVisibility(View.VISIBLE);
            });
        }

        inviteButton.setOnClickListener(v -> {
            Log.i(TAG, "invite user clicked");
            if (logisticsViewModel.validateInviteInput(inviteInput)) {
                logisticsViewModel.queryForUser(inviteInput.getText().toString());
            }
        });

        logOutButton.setOnClickListener(v -> {
            Log.i(TAG, "logout button clicked");
            logisticsViewModel.logOutUser();
            finish();
            Intent intent = new Intent(this, WelcomeScreen.class);
            startActivity(intent);
        });

        addTripButton.setOnClickListener(v -> {
            Log.i(TAG, "add trip button clicked");
            EditText tripName = new EditText(this);
            tripPrompt.setView(tripName);
            tripPrompt.setTitle("Create New Trip");
            tripPrompt.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    logisticsViewModel.createTrip(tripName.getText().toString());
                    tripName.setText("");
                }
            });
            tripPrompt.setNegativeButton("Cancel",
                    (dialogInterface, i) -> dialogInterface.dismiss());
            tripPrompt.create().show();
        });

        selectableButton.setOnCheckedChangeListener((compoundButton, b) -> {
            logisticsViewModel.setTripsAreSelectable(b);
        });
    }

    private void setupObservers() {
        logisticsViewModel.getTotalDaysTraveledMessage().observe(this, message -> {
            totalDaysText.setText(message);
        });

        logisticsViewModel.getTotalTripDays().observe(this, totalDays -> {
            updateChartData();
        });

        logisticsViewModel.getTotalDaysTraveled().observe(this, plannedDays -> {
            updateChartData();
        });

        logisticsViewModel.getHasError().observe(this, bool -> {
            if (bool) {
                errorText.setVisibility(View.VISIBLE);
            } else {
                errorText.setVisibility(View.GONE);
            }
        });

        logisticsViewModel.getHasCurrentUser().observe(this, bool -> {
            if (bool) {
                logisticsViewModel.queryTotalTrips();
                logisticsViewModel.queryForAllTrips();
            }
        });

        logisticsViewModel.getFoundUser().observe(this, user -> {
            logisticsViewModel.getFoundUser().removeObservers(this);
            logisticsViewModel.updateCollaborator(user);
        });

        logisticsViewModel.getUpdateUserSuccessful().observe(this, bool -> {
            if (bool) {
                logisticsViewModel.updateTrip(logisticsViewModel.getFoundUser().getValue());
            }
        });

        logisticsViewModel.getUpdateTripSuccessful().observe(this, bool -> {
            if (bool) {
                builder.setMessage("Invite successfully sent!");
            } else {
                builder.setMessage("No user found");
            }
            builder.create().show();
            inviteInput.setText("");
        });


        logisticsViewModel.getTripCreationSuccessful().observe(this, bool -> {
            if (bool) {
                builder.setMessage("Trip successfully created!");
                logisticsViewModel.setDoneQueryingForAllTrips(false);
                logisticsViewModel.queryForAllTrips();
            } else {
                builder.setMessage("Trip could not be created.");
            }
            builder.create().show();
        });

        logisticsViewModel.getDoneQueryingForAllTrips().observe(this, bool -> {
            if (bool) {
                logisticsViewModel.queryForAllUsers();
                logisticsViewModel.queryForAllDests();
            }
        });

        logisticsViewModel.getDoneQueryingForAllUsers().observe(this, bool -> {
            populateTrips(logisticsViewModel.getTripsAndUsers().getValue());
        });
    }

    private void populateTrips(HashMap<Trip, User> map) {
        for (Trip trip : map.keySet()) {
            User user = map.get(trip);

            LinearLayout block =
                    new LinearLayout(new ContextThemeWrapper(this, R.style.tripBlock));

            TextView name = new TextView(this);
            name.setText(trip.getName());
            name.setTextSize(20f);
            TextView creator = new TextView(this);

            creator.setText(user.getUsername());
            creator.setTextSize(15f);

            block.addView(name);
            block.addView(creator);

            if (logisticsViewModel.isActiveTrip(trip)) {
                block.setBackgroundColor(getColor(R.color.purple));
                name.setTextColor(getColor(R.color.white));
                creator.setTextColor(getColor(R.color.white));
            }

            block.setOnClickListener(view -> {
                if (logisticsViewModel.getTripsAreSelectable().getValue()) {
                    logisticsViewModel.changeCurrentTrip(trip);
                    recolor(block);
                    logisticsViewModel.setTripsAreSelectable(false);
                    selectableButton.setChecked(false);
                }
            });

            tripsLayout.addView(block);
        }
    }

    private void recolor(LinearLayout activeBlock) {
        for (int i = 0; i < tripsLayout.getChildCount(); i++) {
            LinearLayout layout = (LinearLayout) tripsLayout.getChildAt(i);

            for (int j = 0; j < layout.getChildCount(); j++) {
                if (!layout.equals(activeBlock)) {
                    ((TextView) layout.getChildAt(j)).setTextColor(getColor(R.color.black));
                } else {
                    ((TextView) layout.getChildAt(j)).setTextColor(getColor(R.color.white));
                }
            }

            if (!layout.equals(activeBlock)) {
                layout.setBackgroundResource(R.drawable.button_border);
            } else {
                layout.setBackgroundResource(R.drawable.button_border);
                layout.setBackgroundColor(getColor(R.color.purple));
            }

            layout.setPadding(dpToPixel(30), dpToPixel(20), dpToPixel(30), dpToPixel(20));
        }
    }

    private int dpToPixel(int pixel) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (pixel * scale + 0.5f);
    }

    private void updateChartData() {
        if (pieChart == null) {
            return;
        }

        Integer totalDays = logisticsViewModel.getTotalTripDays().getValue();
        Integer plannedDays = logisticsViewModel.getTotalDaysTraveled().getValue();

        if (totalDays == null || totalDays == 0 || plannedDays == null) {
            logisticsViewModel.setHasError(true);
            loadingIndicator.setVisibility(View.GONE);
            pieChart.setVisibility(View.GONE);
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(plannedDays, "% Planned Days"));

        int remainingDays = Math.max(0, totalDays - plannedDays);
        if (remainingDays > 0) {
            entries.add(new PieEntry(remainingDays, "% Remaining Days"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Trip Days");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(android.graphics.Color.WHITE);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();

        loadingIndicator.setVisibility(View.GONE);
        logisticsViewModel.setHasError(false);
    }


    @Override
    protected void onResume() {
        super.onResume();

        logisticsViewModel.refreshTotalTripDays();
    }
}
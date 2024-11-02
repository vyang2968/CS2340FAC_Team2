package com.example.sprintproject.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityLogisticsScreenBinding;
import com.example.sprintproject.viewmodel.LogisticsViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class LogisticsScreen extends NavBarScreen {
    private static final String TAG = "LogisticsScreen";
    private LogisticsViewModel logisticsViewModel;
    private PieChart pieChart;
    private Button visualizeButton;
    private ProgressBar loadingIndicator;
    private TextView errorText;
    private TextView totalDaysText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLogisticsScreenBinding binding = ActivityLogisticsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();
        initializeViews();
        setupViewModel(binding);
        setupPieChart();
        setupButtonListeners();
        setupObservers();
    }

    private void initializeViews() {
        pieChart = findViewById(R.id.daysChart);
        visualizeButton = findViewById(R.id.visualizeButton);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        errorText = findViewById(R.id.errorText);
        totalDaysText = findViewById(R.id.totalDaysText);
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
                errorText.setVisibility(View.GONE);
                updateChartData();
            });
        }
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
    }

    private void updateChartData() {
        if (pieChart == null){
            return;
        }

        Integer totalDays = logisticsViewModel.getTotalTripDays().getValue();
        Integer plannedDays = logisticsViewModel.getTotalDaysTraveled().getValue();

        if (totalDays == null || totalDays == 0 || plannedDays == null) {
            errorText.setVisibility(View.VISIBLE);
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
        errorText.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logisticsViewModel.refreshTotalTripDays();
    }
}
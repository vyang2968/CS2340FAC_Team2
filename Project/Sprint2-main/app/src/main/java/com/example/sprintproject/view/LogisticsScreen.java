package com.example.sprintproject.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.sprintproject.BR;
import com.example.sprintproject.databinding.ActivityLogisticsScreenBinding;
import com.example.sprintproject.viewmodel.LogisticsViewModel;

public class LogisticsScreen extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_logistics_screen);

        ActivityLogisticsScreenBinding binding = ActivityLogisticsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        LogisticsViewModel logisticsViewModel =
                new ViewModelProvider(this).get(LogisticsViewModel.class);
        binding.setVariable(BR.logisticsViewModel, logisticsViewModel);
        binding.setLifecycleOwner(this);

        logisticsViewModel.updateDaysTraveled();
    }
}
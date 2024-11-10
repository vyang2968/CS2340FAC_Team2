package com.example.sprintproject.view;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.databinding.ActivityAccommodationBinding;
import com.example.sprintproject.viewmodel.AccommodationViewModel;

public class AccommodationScreen extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityAccommodationBinding binding =
                ActivityAccommodationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        AccommodationViewModel accommodationViewModel =
                new ViewModelProvider(this).get(AccommodationViewModel.class);
        binding.setVariable(BR.accoViewModel, accommodationViewModel);
        binding.setLifecycleOwner(this);
    }
}
package com.example.sprintproject.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityTravelCommunityBinding;
import com.example.sprintproject.viewmodel.TravelCommunityViewModel;

public class TravelCommunityScreen extends NavBarScreen {

    private TravelCommunityViewModel viewModel;
    private LinearLayout postForm;
    private LinearLayout accommodationContainer;
    private Button createButton;
    private Button postButton;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityTravelCommunityBinding binding =
                ActivityTravelCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        viewModel =
                new ViewModelProvider(this).get(TravelCommunityViewModel.class);
        binding.setVariable(BR.travelViewModel, viewModel);
        binding.setLifecycleOwner(this);

        inflater = LayoutInflater.from(this);

        postForm = findViewById(R.id.createPostWindow);
        createButton = findViewById(R.id.createPostButton);
        postButton = findViewById(R.id.postButton);

        createButton.setOnClickListener(view -> toggleFormVisibility());
        postButton.setOnClickListener(view -> postTravel());
    }

    private void toggleFormVisibility() {
        if (postForm.getVisibility() == View.GONE) {
            postForm.setVisibility(View.VISIBLE);
        } else {
            postForm.setVisibility(View.GONE);
        }
    }

    private void postTravel() {
        EditText startInput = findViewById(R.id.startInput);
        EditText endInput = findViewById(R.id.endInput);
        EditText destinationInput = findViewById(R.id.destinationInput);
        EditText accommodationsInput = findViewById(R.id.accommodationsInput);
        EditText reservationsInput = findViewById(R.id.reservationsInput);
        EditText notesInput = findViewById(R.id.notesInput);

        String start = startInput.getText().toString();
        String end = endInput.getText().toString();
        String destination = destinationInput.getText().toString();
        String accommodations = accommodationsInput.getText().toString();
        String reservations = reservationsInput.getText().toString();
        String notes = notesInput.getText().toString();

        try {
            //viewModel.addAccommodation(checkInTime, checkOutTime, location, roomCount, roomType);

            startInput.setText("");
            endInput.setText("");
            destinationInput.setText("");
            accommodationsInput.setText("");
            reservationsInput.setText("");
            notesInput.setText("");
            postForm.setVisibility(View.GONE);
        } catch (IllegalArgumentException ignored) {
            startInput.setError("dates must be mm/dd/yyyy format");
            endInput.setError("dates must be mm/dd/yyyy format");
        }
    }
}
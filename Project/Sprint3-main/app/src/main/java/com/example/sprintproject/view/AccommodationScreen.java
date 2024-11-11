package com.example.sprintproject.view;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityAccommodationBinding;
import com.example.sprintproject.viewmodel.AccommodationViewModel;

public class AccommodationScreen extends NavBarScreen {

    private AccommodationViewModel viewModel;
    private LinearLayout accommodationForm;
    private Button addButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ActivityAccommodationBinding binding =
                ActivityAccommodationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavBar();

        viewModel =
                new ViewModelProvider(this).get(AccommodationViewModel.class);
        binding.setVariable(BR.accoViewModel, viewModel);
        binding.setLifecycleOwner(this);

        viewModel.queryForAccommodations();

        accommodationForm = findViewById(R.id.accommodations_form);
        addButton = findViewById(R.id.button_add_accommodation);
        submitButton = findViewById(R.id.button_submit_accommodation);

        addButton.setOnClickListener(view -> toggleFormVisibility());
        submitButton.setOnClickListener(view -> submitAccommodation());
    }

    private void toggleFormVisibility() {
        if (accommodationForm.getVisibility() == View.GONE) {
            accommodationForm.setVisibility(View.VISIBLE);
        } else {
            accommodationForm.setVisibility(View.GONE);
        }
    }

    private void submitAccommodation() {
        EditText checkInInput = findViewById(R.id.edit_text_check_in);
        EditText checkOutInput = findViewById(R.id.edit_text_check_out);
        EditText locationInput = findViewById(R.id.edit_text_location);
        Spinner roomCountInput = findViewById(R.id.spinner_rooms);
        Spinner roomTypeInput = findViewById(R.id.spinner_room_type);

        String checkInTime = checkInInput.getText().toString();
        String checkOutTime = checkOutInput.getText().toString();
        String location = locationInput.getText().toString();
        int roomCount = Integer.parseInt(roomCountInput.getSelectedItem().toString());
        String roomType = roomTypeInput.getSelectedItem().toString();

        try {
            viewModel.addAccommodation(checkInTime, checkOutTime, location, roomCount, roomType);

            checkInInput.setText("");
            checkOutInput.setText("");
            locationInput.setText("");
            roomCountInput.setSelection(0);
            roomTypeInput.setSelection(0);
            accommodationForm.setVisibility(View.GONE);
        } catch (IllegalArgumentException ignored) {
            checkInInput.setError("dates must be mm/dd/yyyy format");
            checkOutInput.setError("dates must be mm/dd/yyyy format");
        }
    }
}
package com.example.sprintproject.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.BR;
import com.example.sprintproject.R;
import com.example.sprintproject.databinding.ActivityAccommodationBinding;
import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.viewmodel.AccommodationViewModel;

import java.util.List;

public class AccommodationScreen extends NavBarScreen {

    private AccommodationViewModel viewModel;
    private LinearLayout accommodationForm;
    private LinearLayout accommodationContainer;
    private Button addButton;
    private Button submitButton;
    private LayoutInflater inflater;

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

        inflater = LayoutInflater.from(this);
        accommodationContainer = findViewById(R.id.accommodation_container);
        accommodationForm = findViewById(R.id.accommodations_form);
        addButton = findViewById(R.id.button_add_accommodation);
        submitButton = findViewById(R.id.button_submit_accommodation);

        viewModel.getAccommodations().observe(this, this::updateAccommodationList);

        addButton.setOnClickListener(view -> toggleFormVisibility());
        submitButton.setOnClickListener(view -> submitAccommodation());

        viewModel.queryForAccommodations();
    }

    private void updateAccommodationList(List<Accommodation> accommodations) {
        accommodationContainer.removeAllViews();

        for (Accommodation accommodation : accommodations) {
            View itemView =
                    inflater.inflate(R.layout.accommodation_item, accommodationContainer, false);


            TextView locationText = itemView.findViewById(R.id.text_location);
            TextView datesText = itemView.findViewById(R.id.text_dates);
            TextView roomCountText = itemView.findViewById(R.id.text_room_count);
            TextView roomTypeText = itemView.findViewById(R.id.text_room_type);
            TextView pastReservationText = itemView.findViewById(R.id.text_past_reservation);

            locationText.setText(accommodation.getLocation());

            String dates = String.format("Check-in: %s, Check-out: %s",
                    viewModel.formatDate(accommodation.getCheckInTime()),
                    viewModel.formatDate(accommodation.getCheckOutTime()));
            datesText.setText(dates);

            roomCountText.setText("Number of Rooms: " + accommodation.getNumRooms());
            roomTypeText.setText(accommodation.getRoomType().toString());

            // Past Reservation
            if (viewModel.isPastReservation(accommodation.getCheckOutTime())) {
                pastReservationText.setVisibility(View.VISIBLE);
            }

            accommodationContainer.addView(itemView);
        }
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
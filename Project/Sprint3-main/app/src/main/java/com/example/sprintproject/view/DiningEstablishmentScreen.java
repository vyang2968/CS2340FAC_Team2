package com.example.sprintproject.view;

import com.example.sprintproject.viewmodel.DiningEstablishmentViewModel;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;

public class DiningEstablishmentScreen extends NavBarScreen {
    private DiningEstablishmentViewModel diningEstablishmentViewModel;
    private LinearLayout reservationForm;
    private Button addButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_establishment);

        diningEstablishmentViewModel =
                new ViewModelProvider(this).get(DiningEstablishmentViewModel.class);

        reservationForm = findViewById(R.id.reservation_form);
        addButton = findViewById(R.id.button_add_reservation);
        submitButton = findViewById(R.id.button_submit_reservation);

        addButton.setOnClickListener(view -> toggleFormVisibility());
        submitButton.setOnClickListener(view -> submitReservation());

        setupNavBar();
    }

    private void toggleFormVisibility() {
        if (reservationForm.getVisibility() == View.GONE) {
            reservationForm.setVisibility(View.VISIBLE);
        } else {
            reservationForm.setVisibility(View.GONE);
        }
    }

    private void submitReservation() {
        EditText timeInput = findViewById(R.id.edit_text_time);
        EditText locationInput = findViewById(R.id.edit_text_location);
        EditText websiteInput = findViewById(R.id.edit_text_website);

        String time = timeInput.getText().toString();
        String location = locationInput.getText().toString();
        String website = websiteInput.getText().toString();

        diningEstablishmentViewModel.setTime(time);
        diningEstablishmentViewModel.setLocation(location);
        diningEstablishmentViewModel.setWebsite(website);

        diningEstablishmentViewModel.addReservation();

        timeInput.setText("");
        locationInput.setText("");
        websiteInput.setText("");
        reservationForm.setVisibility(View.GONE);
    }
}
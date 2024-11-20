package com.example.sprintproject.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;

import com.example.sprintproject.R;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.viewmodel.DiningEstablishmentViewModel;

public class DiningEstablishmentScreen extends NavBarScreen {
    private DiningEstablishmentViewModel diningEstablishmentViewModel;
    private LinearLayout reservationForm;
    private Button addButton;
    private Button submitButton;
    private LinearLayout reservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_establishment);

        diningEstablishmentViewModel = new ViewModelProvider(
            this).get(DiningEstablishmentViewModel.class);

        reservationForm = findViewById(R.id.addReservationWindow);
        addButton = findViewById(R.id.openReservationButton);
        submitButton = findViewById(R.id.addReservationButton);
        reservationList = findViewById(R.id.restaurantList);

        diningEstablishmentViewModel.getAllReservations().observe(
                this, this::createReservationView);

        addButton.setOnClickListener(view -> toggleFormVisibility());
        submitButton.setOnClickListener(view -> submitReservation());

        diningEstablishmentViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(
                DiningEstablishmentScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

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
        EditText dateInput = findViewById(R.id.dateInput);
        EditText timeInput = findViewById(R.id.timeInput);
        EditText locationInput = findViewById(R.id.locationInput);
        EditText websiteInput = findViewById(R.id.websiteInput);

        String date = dateInput.getText().toString();
        String time = timeInput.getText().toString();
        String location = locationInput.getText().toString();
        String website = websiteInput.getText().toString();

        if (date.isEmpty() || time.isEmpty() || location.isEmpty() || website.isEmpty()) {
            if (date.isEmpty()) {
                dateInput.setError("Date is Required.");
            }
            if (time.isEmpty()) {
                timeInput.setError("Time is required.");
            }
            if (location.isEmpty()) {
                locationInput.setError("Location is required.");
            }
            if (website.isEmpty()) {
                websiteInput.setError("Website is required.");
            }
            return;
        }

        try {
            diningEstablishmentViewModel.setDate(date);
            diningEstablishmentViewModel.setTime(time);
            diningEstablishmentViewModel.setLocation(location);
            diningEstablishmentViewModel.setWebsite(website);

            reservationList.removeAllViews();
            diningEstablishmentViewModel.addReservation();

            dateInput.setText("");
            timeInput.setText("");
            locationInput.setText("");
            websiteInput.setText("");
            reservationForm.setVisibility(View.GONE);

        } catch (IllegalArgumentException e) {
            timeInput.setError("Invalid time format. Use MM/DD/YYYY and HH:mm.");
        }

        diningEstablishmentViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(
                DiningEstablishmentScreen.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReservationView(List<DiningReservation> reservations) {
        reservations.sort(Comparator.comparing(DiningReservation::getPlannedDate));
        for (DiningReservation i : reservations) {

            LinearLayout newReservation = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);
            newReservation.setLayoutParams(layoutParams);
            newReservation.setOrientation(LinearLayout.VERTICAL);
            newReservation.setPadding(5, 5, 5, 5);
            newReservation.setBackgroundColor(Color.parseColor("#E0E0E0"));

            LinearLayout restaurantTimeLayout = new LinearLayout(this);
            restaurantTimeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            restaurantTimeLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView restaurantTextView = new TextView(this);
            restaurantTextView.setText(i.getLocation());
            restaurantTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            restaurantTimeLayout.addView(restaurantTextView);

            TextView dashTextView = new TextView(this);
            dashTextView.setText(" - ");
            dashTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            restaurantTimeLayout.addView(dashTextView);

            TextView timeTextView = new TextView(this);
            timeTextView.setText(i.getReservationTime().toString());
            timeTextView.setTypeface(null, Typeface.ITALIC);
            timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            if (i.getReservationTime().before(new Date())) {
                timeTextView.setTextColor(Color.parseColor("#EE1111"));
            }
            restaurantTimeLayout.addView(timeTextView);

            newReservation.addView(restaurantTimeLayout);

            TextView ratingTextView = new TextView(this);
            ratingTextView.setText("Rating: 5.0/5.0");
            ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            newReservation.addView(ratingTextView);

            this.reservationList.addView(newReservation);
        }
    }
}
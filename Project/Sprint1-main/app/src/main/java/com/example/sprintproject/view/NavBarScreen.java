package com.example.sprintproject.view;

import android.content.Intent;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sprintproject.R;

public class NavBarScreen extends AppCompatActivity {

    protected void setupNavBar() {
        ImageButton navAccommodation = findViewById(R.id.navbar_accommodations_button);
        ImageButton navDestination = findViewById(R.id.navbar_destination_button);
        ImageButton navDining = findViewById(R.id.navbar_dining_button);
        ImageButton navLogistics = findViewById(R.id.navbar_logistics_button);
        ImageButton navTravelCommunity = findViewById(R.id.navbar_travel_community_button);

        navAccommodation.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccommodationScreen.class);
            this.startActivity(intent);
        });

        navDestination.setOnClickListener(v -> {
            Intent intent = new Intent(this, DestinationScreen.class);
            this.startActivity(intent);
        });

        navDining.setOnClickListener(v -> {
            Intent intent = new Intent(this, DiningEstablishmentScreen.class);
            this.startActivity(intent);
        });

        navLogistics.setOnClickListener(v -> {
            Intent intent = new Intent(this, LogisticsScreen.class);
            this.startActivity(intent);
        });

        navTravelCommunity.setOnClickListener(v -> {
            Intent intent = new Intent(this, TravelCommunity.class);
            this.startActivity(intent);
        });
    }
}

package com.example.sprintproject.view;

import android.os.Bundle;

import com.example.sprintproject.R;

public class AccommodationScreen extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        setupNavBar();
    }
}
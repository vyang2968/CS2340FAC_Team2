package com.example.sprintproject.view;


import android.os.Bundle;

import com.example.sprintproject.R;

public class TravelCommunity extends NavBarScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_community);

        setupNavBar();
    }
}
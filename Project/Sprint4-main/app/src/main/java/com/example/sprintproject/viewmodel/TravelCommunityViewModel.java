package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.utils.LogSource;

public class TravelCommunityViewModel extends ViewModel implements LogSource {

    private static final String TAG = "TravelCommunityViewModel";

    @Override
    public String getTag() {
        return TAG;
    }
}

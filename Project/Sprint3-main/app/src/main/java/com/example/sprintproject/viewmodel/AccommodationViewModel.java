package com.example.sprintproject.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;

import java.util.List;

public class AccommodationViewModel extends ViewModel implements LogSource {

    private static final String TAG = "AccommodationViewModel";

    private final MutableLiveData<List<Accommodation>> accommodations;
    private final UserService userService;
    private final User currUser;

    public AccommodationViewModel() {
        this.accommodations = new MutableLiveData<>();

        this.userService = UserService.getInstance();
        this.currUser = userService.getCurrentUser();
    }

    @Override
    public String getTag() {
        return TAG;
    }
}

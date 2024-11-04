package com.example.sprintproject.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;

import java.util.List;

public class LogisticsViewModel extends ViewModel implements LogSource {

    private static final String TAG = "LogisticsViewModel";
    private final DestinationService destinationService;
    private final UserService userService;
    private final MutableLiveData<Integer> totalDaysTraveled;
    private final MutableLiveData<String> totalDaysTraveledMessage;
    private final MutableLiveData<Integer> totalTripDays;

    public LogisticsViewModel() {
        this.destinationService = DestinationService.getInstance();
        this.userService = UserService.getInstance();
        totalDaysTraveled = new MutableLiveData<>(0);
        totalDaysTraveledMessage = new MutableLiveData<>("Loading Total Days Planned...");
        totalTripDays = new MutableLiveData<>(0);

        // Get total trip days from user
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            totalTripDays.setValue(currentUser.getDuration());
        }
    }

    public void updateDaysTraveled() {
        destinationService.getAllDestinations(new DataCallback<List<Destination>>() {
            @Override
            public void onSuccess(List<Destination> result) {
                logInfo("updateDaysTraveled:success");

                int plannedDays = 0;
                for (Destination destination : result) {
                    plannedDays += destination.getDurationInDays();
                }

                int totalDays = totalTripDays.getValue() != null ? totalTripDays.getValue() : 0;
                totalDaysTraveled.setValue(plannedDays);
                totalDaysTraveledMessage.setValue(String.format("Days Planned: %d of %d total days",
                        plannedDays, totalDays));
            }

            @Override
            public void onError(Exception e) {
                logDebug("updateDaysTraveled:failed");
            }
        });
    }

    public void refreshTotalTripDays() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            totalTripDays.setValue(currentUser.getDuration());
            updateDaysTraveled();
        }
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public LiveData<String> getTotalDaysTraveledMessage() {
        return totalDaysTraveledMessage;
    }

    public LiveData<Integer> getTotalTripDays() {
        return totalTripDays;
    }

    public LiveData<Integer> getTotalDaysTraveled() {
        return totalDaysTraveled;
    }
}


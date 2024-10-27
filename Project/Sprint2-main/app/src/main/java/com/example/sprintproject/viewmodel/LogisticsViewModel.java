package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.utils.DataCallback;

import java.util.List;

public class LogisticsViewModel extends ViewModel implements LogSource {

    private static final String TAG = "LogisticsViewModel";
    private DestinationService destinationService;
    private MutableLiveData<Integer> totalDaysTraveled;
    private MutableLiveData<String> totalDaysTraveledMessage;

    public LogisticsViewModel() {
        this.destinationService = DestinationService.getInstance();

        totalDaysTraveled = new MutableLiveData<>(0);
        totalDaysTraveledMessage = new MutableLiveData<>("Loading Total Days Planned...");
    }

    public void updateDaysTraveled() {
        destinationService.getAllDestinations(new DataCallback<List<Destination>>() {
            @Override
            public void onSuccess(List<Destination> result) {
                logInfo("updateDaysTraveled:success");

                int days = 0;
                for (Destination destination : result) {
                    days += destination.getDurationInDays();
                }

                totalDaysTraveled.setValue(days);
                totalDaysTraveledMessage.setValue("Total Days Planned: " + days);
            }

            @Override
            public void onError(Exception e) {
                logDebug("updateDaysTraveled:failed");
            }
        });
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public LiveData<String> getTotalDaysTraveledMessage() {
        return totalDaysTraveledMessage;
    }
}

package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class SpecificDestinationViewModel extends ViewModel {
    private static final String TAG = "SpecificDestViewModel";
    private static final int STARTING_DAY = 1;

    private final UserService userService;
    private final MutableLiveData<Destination> destination;
    private final MutableLiveData<Integer> startingDay;
    private final DestinationService destinationService;
    private final MutableLiveData<Boolean> updateDestinationSuccessful;

    public SpecificDestinationViewModel() {
        this.destination = new MutableLiveData<>();
        this.startingDay = new MutableLiveData<>(STARTING_DAY);
        this.userService = UserService.getInstance();
        this.destinationService = DestinationService.getInstance();
        this.updateDestinationSuccessful = new MutableLiveData<>(false);
    }

    public void setDestination(Destination destination) {
        this.destination.setValue(destination);
    }

    public String getDestinationName() {
        return destination.getValue().getDestinationName();
    }

    public void setStartingDay(int day) {
        startingDay.setValue(day);
    }

    public LiveData<Integer> getStartingDay() {
        return startingDay;
    }

    public String getStartDate() {
        Date date = destination.getValue().getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        String formatted = format.format(date);
        return formatted;
    }

    public String getEndDate() {
        Date date = destination.getValue().getEndDate();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        return format.format(date);
    }

    public String getDayPlanDetail(int day) {
        Map<String, String> dayPlans = destination.getValue().getDayPlansManager().getDayPlans();

        return dayPlans.getOrDefault("day" + day, "");
    }

    public int getDuration() {
        return destination.getValue().getDurationInDays();
    }

    public boolean getIsOwner() {
        User creator = destination.getValue().getCollaboratorManager().getCreator();
        return creator.equals(userService.getCurrentUser());
    }

    public void updateDetail(int i, String detail) {
        Destination updated = destination.getValue();
        updated.getDayPlansManager().getDayPlans().put("day" + i, detail);
        destinationService.updateDestination(updated).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "updateDestination:success");
                updateDestinationSuccessful.setValue(true);
            } else {
                Log.i(TAG, "updateDestination:failed");
                updateDestinationSuccessful.setValue(false);
            }
        });
    }

    public MutableLiveData<Boolean> getUpdateDestinationSuccessful() {
        return updateDestinationSuccessful;
    }
}

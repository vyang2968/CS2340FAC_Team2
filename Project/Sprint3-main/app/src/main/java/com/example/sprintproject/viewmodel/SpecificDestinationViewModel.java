package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Consumer;

public class SpecificDestinationViewModel extends ViewModel {
    private static final String TAG = "SpecificDestViewModel";
    private static final int STARTING_DAY = 1;
    private MutableLiveData<Destination> destination;
    private MutableLiveData<Integer> startingDay;

    public SpecificDestinationViewModel() {
        this.destination = new MutableLiveData<>();
        this.startingDay = new MutableLiveData<>(STARTING_DAY);
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

    public String getDayPlan(int day) {
        HashMap<Integer, String> dayPlans = destination.getValue().getDayPlans();

        return (dayPlans.containsKey(day)) ? dayPlans.get(day) : "";
    }

    public int getDuration() {
        return destination.getValue().getDurationInDays();
    }
}

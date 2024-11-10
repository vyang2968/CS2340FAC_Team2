package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiningEstablishmentViewModel extends ViewModel {
    private final MutableLiveData<String> time = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>();
    private final MutableLiveData<String> website = new MutableLiveData<>();

    public void setTime(String reservationTime) {
        time.setValue(reservationTime);
    }

    public void setLocation(String reservationLocation) {
        location.setValue(reservationLocation);
    }

    public void setWebsite(String reservationWebsite) {
        website.setValue(reservationWebsite);
    }

    public LiveData<String> getTime() {
        return time;
    }

    public LiveData<String> getLocation() {
        return location;
    }

    public LiveData<String> getWebsite() {
        return website;
    }

    public void addReservation() {
        // placeholder for adding reservation logic
    }
}

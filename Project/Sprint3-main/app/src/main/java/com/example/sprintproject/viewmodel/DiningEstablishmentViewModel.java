package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.service.DiningReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiningEstablishmentViewModel extends ViewModel {
    private static final String TAG = "DiningEstablishmentViewModel";

    private final MutableLiveData<String> time = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>();
    private final MutableLiveData<String> website = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

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
        String timeValue = time.getValue();
        String locationValue = location.getValue();
        String websiteValue = website.getValue();

        if (timeValue == null || locationValue == null || websiteValue == null) {
            errorMessage.setValue("All fields are required.");
            return;
        }

        Log.d(TAG, "Time input: " + timeValue);

        if (!isValidUrl(websiteValue)) {
            errorMessage.setValue("Invalid website URL.");
            return;
        }

        try {
            // Parse time string into Date object
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date reservationDate = sdf.parse(timeValue);  // Parse time to Date

            if (reservationDate == null) {
                errorMessage.setValue("Invalid time format. Use HH:mm.");
                return;
            }

            DiningReservation reservation = new DiningReservation();
            reservation.setReservationTime(reservationDate);  // Store as Date
            reservation.setWebsiteLink(websiteValue);
            reservation.setLocation(locationValue);

            DiningReservationService.getInstance().addDiningReservation(reservation)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "Reservation added successfully.");
                        } else {
                            Log.e(TAG, "Failed to add reservation.", task.getException());
                            errorMessage.setValue("Failed to add reservation. Please try again.");
                        }
                    });
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing time: " + timeValue, e);
            errorMessage.setValue("Invalid time format. Use HH:mm.");
        }
    }

    private boolean isValidUrl(String url) {
        return url != null && android.util.Patterns.WEB_URL.matcher(url).matches();
    }
}
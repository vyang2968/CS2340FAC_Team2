package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.service.DiningReservationService;
import com.example.sprintproject.utils.DataCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DiningEstablishmentViewModel extends ViewModel {

    private static final String TAG = "DiningEstablishmentViewModel";
    private final MutableLiveData<String> date = new MutableLiveData<>();
    private final MutableLiveData<String> time = new MutableLiveData<>();
    private final MutableLiveData<String> location = new MutableLiveData<>();
    private final MutableLiveData<String> website = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final MutableLiveData<List<DiningReservation>> reservations = new MutableLiveData<>();

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setDate(String reservationDate) {
        date.setValue(reservationDate);
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
        String dateValue = date.getValue();
        String timeValue = time.getValue();
        String locationValue = location.getValue();
        String websiteValue = website.getValue();

        if (dateValue == null || timeValue == null || locationValue == null || websiteValue == null) {
            errorMessage.setValue("All fields are required.");
            return;
        }

        Log.d(TAG, "Time input: " + timeValue);

        if (!isValidUrl(websiteValue)) {
            errorMessage.setValue("Invalid website URL.");
            return;
        }

        try {
           
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            Date reservationDate = sdf.parse(dateValue + " " + timeValue);

            if (reservationDate == null) {
                errorMessage.setValue("Invalid date or time format. Use MM/DD/YYYY and HH:mm.");
                return;
            }

            DiningReservation reservation = new DiningReservation();
            reservation.setReservationTime(reservationDate);
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

    public MutableLiveData<List<DiningReservation>> getAllReservations() {
            DiningReservationService.getInstance().getAllDiningReservations(
                new DataCallback<List<DiningReservation>>() {
                    @Override
                    public void onSuccess(List<DiningReservation> result) {
                        reservations.setValue(result);
                    }
                    @Override
                    public void onError(Exception e) {
                        reservations.setValue(new ArrayList<>());
                    }
                });
        return reservations;
    }
}

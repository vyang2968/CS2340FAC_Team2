package com.example.sprintproject.viewmodel;

import android.util.Log;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DestinationViewModel extends ViewModel {
    private static final String TAG = "DestinationViewModel";
    private UserService userService;
    private DestinationService destinationService;
    private User currUser;
    private Destination destination;

    private MutableLiveData<String> errorMsg;

    private boolean locationValid;
    private boolean startDateValid;
    private boolean endDateValid;

    public DestinationViewModel() {
        this.userService = UserService.getInstance();
        this.destinationService = DestinationService.getInstance();
        this.currUser = userService.getCurrentUser();
        this.locationValid = false;
        this.startDateValid = false;
        this.endDateValid = false;
    }

    public void setLocation(EditText locationInput) {
        if (validateLocation(locationInput)) {
            destination.setDestination(locationInput.getText().toString());
        }
    }

    private boolean validateLocation(EditText location) {
        String locationText = location.getText().toString();

        if (locationText.isEmpty()) {
            location.setError("Field cannot be empty");
        } else {
            location.setError(null);
        }

        locationValid = location.getError() == null;
        return locationValid;
    }

    public void setStartDate(EditText startDateInput) {
        Date startDate = parseDate(startDateInput);

        if (startDate != null) {
            startDateValid = true;
            destination.setStartDate(startDate);
            Log.i(TAG, "setStartDate:success");
        }

        startDateValid = startDateInput.getError() == null;
    }

    public void setEndDate(EditText endDateInput) {
        Date endDate = parseDate(endDateInput);

        if (endDate != null) {
            destination.setEndDate(endDate);
            Log.i(TAG, "setEndDate:success");
        }

        endDateValid = endDateInput.getError() == null;
    }

    private Date parseDate(EditText input) {
        try {
            Date date = DateFormat.getDateInstance().parse(input.getText().toString());
            Log.i(TAG, "parseDate:success");
            return date;
        } catch (ParseException error) {
            Log.d(TAG, "startDate could not be parsed");
            input.setError("start");
            return null;
        }
    }

    public boolean addDestination() {
        if (locationValid && startDateValid && endDateValid) {
            errorMsg.setValue(null);
            destination.getCollaboratorManager().setCreator(currUser);
            destinationService.addDestination(destination);
            return true;
        } else {
            errorMsg.setValue("Please fix required fields before submitting");
            return false;
        }
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
}

package com.example.sprintproject.viewmodel;

import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DestinationViewModel extends ViewModel {
    private static final String TAG = "DestinationViewModel";
    private UserService userService;
    private DestinationService destinationService;
    private User currUser;
    private Destination destination;

    private MutableLiveData<String> logErrorMsg;
    private MutableLiveData<String> calcErrorMsg;

    private MutableLiveData<Boolean> submitted;
    private MutableLiveData<Boolean> updateUserSuccess;

    private boolean locationValid;
    private boolean destStartDateValid;
    private boolean destEndDateValid;

    private boolean durationValid;
    private boolean userStartDateValid;
    private boolean userEndDateValid;

    public DestinationViewModel() {
        this.userService = UserService.getInstance();
        this.destinationService = DestinationService.getInstance();
        this.currUser = userService.getCurrentUser();
        this.destination = new Destination();

        this.logErrorMsg = new MutableLiveData<>();
        this.calcErrorMsg = new MutableLiveData<>();
        this.submitted = new MutableLiveData<>();
        this.updateUserSuccess = new MutableLiveData<>();

        this.locationValid = false;
        this.destStartDateValid = false;
        this.destEndDateValid = false;
        this.userStartDateValid = false;
        this.userEndDateValid = false;
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

    public Date setDestinationStartDate(EditText startDateInput) {
        Date startDate = parseDate(startDateInput);

        if (startDate != null) {
            destStartDateValid = true;
            destination.setStartDate(startDate);
            Log.i(TAG, "setDestStartDate:success");
        }

        destStartDateValid = startDateInput.getError() == null;
        return startDate;
    }

    public Date setDestinationEndDate(EditText endDateInput) {
        Date endDate = parseDate(endDateInput);

        if (endDate != null) {
            destination.setEndDate(endDate);
            Log.i(TAG, "setDestEndDate:success");
        }

        destEndDateValid = endDateInput.getError() == null;
        return endDate;
    }

    private Date parseDate(EditText input) {
        Log.i(TAG, "parsing " + input.getText().toString());
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date date = format.parse(input.getText().toString());
            Log.i(TAG, "parseDate:success");
            return date;
        } catch (ParseException error) {
            Log.d(TAG, "date could not be parsed");
            input.setError("start");
            return null;
        }
    }

    public boolean addDestination() {
        if (locationValid && destStartDateValid && destEndDateValid) {
            Log.i(TAG, "adding destination...");
            logErrorMsg.setValue(null);
            destination.getCollaboratorManager().setCreator(currUser);
            destinationService.addDestination(destination);
            setSubmitted(true);

            return true;
        } else {
            logErrorMsg.setValue("Please fix required fields before submitting");
            setSubmitted(false);
            return false;
        }
    }

    public Date setUserStartDate(EditText startDateInput) {
        Date startDate = parseDate(startDateInput);

        if (startDate != null) {
            destStartDateValid = true;
            currUser.setStartDate(startDate);
            Log.i(TAG, "setUserStartDate:success");
        }

        destStartDateValid = startDateInput.getError() == null;
        return startDate;
    }

    public Date setUserEndDate(EditText endDateInput) {
        Date endDate = parseDate(endDateInput);

        if (endDate != null) {
            currUser.setEndDate(endDate);
            Log.i(TAG, "setUserEndDate:success");
        }

        destEndDateValid = endDateInput.getError() == null;
        return endDate;
    }

    public void setDuration(EditText durationInput, long duration) {
        String durationText = durationInput.getText().toString();
        if (!durationText.isEmpty()) {
            long num = Long.parseLong(durationText);
            if (num != duration) {
                String msg = String.format("duration should be %d", duration);
                durationInput.setError(msg);
            }
        } else {
            Log.i(TAG, "filled in duration");
            durationInput.setText(String.valueOf(duration));
        }

        durationValid = durationInput.getError() == null;

        if (durationValid) {
            Log.i(TAG, "setDuration:success");
            currUser.setDuration((int) duration);
        }
    }

    public void updateUser() {
        if (durationValid && destStartDateValid && destEndDateValid) {
            Log.i(TAG, "updating user");
            userService.updateUser(currUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    updateUserSuccess.setValue(true);
                    submitted.setValue(true);
                } else {
                    updateUserSuccess.setValue(false);
                    submitted.setValue(false);
                }
            });
        } else {
            Log.i(TAG, String.format("field errors present. duration: %b, start: %b, end: %b", durationValid, destStartDateValid, destEndDateValid));
            calcErrorMsg.setValue("Please fix required fields before submitting");
            updateUserSuccess.setValue(false);
            submitted.setValue(false);
        }
    }

    public long dateDifference(Date a, Date b) {
        long x = a.getTime();
        long y = b.getTime();

        if (y > x) {
            long temp = x;
            x = y;
            y = temp;
        }

        long diff = x - y;
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    public LiveData<Boolean> getUpdateUserSuccess() {
        return updateUserSuccess;
    }

    public void setSubmitted(boolean val) {
        submitted.setValue(val);
    }

    public LiveData<Boolean> getSubmitted() {
        return submitted;
    }

    public LiveData<String> getLogErrorMsg() {
        return logErrorMsg;
    }

    public List<Destination> getDestinations() {
        List<Destination> result = destinationService.getFirstKDestinations(10);

        if (!result.isEmpty()) {
            Log.i(TAG, "returning destinations...");
            return result;
        } else {
            Log.d(TAG, "no results!");
            return null;
        }
    }
}

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
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private MutableLiveData<List<Destination>> destinations;

    private MutableLiveData<Boolean> submitted;
    private MutableLiveData<Boolean> updateUserSuccess;
    private MutableLiveData<Boolean> addDestSuccess;

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
        this.addDestSuccess = new MutableLiveData<>();
        this.destinations = new MutableLiveData<>();

        this.locationValid = false;
        this.destStartDateValid = false;
        this.destEndDateValid = false;
        this.userStartDateValid = false;
        this.userEndDateValid = false;
    }

    public void queryForDestinations() {
        destinations.setValue(new ArrayList<>());
        destinationService.getFirstKDestinations(5, new DataCallback<List<Destination>>() {
            @Override
            public void onSuccess(List<Destination> result) {
                Log.i(TAG, "getDestinations:success");
                destinations.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                Log.d(TAG, "getDestinations:failed");
                destinations.setValue(new ArrayList<>());
            }
        });
    }

    public void setLocation(EditText locationInput) {
        if (validateLocation(locationInput)) {
            destination.setDestination(locationInput.getText().toString());
            Log.i(TAG, "setLocation:success");
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

    public void addDestination() {
        if (locationValid && destStartDateValid && destEndDateValid) {
            Log.i(TAG, "adding destination...");
            logErrorMsg.setValue(null);
            destination.getCollaboratorManager().setCreator(currUser);
            destinationService.addDestination(destination).addOnCompleteListener(bool -> {
                if (bool.isSuccessful()) {
                    Log.i(TAG, "addDestination:success");
                    addDestSuccess.setValue(true);
                    submitted.setValue(true);
                } else {
                    Log.i(TAG, "addDestination:fail");
                    addDestSuccess.setValue(false);
                    submitted.setValue(false);
                }
            });
        } else {
            Log.i(TAG, String.format("field errors present. location: %b, start: %b, end: %b", locationValid, destStartDateValid, destEndDateValid));
            logErrorMsg.setValue("Please fix required fields before submitting");
            addDestSuccess.setValue(false);
            submitted.setValue(false);
        }
    }

    public Date setUserStartDate(EditText startDateInput) {
        Date startDate = parseDate(startDateInput);

        if (startDate != null) {
            currUser.setStartDate(startDate);
            Log.i(TAG, "setUserStartDate:success");
        }

        userStartDateValid = startDateInput.getError() == null;
        return startDate;
    }

    public Date setUserEndDate(EditText endDateInput) {
        Date endDate = parseDate(endDateInput);

        if (endDate != null) {
            currUser.setEndDate(endDate);
            Log.i(TAG, "setUserEndDate:success");
        }

        userEndDateValid = endDateInput.getError() == null;
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
        if (durationValid && userStartDateValid && userEndDateValid) {
            Log.i(TAG, "updating user...");
            userService.updateUser(currUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "updateUser:success");
                    updateUserSuccess.setValue(true);
                    submitted.setValue(true);
                } else {
                    Log.i(TAG, "updateUser:fail");
                    updateUserSuccess.setValue(false);
                    submitted.setValue(false);
                }
            });
        } else {
            Log.i(TAG, String.format("field errors present. duration: %b, start: %b, end: %b", durationValid, userStartDateValid, userEndDateValid));
            calcErrorMsg.setValue("Please fix required fields before submitting");
            updateUserSuccess.setValue(false);
            submitted.setValue(false);
        }
    }

    public long dateDifference(Date a, Date b) {
        if (a == null || b == null) {
            Log.i(TAG, "one of inputs is null");
            return -1;
        }

        long x = a.getTime();
        long y = b.getTime();

        if (x > y) {
            Log.i(TAG, "invalid inputs, x > y");
            calcErrorMsg.setValue("start date must be smaller than end date");
            return -1;
        } else {
            Log.i(TAG, "valid inputs");
            calcErrorMsg.setValue(null);
            long diff = y - x;
            return TimeUnit.MILLISECONDS.toDays(diff);
        }
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
            input.setError("date must be mm/dd/yyyy format");
            return null;
        }
    }

    public LiveData<Boolean> getUpdateUserSuccess() {
        return updateUserSuccess;
    }

    public LiveData<Boolean> getAddDestSuccess() {
        return addDestSuccess;
    }

    public LiveData<Boolean> getSubmitted() {
        return submitted;
    }

    public LiveData<String> getLogErrorMsg() {
        return logErrorMsg;
    }

    public LiveData<String> getCalcErrorMsg() {
        return calcErrorMsg;
    }

    public LiveData<List<Destination>> getDestinations() {
        return destinations;
    }
}

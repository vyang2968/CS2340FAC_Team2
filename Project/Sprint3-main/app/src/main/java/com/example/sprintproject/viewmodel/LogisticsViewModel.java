package com.example.sprintproject.viewmodel;


import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LogisticsViewModel extends ViewModel implements LogSource {

    private static final String TAG = "LogisticsViewModel";
    private final DestinationService destinationService;
    private final UserService userService;
    private final MutableLiveData<Integer> totalDaysTraveled;
    private final MutableLiveData<String> totalDaysTraveledMessage;
    private final MutableLiveData<Integer> totalTripDays;
    private final MutableLiveData<Boolean> hasError;
    private final MutableLiveData<String> currentTripOwnerMessage;
    private final MutableLiveData<Boolean> updateDestinationSucessful;
    private final MutableLiveData<List<Destination>> allDests;
    private final MutableLiveData<User> foundUser;

    public LogisticsViewModel() {
        this.destinationService = DestinationService.getInstance();
        this.userService = UserService.getInstance();
        totalDaysTraveled = new MutableLiveData<>(0);
        totalDaysTraveledMessage = new MutableLiveData<>("Loading Total Days Planned...");
        totalTripDays = new MutableLiveData<>(0);
        hasError = new MutableLiveData<>();
        currentTripOwnerMessage = new MutableLiveData<>();
        updateDestinationSucessful = new MutableLiveData<>();
        allDests = new MutableLiveData<>();
        foundUser = new MutableLiveData<>();
    }

    public void queryTotalTrips() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            totalTripDays.setValue(currentUser.getDuration());
        }
    }

    public void queryForUser(String email) {
        userService.getUserByEmail(email, new DataCallback<User>() {
            @Override
            public void onSuccess(User result) {
                Log.i(TAG, "found user with email");
                foundUser.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                Log.i(TAG, "did not find user with email");
                foundUser.setValue(null);
            }
        });
    }

    public boolean validateInviteInput(EditText input) {
        String inputText = input.getText().toString();

        return true;
    }

    public void updateDaysTraveled() {
        destinationService.getAllDestinations(new DataCallback<List<Destination>>() {
            @Override
            public void onSuccess(List<Destination> result) {
                logInfo("updateDaysTraveled:success");

                String ownerName = result.get(0).getCollaboratorManager()
                                            .getCreator().getUsername();
                String currUsername = userService.getCurrentUser().getUsername();
                currentTripOwnerMessage.setValue((ownerName.equals(currUsername)
                        ? "Your Trip" : ownerName + "'s Trip"));

                allDests.setValue(result);

                int plannedDays = 0;
                for (Destination destination : result) {
                    plannedDays += destination.getDayPlansManager().getDayPlansDetails().size();
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

    public void updateDestinations(User user) {
        for (Destination dest : allDests.getValue()) {
            CollaboratorManager manager = dest.getCollaboratorManager();
            Log.i(TAG, "adding to dest: " + dest.getId());
            manager.addCollaborator(user);
            destinationService.updateDestination(dest).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    updateDestinationSucessful.setValue(true);
                } else {
                    updateDestinationSucessful.setValue(false);
                }
            });
        }
    }

    public void logOutUser() {

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

    public MutableLiveData<Boolean> getHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError.setValue(hasError);
    }

    public MutableLiveData<Boolean> getHasCurrentUser() {
        return userService.getHasCurrentUser();
    }

    public MutableLiveData<String> getCurrentTripMessage() {
        return currentTripOwnerMessage;
    }

    public MutableLiveData<Boolean> getUpdateDestinationSucessful() {
        return updateDestinationSucessful;
    }

    public MutableLiveData<User> getFoundUser() {
        return foundUser;
    }
}


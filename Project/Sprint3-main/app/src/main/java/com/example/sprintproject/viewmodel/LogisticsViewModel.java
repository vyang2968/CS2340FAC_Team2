package com.example.sprintproject.viewmodel;


import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.AuthService;
import com.example.sprintproject.service.DestinationService;
import com.example.sprintproject.service.TripService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.example.sprintproject.utils.LogSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogisticsViewModel extends ViewModel implements LogSource {
    private static final String TAG = "LogisticsViewModel";
    private final DestinationService destinationService;
    private final UserService userService;
    private final TripService tripService;
    private final MutableLiveData<Integer> totalDaysTraveled;
    private final MutableLiveData<String> totalDaysTraveledMessage;
    private final MutableLiveData<Integer> totalTripDays;
    private final MutableLiveData<Boolean> hasError;
    private final MutableLiveData<String> currentTripName;
    private final MutableLiveData<String> currentTripOwner;
    private final MutableLiveData<Boolean> updateTripSuccessful;
    private final MutableLiveData<Boolean> updateUserSuccessful;
    private final MutableLiveData<HashMap<Trip, User>> tripsAndUsers;
    private final MutableLiveData<Boolean> doneQueryingForAllTrips;
    private final MutableLiveData<List<Destination>> allDests;
    private final MutableLiveData<Boolean> doneQueryingForAllDests;
    private final MutableLiveData<Boolean> doneQueryingForAllUsers;
    private final MutableLiveData<User> foundUser;
    private final MutableLiveData<Boolean> tripCreationSuccessful;
    private final MutableLiveData<Boolean> tripsAreSelectable;
    private final MutableLiveData<Integer> addSuccesses;

    public LogisticsViewModel() {
        this.destinationService = DestinationService.getInstance();
        this.userService = UserService.getInstance();
        this.tripService = TripService.getInstance();
        totalDaysTraveled = new MutableLiveData<>(0);
        totalDaysTraveledMessage = new MutableLiveData<>("Loading Total Days Planned...");
        totalTripDays = new MutableLiveData<>(0);
        hasError = new MutableLiveData<>();
        currentTripName = new MutableLiveData<>();
        updateTripSuccessful = new MutableLiveData<>();
        allDests = new MutableLiveData<>(new ArrayList<>());
        foundUser = new MutableLiveData<>();
        tripCreationSuccessful = new MutableLiveData<>();
        currentTripOwner = new MutableLiveData<>();
        doneQueryingForAllTrips = new MutableLiveData<>();
        doneQueryingForAllDests = new MutableLiveData<>();
        tripsAreSelectable = new MutableLiveData<>(false);
        updateUserSuccessful = new MutableLiveData<>();
        doneQueryingForAllUsers = new MutableLiveData<>();
        tripsAndUsers = new MutableLiveData<>(new HashMap<>());
        addSuccesses = new MutableLiveData<>(0);
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

    public void queryForAllTrips() {
        User currUser = userService.getCurrentUser();
        List<String> tripsIds = currUser.getTripIds();
        if (tripsIds.isEmpty()) {
            doneQueryingForAllTrips.setValue(true);
            return;
        }

        for (String tripId : tripsIds) {
            tripService.getTrip(tripId, new DataCallback<Trip>() {
                @Override
                public void onSuccess(Trip result) {
                    if (result.getId().equals(currUser.getActiveTripId())) {
                        tripService.setCurrentTrip(result);
                        currentTripName.setValue(result.getName());
                    }

                    HashMap<Trip, User> map = tripsAndUsers.getValue();
                    map.putIfAbsent(result, null);
                    tripsAndUsers.setValue(map);

                    if (map.keySet().size() == tripsIds.size()) {
                        doneQueryingForAllTrips.setValue(true);
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "error fetching trip " + tripId);
                }
            });
        }
    }

    public void queryForAllUsers() {
        User currUser = userService.getCurrentUser();

        for (Trip trip : tripsAndUsers.getValue().keySet()) {
            userService.getUser(trip.getManager().getCreator(), new DataCallback<User>() {
                @Override
                public void onSuccess(User result) {
                    String currTripCreator = tripService.getCurrentTrip().getManager().getCreator();

                    if (currTripCreator.equals(result.getId())
                            && currTripCreator.equals(currUser.getId())
                            && currUser.getId().equals(result.getId())) {
                        currentTripOwner.setValue("You");
                    } else if (currTripCreator.equals(result.getId())) {
                        currentTripOwner.setValue(result.getUsername());
                    }

                    HashMap<Trip, User> map = tripsAndUsers.getValue();
                    map.put(trip, result);
                    tripsAndUsers.setValue(map);
                    addSuccesses.setValue(addSuccesses.getValue() + 1);

                    if (addSuccesses.getValue() == tripsAndUsers.getValue().size()) {
                        doneQueryingForAllUsers.setValue(true);
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "error fetching user");
                }
            });
        }
    }

    public void queryForAllDests() {
        Trip currTrip = tripService.getCurrentTrip();
        List<String> destinationsIds = currTrip.getDestinationsIds();
        for (String destinationId : destinationsIds) {
            destinationService.getDestination(destinationId, new DataCallback<Destination>() {
                @Override
                public void onSuccess(Destination result) {
                    List<Destination> dests = allDests.getValue();
                    dests.add(result);
                    allDests.setValue(dests);

                    totalDaysTraveled.setValue(
                            totalDaysTraveled.getValue()
                                    + result.getDayPlansManager().getDayPlansDetails().size()
                    );

                    if (dests.size() == destinationsIds.size()) {
                        doneQueryingForAllDests.setValue(true);
                        int totalDays = (totalTripDays.getValue() != null)
                                ? totalTripDays.getValue() : 0;

                        totalDaysTraveledMessage.setValue(
                                String.format("Days Planned: %d of %d total days",
                                        totalDaysTraveled.getValue(), totalDays));
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.i(TAG, "error fetching trip " + destinationId);
                }
            });
        }
    }

    public boolean validateInviteInput(EditText input) {
        String inputText = input.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(inputText).matches()) {
            input.setError("Must be a valid email");
        } else {
            input.setError(null);
        }
        return input.getError() == null;
    }

    public void refreshTotalTripDays() {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            totalTripDays.setValue(currentUser.getDuration());
            queryForAllDests();
        }
    }

    public void createTrip(String tripName) {
        User currUser = userService.getCurrentUser();
        Trip trip = new Trip();
        trip.setName(tripName);
        trip.getManager().setCreator(currUser.getId());
        tripService.addTrip(trip).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tripCreationSuccessful.setValue(true);
                currUser.getTripIds().add(trip.getId());
                userService.updateUser(currUser);
            } else {
                tripCreationSuccessful.setValue(false);
            }
        });
    }

    public void changeCurrentTrip(Trip trip) {
        Log.i(TAG, "changeCurrentTrip");
        User currUser = userService.getCurrentUser();
        currUser.setActiveTripId(trip.getId());
        userService.updateUser(currUser);
    }

    public boolean isActiveTrip(Trip trip) {
        return userService.getCurrentUser().getActiveTripId().equals(trip.getId());
    }

    public void updateCollaborator(User user) {
        Trip currTrip = tripService.getCurrentTrip();
        List<String> tripIds = user.getTripIds();
        tripIds.add(currTrip.getId());

        Log.i(TAG, "adding trip " + currTrip.getId() + " to user " + user.getId());
        userService.updateUser(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateUserSuccessful.setValue(true);
            } else {
                updateUserSuccessful.setValue(false);
            }
        });
    }

    public void updateTrip(User user) {
        Trip currTrip = tripService.getCurrentTrip();
        CollaboratorManager manager = currTrip.getManager();
        manager.getCollaborators().add(user.getId());
        currTrip.setManager(manager);

        Log.i(TAG, "adding user " + user.getId() + " to trip " + currTrip.getId());
        tripService.updateTrip(currTrip).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateTripSuccessful.setValue(true);
            } else {
                updateTripSuccessful.setValue(false);
            }
        });
    }

    public void logOutUser() {
        AuthService.getInstance().logOutUser();
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

    public MutableLiveData<String> getCurrentTripName() {
        return currentTripName;
    }

    public MutableLiveData<String> getCurrentTripOwner() {
        return currentTripOwner;
    }

    public MutableLiveData<Boolean> getUpdateTripSuccessful() {
        return updateTripSuccessful;
    }

    public MutableLiveData<Boolean> getUpdateUserSuccessful() {
        return updateUserSuccessful;
    }

    public MutableLiveData<Boolean> getTripCreationSuccessful() {
        return tripCreationSuccessful;
    }

    public MutableLiveData<User> getFoundUser() {
        return foundUser;
    }


    public void setDoneQueryingForAllTrips(boolean val) {
        doneQueryingForAllTrips.setValue(val);
    }

    public MutableLiveData<Boolean> getDoneQueryingForAllTrips() {
        return doneQueryingForAllTrips;
    }

    public void setTripsAreSelectable(boolean val) {
        tripsAreSelectable.setValue(val);
    }

    public MutableLiveData<Boolean> getTripsAreSelectable() {
        return tripsAreSelectable;
    }

    public MutableLiveData<Boolean> getDoneQueryingForAllUsers() {
        return doneQueryingForAllUsers;
    }

    public MutableLiveData<HashMap<Trip, User>> getTripsAndUsers() {
        return tripsAndUsers;
    }
}


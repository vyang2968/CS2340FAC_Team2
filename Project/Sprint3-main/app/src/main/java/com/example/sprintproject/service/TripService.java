package com.example.sprintproject.service;

import com.example.sprintproject.model.Trip;
import com.example.sprintproject.repository.TripRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class TripService {
    private final TripRepositoryImpl tripRepositoryImpl;
    private static TripService instance;
    private static final String TAG = "TripService";
    private Trip currentTrip;

    private TripService() {
        this.tripRepositoryImpl = new TripRepositoryImpl();
        currentTrip = new Trip();
    }

    public static TripService getInstance() {
        if (instance == null) {
            instance = new TripService();
        }
        return instance;
    }

    public void setCurrentTrip(Trip trip) {
        this.currentTrip = trip;
    }

    public Trip getCurrentTrip() {
        return this.currentTrip;
    }

    public Task<Void> addTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("trip cannot be null");
        }

        return tripRepositoryImpl.addTrip(trip);
    }

    public void getTrip(String id, DataCallback<Trip> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        tripRepositoryImpl.getTripById(id, callback);
    }

    public void getAllTrips(DataCallback<List<Trip>> callback) {
        tripRepositoryImpl.getAllTrips(callback);
    }

    public Task<Void> updateTrip(Trip trip) {
        if (trip == null) {
            throw new IllegalArgumentException("trip cannot be null");
        }

        return tripRepositoryImpl.updateTrip(trip);
    }
}

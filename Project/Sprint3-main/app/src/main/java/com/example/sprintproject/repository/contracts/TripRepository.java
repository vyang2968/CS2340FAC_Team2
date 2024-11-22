package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Trip;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface TripRepository {
    Task<Void> addTrip(Trip trip);
    void getTripById(String userId, DataCallback<Trip> callback);
    void getAllTrips(DataCallback<List<Trip>> callback);
    Task<Void> updateTrip(Trip trip);
    Task<Void> deleteTrip(String id);
}

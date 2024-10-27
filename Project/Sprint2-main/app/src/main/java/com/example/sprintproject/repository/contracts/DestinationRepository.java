package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface DestinationRepository {
    Task<Void> addDestination(Destination destination);
    void getDestinationById(String id, DataCallback<Destination> callback);
    void getFirstKDestinations(int k, DataCallback<List<Destination>> callback);
    void getAllDestinations(DataCallback<List<Destination>> callback);
    Task<Void> updateDestination(Destination destination);
    Task<Void> deleteDestination(String id);
}

package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

public interface DestinationRepository {
    Task<Void> addDestination(Destination destination);
    void getDestinationById(String id, DataCallback<Destination> callback);
    Task<Void> updateDestination(Destination destination);
    Task<Void> deleteDestination(String id);
}

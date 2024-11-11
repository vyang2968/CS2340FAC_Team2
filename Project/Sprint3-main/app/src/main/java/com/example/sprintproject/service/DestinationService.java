package com.example.sprintproject.service;




import com.example.sprintproject.model.Destination;
import com.example.sprintproject.repository.DestinationRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;


import java.util.List;

public class DestinationService {
    private final DestinationRepositoryImpl destinationRepository;
    private static DestinationService instance;
    private static final String TAG = "DestService";

    private DestinationService() {
        this.destinationRepository = new DestinationRepositoryImpl();
    }

    public static DestinationService getInstance() {
        if (instance == null) {
            instance = new DestinationService();
        }
        return instance;
    }

    public Task<Void> addDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("destination cannot be null");
        }

        return destinationRepository.addDestination(destination);
    }

    public void getDestination(String id, DataCallback<Destination> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        destinationRepository.getDestinationById(id, callback);
    }

    public void getFirstKDestinations(int k, DataCallback<List<Destination>> callback) {
        if (k <= 0) {
            throw new IllegalArgumentException("k needs to be greater than 0");
        }

        destinationRepository.getFirstKDestinations(k, callback);
    }

    public void getAllDestinations(DataCallback<List<Destination>> callback) {
        destinationRepository.getAllDestinations(callback);
    }

    public Task<Void> updateDestination(Destination destination) {
        if (destination == null) {
            throw new IllegalArgumentException("destination cannot be null");
        }
        return destinationRepository.updateDestination(destination);
    }
}

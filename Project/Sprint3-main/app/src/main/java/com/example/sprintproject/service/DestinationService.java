package com.example.sprintproject.service;



import com.example.sprintproject.model.Destination;
import com.example.sprintproject.repository.DestinationRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;


import java.util.List;

public class DestinationService {
    private DestinationRepositoryImpl destinationRepository;
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

    public Destination getDestination(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        destinationRepository.getDestinationById(id, new DataCallback<Destination>() {
            @Override
            public void onSuccess(Destination result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

        return null;
    }

    public void getFirstKDestinations(int k, DataCallback<List<Destination>> callback) {
        destinationRepository.getFirstKDestinations(k, callback);
    }

    public void getAllDestinations(DataCallback<List<Destination>> callback) {
        destinationRepository.getAllDestinations(callback);
    }
}

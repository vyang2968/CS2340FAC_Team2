package com.example.sprintproject.service;

import android.util.Log;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.repository.DestinationRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class DestinationService {
    private DestinationRepositoryImpl destinationRepository;
    private static DestinationService instance;

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

    public List<Destination> getFirstKDestinations(int k) {
        List<Destination> destinations = new ArrayList<>();
        destinationRepository.getFirstKDestinations(k, new DataCallback<List<Destination>>() {
            @Override
            public void onSuccess(List<Destination> result) {
                destinations.addAll(result);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        return destinations;
    }
}

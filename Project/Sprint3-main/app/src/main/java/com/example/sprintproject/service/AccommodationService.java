package com.example.sprintproject.service;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.repository.AccommodationRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class AccommodationService {
    private final AccommodationRepositoryImpl accommodationRepository;
    private static AccommodationService instance;
    private static final String TAG = "AccommodationService";

    private AccommodationService() {
        this.accommodationRepository = new AccommodationRepositoryImpl();
    }

    public static AccommodationService getInstance() {
        if (instance == null) {
            instance = new AccommodationService();
        }
        return instance;
    }

    public Task<Void> addAccommodation(Accommodation accommodation) {
        if (accommodation == null) {
            throw new IllegalArgumentException("accommodation cannot be null");
        }

        return accommodationRepository.addAccommodation(accommodation);
    }

    public void getAccommodation(String id, DataCallback<Accommodation> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        accommodationRepository.getAccommodationById(id, callback);
    }

    public void getAllAccommodations(DataCallback<List<Accommodation>> callback) {
        accommodationRepository.getAllAccommodations(callback);
    }

    public Task<Void> updateAccommodation(Accommodation accommodation) {
        if (accommodation == null) {
            throw new IllegalArgumentException("destination cannot be null");
        }

        return accommodationRepository.updateAccommodation(accommodation);
    }
}

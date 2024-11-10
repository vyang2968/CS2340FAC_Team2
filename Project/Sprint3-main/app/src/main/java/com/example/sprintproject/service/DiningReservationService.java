package com.example.sprintproject.service;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.repository.AccommodationRepositoryImpl;
import com.example.sprintproject.repository.DiningReservationRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class DiningReservationService {
    private final DiningReservationRepositoryImpl diningReservationRepository;
    private static DiningReservationService instance;
    private static final String TAG = "DiningReservationService";

    private DiningReservationService() {
        this.diningReservationRepository = new DiningReservationRepositoryImpl();
    }

    public static DiningReservationService getInstance() {
        if (instance == null) {
            instance = new DiningReservationService();
        }
        return instance;
    }

    public Task<Void> addDiningReservation(DiningReservation diningReservation) {
        if (diningReservation == null) {
            throw new IllegalArgumentException("accommodation cannot be null");
        }

        return diningReservationRepository.addReservation(diningReservation);
    }

    public void getDiningReservation(String id, DataCallback<DiningReservation> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        diningReservationRepository.getReservationById(id, callback);
    }

    public void getAllDiningReservations(DataCallback<List<DiningReservation>> callback) {
        diningReservationRepository.getAllReservations(callback);
    }

    public Task<Void> updateDiningReservation(DiningReservation diningReservation) {
        if (diningReservation == null) {
            throw new IllegalArgumentException("destination cannot be null");
        }

        return diningReservationRepository.updateReservation(diningReservation);
    }
}

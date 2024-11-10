package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface DiningReservationRepository {
    Task<Void> addReservation(DiningReservation reservation);
    void getReservationById(String id, DataCallback<DiningReservation> callback);
    void getAllReservations(DataCallback<List<DiningReservation>> callback);
    Task<Void> updateReservation(DiningReservation reservation);
    Task<Void> deleteReservation(String id);
}

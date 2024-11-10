package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.User;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface AccommodationRepository {
    Task<Void> addAccommodation(Accommodation accommodation);
    void getAccommodationById(String id, DataCallback<Accommodation> callback);
    void getAllAccommodations(DataCallback<List<Accommodation>> callback);
    Task<Void> updateAccommodation(Accommodation accommodation);
    Task<Void> deleteAccommodation(String id);
}

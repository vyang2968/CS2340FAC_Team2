package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

public interface AccommodationRepository {
    Task<Void> addAccommodation(Accommodation accommodation);
    void getAccommodationById(String id, DataCallback<Accommodation> callback);
    Task<Void> updateAccommodation(Accommodation accommodation);
    Task<Void> deleteAccommodation(String id);
}

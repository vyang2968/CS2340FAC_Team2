package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.repository.contracts.AccommodationRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccommodationRepositoryImpl implements AccommodationRepository {
    private static final String TAG = "AccomRepoImpl";
    private DatabaseReference dbRef;

    public AccommodationRepositoryImpl() {
        Log.i(TAG, "connecting to accommodations database...");
        this.dbRef = FirebaseDatabase.getInstance().getReference().child("accommodations");
    }

    @Override
    public Task<Void> addAccommodation(Accommodation accommodation) {
        String id = dbRef.push().getKey();
        accommodation.setId(id);
        return dbRef.push().setValue(accommodation);
    }

    @Override
    public void getAccommodationById(String id, DataCallback<Accommodation> callback) {
        dbRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onSuccess(snapshot.getValue(Accommodation.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public Task<Void> updateAccommodation(Accommodation accommodation) {
        return dbRef.child(accommodation.getId()).setValue(accommodation);
    }

    @Override
    public Task<Void> deleteAccommodation(String id) {
        return dbRef.child(id).removeValue();
    }
}

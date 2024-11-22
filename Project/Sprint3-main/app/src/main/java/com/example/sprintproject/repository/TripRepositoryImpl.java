package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.repository.contracts.TripRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripRepositoryImpl implements TripRepository {
    private static final String TAG = "TripRepoImpl";
    private final DatabaseReference dbRef;

    public TripRepositoryImpl() {
        Log.i(TAG, "connecting to trips database...");
        this.dbRef = FirebaseDatabase.getInstance().getReference().child("trips");
    }

    @Override
    public Task<Void> addTrip(Trip trip) {
        String id = dbRef.push().getKey();
        trip.setId(id);
        return dbRef.child(id).setValue(trip);
    }

    @Override
    public void getTripById(String id, DataCallback<Trip> callback) {
        dbRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onSuccess(snapshot.getValue(Trip.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public void getAllTrips(DataCallback<List<Trip>> callback) {
        dbRef.orderByChild("id").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Trip> results = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            results.add(dataSnapshot.getValue(Trip.class));
                        }

                        callback.onSuccess(results);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError(error.toException());
                    }
                }
        );
    }

    @Override
    public Task<Void> updateTrip(Trip trip) {
        return dbRef.child(trip.getId()).setValue(trip);
    }

    @Override
    public Task<Void> deleteTrip(String id) {
        return dbRef.child(id).removeValue();
    }
}

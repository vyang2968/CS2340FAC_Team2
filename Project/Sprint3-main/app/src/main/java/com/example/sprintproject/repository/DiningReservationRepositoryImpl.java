package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.repository.contracts.DiningReservationRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiningReservationRepositoryImpl implements DiningReservationRepository {
    private static final String TAG = "DiningResRepoImpl";
    private final DatabaseReference dbRef;

    public DiningReservationRepositoryImpl() {
        Log.i(TAG, "connecting to dining reservation...");
        this.dbRef = FirebaseDatabase.getInstance().getReference().child("dining_reservations");
    }

    @Override
    public Task<Void> addReservation(DiningReservation reservation) {
        String id = dbRef.push().getKey();
        reservation.setId(id);
        return dbRef.push().setValue(reservation);
    }

    @Override
    public void getReservationById(String id, DataCallback<DiningReservation> callback) {
        dbRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onSuccess(snapshot.getValue(DiningReservation.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public Task<Void> updateReservation(DiningReservation reservation) {
        return dbRef.child(reservation.getId()).setValue(reservation);
    }

    @Override
    public Task<Void> deleteReservation(String id) {
        return dbRef.child(id).removeValue();
    }
}

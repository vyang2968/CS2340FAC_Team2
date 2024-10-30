package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.repository.contracts.DestinationRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DestinationRepositoryImpl implements DestinationRepository {
    private static final String TAG = "DestRepoImpl";
    private final DatabaseReference destDBRef;

    public DestinationRepositoryImpl() {
        Log.i(TAG, "connecting to destinations database...");
        this.destDBRef = FirebaseDatabase.getInstance().getReference().child("destinations");
    }

    @Override
    public Task<Void> addDestination(Destination destination) {
        String id = destDBRef.push().getKey();
        destination.setId(id);
        return destDBRef.push().setValue(destination);
    }

    @Override
    public void getDestinationById(String id, DataCallback<Destination> callback) {
        destDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Destination dest = snapshot.getValue(Destination.class);
                callback.onSuccess(dest);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public void getFirstKDestinations(int k, DataCallback<List<Destination>> callback) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Destination> results = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    results.add(dataSnapshot.getValue(Destination.class));
                }

                callback.onSuccess(results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        };

        destDBRef.orderByChild("location").limitToFirst(k).addValueEventListener(eventListener);
    }

    @Override
    public void getAllDestinations(DataCallback<List<Destination>> callback) {
        destDBRef.orderByChild("location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Destination> results = new ArrayList<>();

                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    results.add(iterator.next().getValue(Destination.class));
                }

                callback.onSuccess(results);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }




    @Override
    public Task<Void> updateDestination(Destination destination) {
        return destDBRef.child(destination.getId()).setValue(destination);
    }

    @Override
    public Task<Void> deleteDestination(String id) {
        return destDBRef.child(id).removeValue();
    }
}

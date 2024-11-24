package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.repository.contracts.TravelPostRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TravelPostRepositoryImpl implements TravelPostRepository {
    private static final String TAG = "TravelPost";
    private final DatabaseReference travelPostsRef;

    public TravelPostRepositoryImpl() {
        Log.i(TAG, "connecting to travel posts database...");
        this.travelPostsRef =
                FirebaseDatabase.getInstance().getReference().child("travel_posts");
    }

    @Override
    public Task<Void> addTravelPost(TravelPost post) {
        String id = travelPostsRef.push().getKey();
        post.setId(id);
        return travelPostsRef.child(post.getId()).setValue(post);
    }

    @Override
    public void getTravelPostById(String id, DataCallback<TravelPost> callback) {
        travelPostsRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onSuccess(snapshot.getValue(TravelPost.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public Task<Void> updateTravelPost(TravelPost post) {
        return travelPostsRef.child(post.getId()).setValue(post);

    }

    @Override
    public Task<Void> deleteTravelPost(String id) {
        return travelPostsRef.child(id).removeValue();
    }
}

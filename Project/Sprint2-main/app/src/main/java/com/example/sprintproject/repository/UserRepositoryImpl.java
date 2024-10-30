package com.example.sprintproject.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sprintproject.model.User;
import com.example.sprintproject.repository.contracts.UserRepository;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRepositoryImpl implements UserRepository {
    private static final String TAG = "UserRepoImpl";
    private final DatabaseReference usersRef;

    public UserRepositoryImpl() {
        Log.i(TAG, "connecting to users database...");
        this.usersRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    @Override
    public Task<Void> addUser(User user) {
        Log.d(TAG, "adding to users database...");
        return usersRef.child(user.getId()).setValue(user);
    }

    @Override
    public void getUserById(String id, DataCallback<User> callback) {
        Log.d(TAG, "getting from users database...");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "getUserById:success");
                User user = snapshot.getValue(User.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "getUserById:failed");
                callback.onError(error.toException());
            }
        });
    }

    @Override
    public Task<Void> updateUser(User user) {
        Log.d(TAG, "updating to users database...");
        return usersRef.child(user.getId()).setValue(user);
    }

    @Override
    public Task<Void> deleteUser(String id) {
        Log.d(TAG, "deleting from users database...");
        return usersRef.child(id).removeValue();
    }
}

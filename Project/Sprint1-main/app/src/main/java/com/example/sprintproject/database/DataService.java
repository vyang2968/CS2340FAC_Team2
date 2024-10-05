package com.example.sprintproject.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataService {
    private FirebaseDatabase db;
    private final static String TAG = "DataService";

    public DataService() {
        Log.i(TAG, "Connecting to database...");
        this.db = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getDBRef(String key) {
        return db.getReference(key);
    }
}

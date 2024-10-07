package com.example.sprintproject.database;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataService {
    private FirebaseDatabase db;
    private static final String TAG = "DataService";

    public DataService() {
        Log.i(TAG, "Connecting to database...");
        this.db = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getDBRef(String key) {
        return db.getReference(key);
    }
}

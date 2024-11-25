package com.example.sprintproject.service;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.repository.TravelPostRepositoryImpl;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class TravelPostService {
    private final TravelPostRepositoryImpl travelPostRepositoryImpl;
    private static TravelPostService instance;
    private static final String TAG = "TravelService";

    public static TravelPostService getInstance() {
        if (instance == null) {
            instance = new TravelPostService();
        }
        return instance;
    }

    private TravelPostService() {
        this.travelPostRepositoryImpl = new TravelPostRepositoryImpl();
    }

    public Task<Void> addTravelPost(TravelPost post) {
        if (post == null) {
            throw new IllegalArgumentException("post cannot be null");
        }

        return travelPostRepositoryImpl.addTravelPost(post);
    }

    public void getTravelPost(String id, DataCallback<TravelPost> callback) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (id.isEmpty()) {
            throw new IllegalArgumentException("id cannot be empty");
        }

        travelPostRepositoryImpl.getTravelPostById(id, callback);
    }

    public void getAllTravelPosts(DataCallback<List<TravelPost>> callback) {
        travelPostRepositoryImpl.getAllTravelPosts(callback);
    }
}

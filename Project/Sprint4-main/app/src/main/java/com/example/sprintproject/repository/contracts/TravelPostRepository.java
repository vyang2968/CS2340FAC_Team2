package com.example.sprintproject.repository.contracts;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.utils.DataCallback;
import com.google.android.gms.tasks.Task;

public interface TravelPostRepository {
    Task<Void> addTravelPost(TravelPost post);
    void getTravelPostById(String id, DataCallback<TravelPost> callback);
    Task<Void> updateTravelPost(TravelPost post);
    Task<Void> deleteTravelPost(String id);
}

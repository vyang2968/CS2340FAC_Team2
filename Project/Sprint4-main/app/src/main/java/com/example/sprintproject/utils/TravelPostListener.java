package com.example.sprintproject.utils;

import com.example.sprintproject.model.TravelPost;

public interface TravelPostListener {
    void observePost(TravelPost post);
    default void subscribe(TravelPostObservable observable) {
        observable.addListener(this);
    }
    default void unsubscribe(TravelPostObservable observable) {
        observable.removeListener(this);
    }
}

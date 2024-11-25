package com.example.sprintproject.utils;

import com.example.sprintproject.model.TravelPost;

public interface TravelPostObservable {
    void addListener(TravelPostListener listener);
    void removeListener(TravelPostListener listener);

    void updateListeners(TravelPost post);
}

package com.example.sprintproject.utils;

import com.example.sprintproject.model.TravelPost;

import java.util.List;

public interface TravelPostObservable {
    void addListener(TravelPostListener listener);
    void removeListener(TravelPostListener listener);

    void updateListeners(TravelPost post);
}

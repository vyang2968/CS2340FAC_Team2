package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.service.TravelPostService;
import com.example.sprintproject.utils.LogSource;
import com.example.sprintproject.utils.PlannableSortMethod;
import com.example.sprintproject.utils.SortMethod;
import com.example.sprintproject.utils.TravelPostListener;
import com.example.sprintproject.utils.TravelPostObservable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TravelCommunityViewModel extends ViewModel implements LogSource, TravelPostObservable {

    private static final String TAG = "TravelCommunityViewModel";
    private Set<TravelPostListener> listeners;

    private final MutableLiveData<List<TravelPost>> travelPosts;
    private final SimpleDateFormat dateFormat;
    private final TravelPostService travelPostService;

    public TravelCommunityViewModel() {
        this.travelPosts = new MutableLiveData<>();
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        this.listeners = new HashSet<>();
        this.travelPostService = TravelPostService.getInstance();
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public MutableLiveData<List<TravelPost>> getTravelPosts() {
        return travelPosts;
    }

    public void addTravelPost(String start, String end,
                              String destination, String accommodations,
                              String reservations, String notes) {
        
        List<TravelPost> list = travelPosts.getValue();

        if (list == null) {
            list = new ArrayList<>();
        }

        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            TravelPost travelPost = new TravelPost();
            travelPost.setStartDate(startDate);
            travelPost.setEndDate(endDate);
            travelPost.setDestination(destination);
            travelPost.setAccommodations(accommodations);
            travelPost.setDiningReservations(reservations);

            if (notes != null && !notes.trim().isEmpty()) {
                Note note = new Note();
                note.setNote(notes);
                travelPost.addNote(note);
            }

            travelPostService.addTravelPost(travelPost)
                    .addOnSuccessListener(aVoid -> {

                        List<TravelPost> currentPosts = travelPosts.getValue();
                        if (currentPosts == null) {
                            currentPosts = new ArrayList<>();
                        }
                        currentPosts.add(travelPost);
                        travelPosts.setValue(currentPosts);

                        updateListeners(travelPost);
                        Log.i(TAG, "Successfully added travel post");
                    })
                    .addOnFailureListener(e ->
                            Log.e(TAG, "Error adding travel post", e));
            list.add(travelPost);
        } catch (ParseException error) {
            Log.d(TAG, "date could not be parsed");
            throw new IllegalArgumentException(error);
        }

    }

    @Override
    public void addListener(TravelPostListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(TravelPostListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void updateListeners(TravelPost post) {
        for (TravelPostListener listener : listeners) {
            listener.observePost(post);
        }
    }
}

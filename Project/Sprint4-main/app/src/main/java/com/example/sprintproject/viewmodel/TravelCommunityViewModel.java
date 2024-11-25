package com.example.sprintproject.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.service.TravelPostService;
import com.example.sprintproject.service.TripService;
import com.example.sprintproject.service.UserService;
import com.example.sprintproject.utils.DataCallback;
import com.example.sprintproject.utils.LogSource;
import com.example.sprintproject.utils.TravelPostFactory;
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
    private boolean first = true;

    private final MutableLiveData<List<TravelPost>> travelPosts;
    private final SimpleDateFormat dateFormat;
    private final TravelPostService travelPostService;
    private TravelPostFactory postFactory;

    public TravelCommunityViewModel() {
        this.travelPosts = new MutableLiveData<>();
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        this.listeners = new HashSet<>();
        this.travelPostService = TravelPostService.getInstance();
        this.postFactory = new TravelPostFactory(TripService.getInstance().getCurrentTrip());
    }

    @Override
    public String getTag() {
        return TAG;
    }

    public MutableLiveData<List<TravelPost>> getTravelPosts() {
        TravelPostService.getInstance().getAllTravelPosts(
                new DataCallback<List<TravelPost>>() {
                    @Override
                    public void onSuccess(List<TravelPost> result) {
                        travelPosts.setValue(result);
                        if (result != null && first) {
                            first = false;
                            for (TravelPost post : result) {
                                updateListeners(post);
                            }
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        travelPosts.setValue(new ArrayList<>());
                    }
                });
        return travelPosts;
    }

    public void addTravelPost(String start, String end,
                              String destination, String accommodations,
                              String reservations, String notes) {

        try {
            Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);

            TravelPost travelPost = postFactory.create(startDate, endDate,
                    destination, accommodations, reservations);

            if (notes != null && !notes.trim().isEmpty()) {
                Note note = new Note();
                note.setCreator(UserService.getInstance().getCurrentUser());
                note.setNote(notes);
                travelPost.setNotes(note);
            }

            travelPostService.addTravelPost(travelPost)
                    .addOnSuccessListener(aVoid -> {
                        updateListeners(travelPost);
                        Log.i(TAG, "Successfully added travel post");
                    })
                    .addOnFailureListener(e ->
                            Log.e(TAG, "Error adding travel post", e));
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

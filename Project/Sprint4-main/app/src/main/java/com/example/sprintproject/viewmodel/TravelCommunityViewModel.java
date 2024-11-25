package com.example.sprintproject.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.utils.LogSource;
import com.example.sprintproject.utils.PlannableSortMethod;
import com.example.sprintproject.utils.SortMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TravelCommunityViewModel extends ViewModel implements LogSource {

    private static final String TAG = "TravelCommunityViewModel";

    private final MutableLiveData<List<TravelPost>> travelPosts;
    private final SimpleDateFormat dateFormat;

    public TravelCommunityViewModel() {
        this.travelPosts = new MutableLiveData<>();
        this.dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
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

        //list.add(travelPost);
    }
}

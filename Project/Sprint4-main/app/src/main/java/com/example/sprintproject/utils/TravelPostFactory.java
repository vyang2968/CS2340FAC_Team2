package com.example.sprintproject.utils;

import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.model.Trip;

import java.util.Date;

public class TravelPostFactory {

    private String tripId;

    public TravelPostFactory(Trip trip) {
        if (trip != null) {
            this.tripId = trip.getId();
        } else {
            this.tripId = "";
        }
    }

    public TravelPost create(Date startDate, Date endDate,
                             String destination, String accommodations,
                             String diningReservations) {
        return new TravelPost(tripId, startDate, endDate,
                destination, accommodations, diningReservations);
    }
}

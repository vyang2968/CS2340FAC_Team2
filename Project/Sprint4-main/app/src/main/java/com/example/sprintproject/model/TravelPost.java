package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelPost {
    private String id;
    private String tripId;
    private Date startDate;
    private Date endDate;
    private String destination;
    private String accommodations;
    private String diningReservations;
    private Note notes;

    public TravelPost() {
        this.id = "";
        this.tripId = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.destination = "";
        this.accommodations = "";
        this.diningReservations = "";
        this.notes = new Note();
    }

    public TravelPost(String tripId, Date startDate, Date endDate,
                      String destination, String accommodations,
                      String diningReservations) {
        this.tripId = tripId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.destination = destination;
        this.accommodations = accommodations;
        this.diningReservations = diningReservations;
        this.notes = new Note();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public String getDiningReservations() {
        return diningReservations;
    }

    public void setDiningReservations(String diningReservations) {
        this.diningReservations = diningReservations;
    }

    public Note getNotes() {
        return notes;
    }

    public void setNotes(Note notes) {
        this.notes = notes;
    }
}
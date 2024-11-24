package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class TravelPost {
    private String id;
    private String tripId;
    private List<Note> notes;

    public TravelPost() {
        this.id = "";
        this.tripId = "";
        this.notes = new ArrayList<>();
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

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}

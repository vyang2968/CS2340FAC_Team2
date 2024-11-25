package com.example.sprintproject.utils;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.model.User;

public class TravelPostFactory {

    private String tripId;
    private User user;

    public TravelPostFactory(String tripId, User user) {
        this.tripId = tripId;
        this.user = user;
    }

    public TravelPost create(String start, String end,
            String destination, String accommodations,
            String reservations, String notes) {
        TravelPost post = new TravelPost();

        post.setTripId(tripId);

        // Setup Logic for other variables Goes Here
        Note note = new Note();
        note.setCreator(user);
        note.setNote(notes);
        post.getNotes().add(note);

        return post;
    }
}

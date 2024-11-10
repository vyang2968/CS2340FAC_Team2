package com.example.sprintproject.model;

import java.io.Serializable;

public class Note implements Serializable {
    private String note;
    private User creator;

    public Note() {
        this.note = "";
        this.creator = new User();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

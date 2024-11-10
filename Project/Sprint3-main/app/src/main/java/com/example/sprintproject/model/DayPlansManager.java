package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayPlansManager implements Serializable {
    private Map<String, String> dayPlansDetails;
    private Map<String, List<Note>> dayPlansNotes;

    public DayPlansManager() {
        dayPlansDetails = new HashMap<>();
        dayPlansNotes = new HashMap<>();
    }

    public Map<String, String> getDayPlansDetails() {
        return dayPlansDetails;
    }

    public void setDayPlansDetails(Map<String, String> dayPlansDetails) {
        this.dayPlansDetails = dayPlansDetails;
    }

    public Map<String, List<Note>> getDayPlansNotes() {
        return dayPlansNotes;
    }

    public void setDayPlansNotes(Map<String, List<Note>> dayPlansNotes) {
        this.dayPlansNotes = dayPlansNotes;
    }
}

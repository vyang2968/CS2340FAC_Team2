package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DayPlansManager implements Serializable {
    private Map<String, String> dayPlans;

    public DayPlansManager() {
        dayPlans = new HashMap<>();
    }

    public Map<String, String> getDayPlans() {
        return dayPlans;
    }

    public void setDayPlans(Map<String, String> dayPlans) {
        this.dayPlans = dayPlans;
    }
}

package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String id;
    private String email;
    private String username;
    private Date startDate;
    private Date endDate;
    private int duration;
    private String activeTripId;
    private final List<String> tripIds;

    public User() {
        this.id = "";
        this.email = "";
        this.username = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.duration = 0;
        this.activeTripId = "";
        this.tripIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        setUsername(email.substring(0, email.indexOf("@")));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getActiveTripId() {
        return activeTripId;
    }

    public void setActiveTripId(String activeTripId) {
        this.activeTripId = activeTripId;
    }

    public List<String> getTripIds() {
        return tripIds;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

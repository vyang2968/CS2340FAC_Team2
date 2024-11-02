package com.example.sprintproject.model;

import java.util.Date;

public class User {
    private String id;
    private String email;
    private String username;
    private Date startDate;
    private Date endDate;
    private int duration;

    public User() {
        this.id = "";
        this.email = "";
        this.username = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.duration = 0;
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

    public Date getEndDate() {
        return endDate;
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
}

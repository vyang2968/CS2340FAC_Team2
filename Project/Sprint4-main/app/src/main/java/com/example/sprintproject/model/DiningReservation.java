package com.example.sprintproject.model;

import com.example.sprintproject.utils.Plannable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiningReservation implements Serializable, Plannable {
    private String id;
    private String location;
    private String websiteLink;
    private List<Review> reviews;
    private Date reservationTime;  // Store as Date

    public DiningReservation() {
        this.id = "";
        this.location = "";
        this.websiteLink = "";
        this.reviews = new ArrayList<>();
        this.reservationTime = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    @Override
    public Date getPlannedDate() {
        return reservationTime;
    }
}
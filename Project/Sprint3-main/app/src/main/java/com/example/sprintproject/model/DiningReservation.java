package com.example.sprintproject.model;

import android.location.Address;
import android.util.Patterns;

import com.example.sprintproject.utils.Plannable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiningReservation implements Serializable, Plannable {
    private String id;
    private Address location;
    private String websiteLink;
    private List<Review> reviews;
    private Date reservationTime;

    public DiningReservation() {
        this.id = "";
        this.location = new Address(Locale.getDefault());
        this.websiteLink = "";
        this.reviews = new ArrayList<>();
        this.reservationTime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        if (Patterns.WEB_URL.matcher(websiteLink).matches()) {
            throw new IllegalArgumentException("websiteLink is not a valid URL");
        }

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
        return getReservationTime();
    }
}

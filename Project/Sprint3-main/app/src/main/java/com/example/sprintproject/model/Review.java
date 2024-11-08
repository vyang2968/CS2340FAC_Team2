package com.example.sprintproject.model;

public class Review {
    private String id;
    private int rating;
    private String review;
    private User creator;

    public Review() {
        this.id = "";
        this.rating = 0;
        this.review = "";
        this.creator = new User();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

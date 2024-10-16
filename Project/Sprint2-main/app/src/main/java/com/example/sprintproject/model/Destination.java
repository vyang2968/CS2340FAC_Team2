package com.example.sprintproject.model;

import java.util.Date;

public class Destination {
    private String id;
    private String destination;
    private Date startDate;
    private Date endDate;
    private CollaborationManager collaborationManager;

    public Destination() {
        this.id = "";
        this.destination = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.collaborationManager = new CollaborationManager();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public CollaborationManager getCollaborationManager() {
        return collaborationManager;
    }
}

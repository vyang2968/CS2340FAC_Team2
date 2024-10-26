package com.example.sprintproject.model;

import java.util.Date;

public class Destination {
    private String id;
    private String destination;
    private Date startDate;
    private Date endDate;
    private CollaboratorManager collaboratorManager;

    public Destination() {
        this.id = "";
        this.destination = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.collaboratorManager = new CollaboratorManager();
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

    public CollaboratorManager getCollaboratorManager() {
        return collaboratorManager;
    }
}

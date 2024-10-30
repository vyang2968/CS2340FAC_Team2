package com.example.sprintproject.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Destination {
    private String destination;
    private Date startDate;
    private Date endDate;
    private final CollaboratorManager collaboratorManager;

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

    public int getDurationInDays() {
        long milliseconds = endDate.getTime() - startDate.getTime();

        return (int) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
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

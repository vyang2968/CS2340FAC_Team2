package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Destination implements Serializable {
    private String id;
    private String destinationName;
    private Date startDate;
    private Date endDate;
    private final CollaboratorManager collaboratorManager;
    private final DayPlansManager dayPlansManager;

    public Destination() {
        this.id = "";
        this.destinationName = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.collaboratorManager = new CollaboratorManager();
        this.dayPlansManager = new DayPlansManager();
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

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public CollaboratorManager getCollaboratorManager() {
        return collaboratorManager;
    }

    public DayPlansManager getDayPlansManager() {
        return dayPlansManager;
    }
}

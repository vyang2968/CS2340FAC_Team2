package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trip implements Serializable {
    private String id;
    private CollaboratorManager manager;
    private List<String> diningReservationsIds;
    private List<String> accommodationsIds;
    private List<String> destinationsIds;

    public Trip() {
        this.id = "";
        this.manager = new CollaboratorManager();
        this.diningReservationsIds = new ArrayList<>();
        this.accommodationsIds = new ArrayList<>();
        this.destinationsIds = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CollaboratorManager getManager() {
        return manager;
    }

    public void setManager(CollaboratorManager manager) {
        this.manager = manager;
    }

    public List<String> getDiningReservationsIds() {
        return diningReservationsIds;
    }

    public void setDiningReservationsIds(List<String> diningReservationsIds) {
        this.diningReservationsIds = diningReservationsIds;
    }

    public List<String> getAccommodationsIds() {
        return accommodationsIds;
    }

    public void setAccommodationsIds(List<String> accommodationsIds) {
        this.accommodationsIds = accommodationsIds;
    }

    public List<String> getDestinationsIds() {
        return destinationsIds;
    }

    public void setDestinationsIds(List<String> destinationsIds) {
        this.destinationsIds = destinationsIds;
    }
}

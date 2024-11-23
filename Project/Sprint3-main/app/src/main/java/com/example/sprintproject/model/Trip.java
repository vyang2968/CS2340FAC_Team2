package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip implements Serializable {
    private String id;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

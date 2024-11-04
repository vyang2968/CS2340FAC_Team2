package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorManager {
    private int daysPlanned;
    private User creator;
    private final List<User> collaborators;

    public CollaboratorManager() {
        this.collaborators = new ArrayList<>();
        this.creator = new User();
    }

    public int getDaysPlanned() {
        return daysPlanned;
    }

    public void setDaysPlanned(int daysPlanned) {
        this.daysPlanned = daysPlanned;
    }

    public void addCollaborator(User user) {
        collaborators.add(user);
    }

    public void removeCollaborator(User user) {
        collaborators.remove(user);
    }

    public boolean hasCollaborator(User user) {
        return collaborators.contains(user);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

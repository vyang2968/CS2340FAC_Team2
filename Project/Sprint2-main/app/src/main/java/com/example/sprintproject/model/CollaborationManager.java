package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class CollaborationManager {
    private List<User> collaborators;

    public CollaborationManager(List<User> collaborators) {
        this.collaborators = collaborators;
    }

    public CollaborationManager() {
        this.collaborators = new ArrayList<>();
    }

    public void addCollaborator(User user) {
        collaborators.add(user);
    }

    public void removeCollaborator(User user) {
        collaborators.remove(user);
    }
}

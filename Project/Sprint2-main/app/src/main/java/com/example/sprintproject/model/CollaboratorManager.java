package com.example.sprintproject.model;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorManager {
    private User creator;
    private List<User> collaborators;

    public CollaboratorManager() {
        this.collaborators = new ArrayList<>();
        this.creator = new User();
    }

    public void addCollaborator(User user) {
        collaborators.add(user);
    }

    public void removeCollaborator(User user) {
        collaborators.remove(user);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

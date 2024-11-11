package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorManager implements Serializable {
    private User creator;
    private List<User> collaborators;

    public CollaboratorManager() {
        this.collaborators = new ArrayList<>();
        this.creator = new User();
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}

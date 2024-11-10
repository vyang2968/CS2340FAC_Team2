package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaboratorManager implements Serializable {
    private User creator;
    private final List<User> collaborators;
    private final List<Note> notes;

    public CollaboratorManager() {
        this.collaborators = new ArrayList<>();
        this.creator = new User();
        this.notes = new ArrayList<>();
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

    public List<Note> getNotes() {
        return notes;
    }
}

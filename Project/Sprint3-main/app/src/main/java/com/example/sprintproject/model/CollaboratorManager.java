package com.example.sprintproject.model;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollaboratorManager implements Serializable {
    private String creator;
    private List<String> collaborators;

    public CollaboratorManager() {
        this.collaborators = new ArrayList<>();
        this.creator = "";
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String id) {
        this.creator = id;
    }
}

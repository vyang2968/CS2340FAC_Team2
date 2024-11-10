package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Destination;

import com.example.sprintproject.model.User;

import org.junit.Before;
import org. junit.Test;

public class AdditionalDestinationUnitTests {
    private Destination destination;
    private CollaboratorManager collaborationManager;

    @Before
    public void start() {
        destination = new Destination();

        collaborationManager = destination.getCollaboratorManager();
    }
    @Test
    public void testLeader(){
        User Leader = new User();
        User Follower1 = new User();
        User Follower2 = new User();

        collaborationManager.setCreator(Leader);
        collaborationManager.addCollaborator(Follower1);
        collaborationManager.addCollaborator(Follower2);

        assertEquals(Leader, collaborationManager.getCreator());
    }
    @Test
    public void testDestination(){
        destination.setDestinationName("Paris");

        assertEquals("Paris", destination.getDestinationName());
    }
}

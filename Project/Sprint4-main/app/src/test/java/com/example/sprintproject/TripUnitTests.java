package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Trip;
import com.example.sprintproject.model.User;

public class TripUnitTests {
    private Trip trip;
    private CollaboratorManager collabManager;

    @Before
    public void start() {
        trip = new Trip();

        collabManager = trip.getManager();
    }

    @Test
    public void testTripName() {
        trip.setName("TRIP TO AMERICA");
        assertEquals("TRIP TO AMERICA", trip.getName());
    }

    @Test
    public void testCollaboration() {
        User userA = new User();
        User userB = new User();
        User userC = new User();

        userA.setId("1");
        userB.setId("2");
        userC.setId("3");

        collabManager.getCollaborators().add(userA.getId());
        collabManager.getCollaborators().add(userC.getId());

        assertTrue(collabManager.getCollaborators().contains(userA.getId()));
        assertFalse(collabManager.getCollaborators().contains(userB.getId()));
        assertTrue(collabManager.getCollaborators().contains(userC.getId()));

        collabManager.getCollaborators().remove(userA.getId());

        assertFalse(collabManager.getCollaborators().contains(userA.getId()));
        assertFalse(collabManager.getCollaborators().contains(userB.getId()));
        assertTrue(collabManager.getCollaborators().contains(userC.getId()));
    }

    @Test
    public void testLeader() {
        User Leader = new User();
        User Follower1 = new User();
        User Follower2 = new User();

        collabManager.setCreator(Leader.getId());
        collabManager.getCollaborators().add(Follower1.getId());
        collabManager.getCollaborators().add(Follower2.getId());

        assertEquals(Leader.getId(), collabManager.getCreator());
    }
}

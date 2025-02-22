package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;

import java.util.Date;
import java.util.GregorianCalendar;

public class DestinationUnitTests {

    private Destination destination;
    private CollaboratorManager collabManager;

    @Before
    public void start() {
        destination = new Destination();

        collabManager = destination.getCollaboratorManager();
    }

    @Test
    public void testCollaboration() {
        User userA = new User();
        User userB = new User();
        User userC = new User();

        userA.setId("1");
        userB.setId("2");
        userC.setId("3");

        collabManager.addCollaborator(userA);
        collabManager.addCollaborator(userC);

        assertTrue(collabManager.hasCollaborator(userA));
        assertFalse(collabManager.hasCollaborator(userB));
        assertTrue(collabManager.hasCollaborator(userC));

        collabManager.removeCollaborator(userA);

        assertFalse(collabManager.hasCollaborator(userA));
        assertFalse(collabManager.hasCollaborator(userB));
        assertTrue(collabManager.hasCollaborator(userC));
    }

    @Test
    public void testDates() {
        destination.setStartDate(new GregorianCalendar(2024, 10, 9).getTime());
        destination.setEndDate(new GregorianCalendar(2024, 10, 23).getTime());

        assertEquals(14, destination.getDurationInDays());
    }
}

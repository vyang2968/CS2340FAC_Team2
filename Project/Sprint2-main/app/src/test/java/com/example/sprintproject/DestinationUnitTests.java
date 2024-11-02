package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.CollaboratorManager;
import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.User;

import org.junit.Before;
import org.junit.Test;

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

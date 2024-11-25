package com.example.sprintproject;

import com.example.sprintproject.model.Destination;
import com.example.sprintproject.model.Note;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class DestinationUnitTests {
    private Destination destination;
    @Before
    public void start() {
        destination = new Destination();
    }

    @Test
    public void testDates() {
        destination.setStartDate(new GregorianCalendar(2024, 10, 9).getTime());
        destination.setEndDate(new GregorianCalendar(2024, 10, 23).getTime());

        assertEquals(14, destination.getDurationInDays());
    }

    @Test
    public void testDestination(){
        destination.setDestinationName("Paris");

        assertEquals("Paris", destination.getDestinationName());
    }

    @Test
    public void testDayPlansNotes() {
        Note note = new Note();
        note.setNote("I HATE IT");
        Map<String, List<Note>> dayPlans = destination.getDayPlansManager().getDayPlansNotes();
        dayPlans.put("Day 1", List.of(note));

        assertTrue(dayPlans.get("Day 1").contains(note));
    }
}

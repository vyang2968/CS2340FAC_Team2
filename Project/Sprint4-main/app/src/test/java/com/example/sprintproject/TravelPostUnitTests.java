package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TravelPostUnitTests {

    private TravelPost travelPost;

    @Before
    public void setUp() {
        travelPost = new TravelPost();
    }

    @Test
    public void testId() {
        String id = "12345";
        travelPost.setId(id);
        assertEquals(id, travelPost.getId());
    }

    @Test
    public void testNotes() {
        List<Note> notes = new ArrayList<>();
        Note note1 = new Note();
        Note note2 = new Note();
        notes.add(note1);
        notes.add(note2);

        travelPost.setNotes(notes);
        assertEquals(notes, travelPost.getNotes());
    }
}
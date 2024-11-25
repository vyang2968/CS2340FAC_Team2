package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TravelPostUnitTests2 {

    private TravelPost travelPost;

    @Before
    public void setUp() {
        travelPost = new TravelPost();
    }

    @Test
    public void testStartDate() {
        String date = "07/11/2005";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        travelPost.setStartDate(sdf.parse(date));
        assertEquals(date, travelPost.getId());
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
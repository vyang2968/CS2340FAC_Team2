package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
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
    public void testStartDate() throws ParseException {
        String date = "07/11/2005";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        travelPost.setStartDate(sdf.parse(date));
        assertEquals(sdf.parse("07/11/2005"), travelPost.getStartDate());
    }

    @Test
    public void testNotes() {
        Note note1 = new Note();

        travelPost.setNotes(note1);
        assertEquals(note1, travelPost.getNotes());
    }
}
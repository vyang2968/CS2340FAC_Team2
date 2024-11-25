package com.example.sprintproject;

import static org.junit.Assert.*;

import android.util.Log;

import com.example.sprintproject.model.Note;
import com.example.sprintproject.model.TravelPost;
import com.example.sprintproject.model.User;
import com.example.sprintproject.service.UserService;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TravelPostUnitTests2 {

    private TravelPost travelPost;

    @Before
    public void setUp() {
        travelPost = new TravelPost();
    }

    @Test
    public void testPostUsername() {
        Note note = new Note();
        User user = new User();
        user.setUsername("chunglis");
        note.setCreator(user);
        travelPost.setNotes(note);
        assertEquals("chunglis", travelPost.getNotes().getCreator().getUsername());
    }

    @Test
    public void testDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        String date1 = "07/11/2005";
        String date2 = "06/11/2005";
        try {
            Date startDate = sdf.parse(date1);
            Date endDate = sdf.parse(date2);
            assertEquals(travelPost.checkDates(startDate, endDate), false);
        } catch (ParseException error) {
            throw new IllegalArgumentException(error);
        }
    }
}
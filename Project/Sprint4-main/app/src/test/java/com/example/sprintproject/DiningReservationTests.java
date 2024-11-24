package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.location.Address;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.model.User;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

public class DiningReservationTests {
    private User user;
    @Before
    public void init() {
        user = new User();
        user.setId("1");
        user.setEmail("legoat@gmail.com");
    }

    @Test
    public void testDefaultAddress() {
        DiningReservation reservation = new DiningReservation();
        String result = reservation.getLocation();
        assertEquals("", result);
    }

    @Test(expected = ParseException.class)
    public void testInvalidDateString() throws ParseException {
        String dumbDate = "1";
        DiningReservation reservation = new DiningReservation();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        Date date = format.parse(dumbDate);
        reservation.setReservationTime(date);
    }

}

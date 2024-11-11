package com.example.sprintproject;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.Accommodation.RoomType;

import org.junit.Before;
import org.junit.Test;

public class AccommodationUnitTests {

    private Accommodation accommodation;

    @Before
    public void setUp() {
        accommodation = new Accommodation();
    }

    @Test
    public void testLocation() {
        String location = "Paris";
        accommodation.setLocation(location);
        assertEquals(location, accommodation.getLocation());
    }

    @Test
    public void testRoomType() {
        Accommodation.RoomType roomType = RoomType.SUITE;
        accommodation.setRoomType(roomType);
        assertEquals(roomType, accommodation.getRoomType());
    }
}
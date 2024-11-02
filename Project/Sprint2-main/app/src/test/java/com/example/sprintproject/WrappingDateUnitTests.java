package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.model.Destination;

import org.junit.Test;

import java.util.GregorianCalendar;

public class WrappingDateUnitTests {
    @Test
    public void testWrapMonth() {
        GregorianCalendar calendar1 = new GregorianCalendar(2000, 1, 1);
        GregorianCalendar calendar2 = new GregorianCalendar(2000, 2, 5);
        Destination dest = new Destination();
        dest.setStartDate(calendar1.getTime());
        dest.setEndDate(calendar2.getTime());
        long diff = calendar2.getTime().getTime() - calendar1.getTime().getTime();

        assertEquals(dest.getDurationInDays(), 33);
    }
    @Test
    public void testWrapYear() {
        GregorianCalendar calendar1 = new GregorianCalendar(2000, 12, 25);
        GregorianCalendar calendar2 = new GregorianCalendar(2001, 1, 5);
        Destination dest = new Destination();
        dest.setStartDate(calendar1.getTime());
        dest.setEndDate(calendar2.getTime());
        long diff = calendar2.getTime().getTime() - calendar1.getTime().getTime();

        assertEquals(dest.getDurationInDays(), 11);
    }
}

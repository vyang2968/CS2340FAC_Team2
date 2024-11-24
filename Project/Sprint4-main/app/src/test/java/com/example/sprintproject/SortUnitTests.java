package com.example.sprintproject;

import com.example.sprintproject.model.Accommodation;
import com.example.sprintproject.model.DiningReservation;
import com.example.sprintproject.utils.CheckOutSortMethod;
import com.example.sprintproject.utils.PlannableSortMethod;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class SortUnitTests {
    private ArrayList<Accommodation> accommodations;
    private ArrayList<DiningReservation> reservations;

    @Before
    public void start() {
        Accommodation a1 = new Accommodation();
        a1.setCheckInTime(new GregorianCalendar(2024, 10, 10).getTime());
        a1.setCheckOutTime(new GregorianCalendar(2024, 10, 15).getTime());

        Accommodation a2 = new Accommodation();
        a2.setCheckInTime(new GregorianCalendar(2024, 10, 12).getTime());
        a2.setCheckOutTime(new GregorianCalendar(2024, 10, 14).getTime());

        Accommodation a3 = new Accommodation();
        a3.setCheckInTime(new GregorianCalendar(2024, 6, 16).getTime());
        a3.setCheckOutTime(new GregorianCalendar(2024, 6, 23).getTime());

        Accommodation a4 = new Accommodation();
        a4.setCheckInTime(new GregorianCalendar(2024, 3, 4).getTime());
        a4.setCheckOutTime(new GregorianCalendar(2024, 3, 8).getTime());


        accommodations = new ArrayList<>();
        accommodations.add(a1);
        accommodations.add(a2);
        accommodations.add(a3);
        accommodations.add(a4);

        for (Accommodation a : accommodations) {
            a.setLocation("Place");
            a.setNumRooms(1);
            a.setRoomType(Accommodation.RoomType.fromString("Single"));
        }


        DiningReservation r1 = new DiningReservation();
        r1.setReservationTime(new GregorianCalendar(2022, 3, 15).getTime());

        DiningReservation r2 = new DiningReservation();
        r2.setReservationTime(new GregorianCalendar(2022, 1, 10).getTime());

        DiningReservation r3 = new DiningReservation();
        r3.setReservationTime(new GregorianCalendar(2022, 2, 17).getTime());

        DiningReservation r4 = new DiningReservation();
        r1.setReservationTime(new GregorianCalendar(2022, 2, 5).getTime());

        reservations = new ArrayList<>();
        reservations.add(r3);
        reservations.add(r4);
        reservations.add(r2);
        reservations.add(r1);
    }

    @Test
    public void testPlannableSort() {
        List<Accommodation> expectedA = new ArrayList<>();

        expectedA.add(accommodations.get(3));
        expectedA.add(accommodations.get(2));
        expectedA.add(accommodations.get(0));
        expectedA.add(accommodations.get(1));

        List<DiningReservation> expectedR = new ArrayList<>();

        expectedR.add(reservations.get(1));
        expectedR.add(reservations.get(3));
        expectedR.add(reservations.get(2));
        expectedR.add(reservations.get(0));

        PlannableSortMethod<Accommodation> sortA = new PlannableSortMethod<>();
        PlannableSortMethod<DiningReservation> sortR = new PlannableSortMethod<>();

        sortA.sortList(accommodations);
        sortR.sortList(reservations);

        for (int i = 0; i < accommodations.size(); i++) {
            assertEquals(expectedA.get(i), accommodations.get(i));
        }

        for (int i = 0; i < reservations.size(); i++) {
            assertEquals(expectedR.get(i), reservations.get(i));
        }
    }

    @Test
    public void testCheckOutSort() {
        List<Accommodation> expectedA = new ArrayList<>();

        expectedA.add(accommodations.get(3));
        expectedA.add(accommodations.get(2));
        expectedA.add(accommodations.get(0));
        expectedA.add(accommodations.get(1));

        CheckOutSortMethod sortA = new CheckOutSortMethod();

        sortA.sortList(accommodations);

        for (int i = 0; i < accommodations.size(); i++) {
            assertEquals(expectedA.get(i), accommodations.get(i));
        }
    }
}

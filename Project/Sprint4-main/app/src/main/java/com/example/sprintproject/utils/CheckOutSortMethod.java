package com.example.sprintproject.utils;

import com.example.sprintproject.model.Accommodation;

public class CheckOutSortMethod implements SortMethod<Accommodation> {
    @Override
    public int compare(Accommodation o1, Accommodation o2) {

        return o1.getCheckOutTime().compareTo(o2.getCheckOutTime());
    }
}

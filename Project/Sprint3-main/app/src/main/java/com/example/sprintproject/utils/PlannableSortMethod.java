package com.example.sprintproject.utils;

import com.example.sprintproject.model.Accommodation;

public class PlannableSortMethod<T extends Plannable> implements SortMethod<T> {
    @Override
    public int compare(T o1, T o2) {

        return o1.getPlannedDate().compareTo(o2.getPlannedDate());
    }
}

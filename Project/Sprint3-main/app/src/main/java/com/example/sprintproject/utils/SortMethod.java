package com.example.sprintproject.utils;

import java.util.Comparator;
import java.util.List;

public interface SortMethod<T> extends Comparator<T> {
    default void sortList(List<T> list) {
        list.sort(this);
    }
}

package com.example.sprintproject.utils;

public interface DataCallback<T> {
    void onSuccess(T result);
    void onError(Exception e);
}

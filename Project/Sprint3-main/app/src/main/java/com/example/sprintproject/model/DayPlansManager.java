package com.example.sprintproject.model;

import java.io.Serializable;

public class DayPlan implements Serializable {
    private String id;
    private int day;
    private String detail;

    public DayPlan() {
        day = 0;
        detail = "";
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

package com.example.sprintproject.model;

import java.util.Date;

public class LodgingReservation {
    public enum RoomType {
        SINGLE,
        DOUBLE,
        QUEEN,
        KING,
        SUITE
    }

    private Date checkInTime;
    private Date checkOutTime;
    private int numRooms;
    private RoomType roomType;

    public LodgingReservation() {
        this.checkInTime = new Date();
        this.checkOutTime = new Date();
        this.numRooms = 0;
        this.roomType = RoomType.SINGLE;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}

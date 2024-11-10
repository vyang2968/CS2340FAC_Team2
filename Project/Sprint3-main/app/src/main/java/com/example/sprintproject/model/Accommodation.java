package com.example.sprintproject.model;

import java.io.Serializable;
import java.util.Date;

public class Accommodation implements Serializable {

    public enum RoomType {
        SINGLE,
        DOUBLE,
        QUEEN,
        KING,
        SUITE;

        public static RoomType fromString(String type) {
            // Since this is an enum, this should be fine
            switch (type) {
            case "Single":
                return SINGLE;
            case "Double":
                return DOUBLE;
            case "Suite":
                return SUITE;
            case "Queen Suite":
                return QUEEN;
            case "King Suite":
                return KING;
            default:
                throw new IllegalArgumentException("Unknown room type " + type);
            }
        }
    }

    private String id;
    private String location;
    private Date checkInTime;
    private Date checkOutTime;
    private int numRooms;
    private RoomType roomType;

    public Accommodation() {
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

    public void setRoomType(String roomType) {
        setRoomType(RoomType.fromString(roomType));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

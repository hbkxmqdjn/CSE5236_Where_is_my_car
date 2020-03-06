package com.bignerdranch.android.findmycar.model;

import android.location.Location;

import java.util.Date;
import java.util.UUID;

public class Parking {
    private UUID mId;
    private String mNote;
    private Date mDate;
    private Location mLocation;

    public Parking() {
        this(UUID.randomUUID());
    }

    public Parking(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }
    public String getNote() {
        return mNote;
    }
    public void setNote(String note) {
        mNote = note;
    }
    public Date getDate() {
        return mDate;
    }
    public void setDate (Date date) {
        mDate = date;
    }
    public void setLocation(Location location) {
        mLocation.setLatitude(location.getLatitude());
        mLocation.setLongitude(location.getLongitude());
    }
    public Location getLocation() {
        return mLocation;
    }
}

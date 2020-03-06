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
        mId = UUID.randomUUID();
        mDate = new Date();
        mLocation = new Location("");
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
    public void setLocation(double lon, double lat) {
        mLocation.setLatitude(lat);
        mLocation.setLongitude(lon);
    }
    public Location getLocation() {
        return mLocation;
    }
}

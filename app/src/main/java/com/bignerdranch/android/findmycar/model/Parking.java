package com.bignerdranch.android.findmycar.model;

import android.location.Location;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Queue;
import java.util.UUID;

public class Parking {
    private UUID mId;
    private String mNote;
    private Date mDate;
    private double mLongitude;
    private double mLatitude;

    public Parking() {
        this(UUID.randomUUID());
    }

    public Parking(UUID id) {
        mId = id;
        mDate = new Date();
        mLongitude = 20.3;
        mLatitude = 52.6;
        mNote = "Parked here!";
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
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
    public void setLongitude(Double lon) {
        mLongitude = lon;
    }
    public void setLatitude(Double lat) {
        mLatitude = lat;
    }
    public double getLongitude() {
        return mLongitude;
    }
    public double getLatitude() {
        return mLatitude;
    }
}

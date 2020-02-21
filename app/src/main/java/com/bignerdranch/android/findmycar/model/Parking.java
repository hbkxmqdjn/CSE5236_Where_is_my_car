package com.bignerdranch.android.findmycar.model;

import java.util.Date;
import java.util.UUID;

public class Parking {
    private UUID mId;
    private String mNote;
    private Date mDate;
    private boolean mSolved;

    public Parking() {
        mId = UUID.randomUUID();
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
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved (boolean solved) {
        mSolved = solved;
    }
}

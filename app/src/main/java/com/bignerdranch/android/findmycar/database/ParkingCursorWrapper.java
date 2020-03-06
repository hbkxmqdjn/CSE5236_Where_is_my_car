package com.bignerdranch.android.findmycar.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.location.Location;

import com.bignerdranch.android.findmycar.model.Parking;

import java.util.Date;
import java.util.UUID;

public class ParkingCursorWrapper extends CursorWrapper {
    public ParkingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Parking getParking() {
        String uuidString = getString(getColumnIndex(ParkingDbSchema.Cols.UUID));
        String note = getString(getColumnIndex(ParkingDbSchema.Cols.NOTE));
        long date = getLong(getColumnIndex(ParkingDbSchema.Cols.DATE));
        String location = getString(getColumnIndex(ParkingDbSchema.Cols.LOCATION));

        Parking parking = new Parking(UUID.fromString(uuidString));
        parking.setNote(note);
        parking.setDate(new Date(date));
        parking.setLocation(new Location(location));

        return parking;
    }
}

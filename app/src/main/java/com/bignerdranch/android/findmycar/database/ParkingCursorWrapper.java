package com.bignerdranch.android.findmycar.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.location.Location;

import com.bignerdranch.android.findmycar.model.Parking;
import com.bignerdranch.android.findmycar.database.ParkingDbSchema.*;

import java.util.Date;
import java.util.UUID;

public class ParkingCursorWrapper extends CursorWrapper {
    public ParkingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Parking getParking() {
        String uuidString = getString(getColumnIndex(ParkingTable.Cols.UUID));
        String note = getString(getColumnIndex(ParkingTable.Cols.NOTE));
        long date = getLong(getColumnIndex(ParkingTable.Cols.DATE));
        double latitude = getDouble(getColumnIndex(ParkingTable.Cols.LATITUDE));
        double longitude = getDouble(getColumnIndex(ParkingTable.Cols.LONGITUDE));

        Parking parking = new Parking(UUID.fromString(uuidString));
        parking.setNote(note);
        parking.setDate(new Date(date));
        parking.setLatitude(latitude);
        parking.setLongitude(longitude);

        return parking;
    }
}

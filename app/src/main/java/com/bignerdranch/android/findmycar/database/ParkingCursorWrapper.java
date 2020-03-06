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
        String location = getString(getColumnIndex(ParkingTable.Cols.LOCATION));

        Parking parking = new Parking(UUID.fromString(uuidString));
        parking.setNote(note);
        parking.setDate(new Date(date));
        parking.setLocation(new Location(location));

        return parking;
    }
}

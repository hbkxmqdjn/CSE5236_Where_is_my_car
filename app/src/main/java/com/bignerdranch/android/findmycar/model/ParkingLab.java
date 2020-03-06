package com.bignerdranch.android.findmycar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.findmycar.database.ParkingBaseHelper;
import com.bignerdranch.android.findmycar.database.ParkingCursorWrapper;
import com.bignerdranch.android.findmycar.database.ParkingDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Calendar;

public class ParkingLab {
    private static ParkingLab sParkingLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ParkingLab get(Context context) {
        if (sParkingLab == null) {
            sParkingLab = new ParkingLab(context);
        }
        return sParkingLab;
    }

    private ParkingLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ParkingBaseHelper(mContext)
                .getWritableDatabase();
    }

    public List<Parking> getParkings() {
        List<Parking> parkings = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Parking parking = new Parking();
//            parking.setNote("Parking #" + i);
//            parking.setDate(Calendar.getInstance().getTime());
//            parkings.add(parking);
//        }

        ParkingCursorWrapper cursor = queryParkings(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                parkings.add(cursor.getParking());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return parkings;
    }
    
    public Parking getParking(UUID id) {
        ParkingCursorWrapper cursor = queryParkings(
                ParkingDbSchema.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getParking();
        } finally {
            cursor.close();
        }
    }

    public void addParking(Parking p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(ParkingDbSchema.ParkingTable.NAME, null, values);
    }

    public void deleteParking(Parking p) {
        ContentValues values = getContentValues(p);
        mDatabase.delete(ParkingDbSchema.ParkingTable.NAME, "_id=?", new String[] { String.valueOf(p.getId())});
    }

    public void updateParking(Parking parking) {
        String uuidString = parking.getId().toString();
        ContentValues values = getContentValues(parking);

        mDatabase.update(ParkingDbSchema.ParkingTable.NAME, values,
                ParkingDbSchema.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private ParkingCursorWrapper queryParkings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ParkingDbSchema.ParkingTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ParkingCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Parking parking) {
        ContentValues values = new ContentValues();
        values.put(ParkingDbSchema.Cols.UUID, parking.getId().toString());
        values.put(ParkingDbSchema.Cols.NOTE, parking.getNote());
        values.put(ParkingDbSchema.Cols.DATE, parking.getDate().getTime());
        values.put(ParkingDbSchema.Cols.LOCATION, parking.getLocation().toString());

        return values;
    }
}

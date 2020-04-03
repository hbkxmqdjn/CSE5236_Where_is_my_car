package com.bignerdranch.android.findmycar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.findmycar.database.ParkingBaseHelper;
import com.bignerdranch.android.findmycar.database.ParkingCursorWrapper;
import com.bignerdranch.android.findmycar.database.ParkingDbSchema;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Calendar;
import com.bignerdranch.android.findmycar.database.ParkingDbSchema.*;

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
                ParkingTable.Cols.UUID + " = ?",
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
        mDatabase.insert(ParkingTable.NAME, null, values);

    }

//    public void deleteParking(Parking p) {
//        ContentValues values = getContentValues(p);
//        mDatabase.delete(ParkingDbSchema.ParkingTable.NAME, "_id=?", new String[] { String.valueOf(p.getId())});
//    }


    public void deleteParking(Parking c) {
        mDatabase.delete(ParkingTable.NAME, ParkingTable.Cols.UUID + " = ?",
                new String[]{c.getId().toString()});
    }

    public void updateParking(Parking parking) {
        String uuidString = parking.getId().toString();
        ContentValues values = getContentValues(parking);

        mDatabase.update(ParkingTable.NAME, values,
                ParkingTable.Cols.UUID + " = ?",
                new String[] { uuidString });

    }

    private ParkingCursorWrapper queryParkings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ParkingTable.NAME,
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
        values.put(ParkingTable.Cols.UUID, parking.getId().toString());
        values.put(ParkingTable.Cols.NOTE, parking.getNote());
        values.put(ParkingTable.Cols.DATE, parking.getDate().getTime());
        values.put(ParkingTable.Cols.LONGITUDE, parking.getLongitude());
        values.put(ParkingTable.Cols.LATITUDE, parking.getLatitude());
        return values;
    }

    public File getPhotoFile(Parking parking) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, parking.getPhotoFilename());
    }
}

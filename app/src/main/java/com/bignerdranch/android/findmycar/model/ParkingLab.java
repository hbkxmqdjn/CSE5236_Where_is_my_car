package com.bignerdranch.android.findmycar.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.findmycar.database.ParkingBaseHelper;
import com.bignerdranch.android.findmycar.database.ParkingDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
//        for (int i = 0; i < 100; i++) {
//            Parking parking = new Parking();
//            parking.setNote("Parking #" + i);
//            mParkings.add(parking);
//        }
    }

    public List<Parking> getParkings() {
        return new ArrayList<>();
    }
    
    public Parking getParking(UUID id) {
        return null;
    }

    public void addParking(Parking p) {
        ContentValues values = getContentValues(p);
        mDatabase.insert(ParkingDbSchema.ParkingTable.NAME, null, values);
    }

    public void updateParking(Parking parking) {
        String uuidString = parking.getId().toString();
        ContentValues values = getContentValues(parking);

        mDatabase.update(ParkingDbSchema.ParkingTable.NAME, values,
                ParkingDbSchema.Cols.UUID + " = ?",
                new String[] { uuidString });
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

package com.bignerdranch.android.findmycar.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.findmycar.database.ParkingBaseHelper;

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
    }

    public List<Parking> getParkings() {
        return new ArrayList<>();
    }
    
    public Parking getParking(UUID id) {
        return null;
    }
}

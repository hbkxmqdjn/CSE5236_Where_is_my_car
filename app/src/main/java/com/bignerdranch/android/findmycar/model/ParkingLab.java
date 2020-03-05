package com.bignerdranch.android.findmycar.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkingLab {
    private static ParkingLab sParkingLab;
    private List<Parking> mParkings;


    public static ParkingLab get(Context context) {
        if (sParkingLab == null) {
            sParkingLab = new ParkingLab(context);
        }
        return sParkingLab;
    }
    private ParkingLab(Context context) {
        mParkings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Parking parking = new Parking();
            mParkings.add(parking);
        }
    }

    public List<Parking> getParkings() {
        return mParkings;
    }
    
    public Parking getParking(UUID id) {
        for (Parking parking : mParkings) {
            if (parking.getId().equals(id)) {
                return parking;
            }
        }
        return null;
    }
}

package com.bignerdranch.android.findmycar.ui;

import androidx.fragment.app.Fragment;

public class ParkingActivity extends SingleFragmentActivity {

    private static final String TAG = "ParkingActivity";

    @Override
    protected Fragment createFragment() {
        return new ParkingFragment();
    }

}

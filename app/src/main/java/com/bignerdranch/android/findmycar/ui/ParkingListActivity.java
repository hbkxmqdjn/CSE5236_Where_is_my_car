package com.bignerdranch.android.findmycar.ui;

import androidx.fragment.app.Fragment;


public class ParkingListActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ParkingListFragment();
    }
}

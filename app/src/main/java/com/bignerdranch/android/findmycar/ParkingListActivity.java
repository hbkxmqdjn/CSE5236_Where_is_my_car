package com.bignerdranch.android.findmycar;

import androidx.fragment.app.Fragment;

public class ParkingListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ParkingListFragment();
    }
}

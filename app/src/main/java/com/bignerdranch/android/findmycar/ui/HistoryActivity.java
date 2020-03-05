package com.bignerdranch.android.findmycar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.bignerdranch.android.findmycar.R;

public class HistoryActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        return new ParkingListFragment();
    }
}

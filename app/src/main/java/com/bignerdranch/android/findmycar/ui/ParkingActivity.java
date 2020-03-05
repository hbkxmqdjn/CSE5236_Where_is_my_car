package com.bignerdranch.android.findmycar.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.bignerdranch.android.findmycar.R;
import com.bignerdranch.android.findmycar.SingleFragmentActivity;

public class ParkingActivity extends SingleFragmentActivity {

    private static final String TAG = "ParkingActivity";

    @Override

    protected Fragment createFragment() {
        return new RecordingFragment();
    }


}

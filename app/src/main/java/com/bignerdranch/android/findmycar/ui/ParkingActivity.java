package com.bignerdranch.android.findmycar.ui;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.findmycar.model.Parking;

import java.util.UUID;

public class ParkingActivity extends SingleFragmentActivity {

    private static final String EXTRA_PARKING_ID =
            "com.bignerdranch.android.criminalintent.parking_id";


    @Override
    protected Fragment createFragment() {
        UUID parkingId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_PARKING_ID);
        return ParkingFragment.newInstance(parkingId);
    }



    public static Intent newIntent(Context packageContext, UUID parkingId) {
        Intent intent = new Intent(packageContext, ParkingActivity.class);
        intent.putExtra(EXTRA_PARKING_ID, parkingId);
        return intent;
    }
}


//package com.bignerdranch.android.findmycar.ui;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//
//import com.bignerdranch.android.findmycar.R;
//import com.bignerdranch.android.findmycar.ui.ParkingFragment;
//
//public class ParkingActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment);
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
//        if (fragment == null) {
//            fragment = new ParkingFragment();
//            fm.beginTransaction()
//                    .add(R.id.fragment_container, fragment)
//                    .commit();
//        }
//    }
//
//
//}
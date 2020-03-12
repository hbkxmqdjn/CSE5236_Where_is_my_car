package com.bignerdranch.android.findmycar.ui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bignerdranch.android.findmycar.R;
import com.bignerdranch.android.findmycar.model.Parking;
import com.bignerdranch.android.findmycar.model.ParkingLab;


import java.util.UUID;

public class ParkingFragment extends Fragment {

    private static final String TAG = "RecordingFragment";
    private static final String ARG_PARKING_ID = "parking_id";

    private Parking mParking;
    private EditText mTitleField;
    private Button mDateButton;
    private TextView mDateText;


    public static ParkingFragment newInstance(UUID parkingId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARKING_ID, parkingId);
        ParkingFragment fragment = new ParkingFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID parkingId = (UUID) getArguments().getSerializable(ARG_PARKING_ID);
        mParking = ParkingLab.get(getActivity()).getParking(parkingId);

//        super.onCreate(savedInstanceState);
//        UUID parkingId = (UUID) getArguments().getSerializable(ARG_PARKING_ID);
//        mParking = ParkingLab.get(getActivity()).getParking(parkingId);
    }

    @Override
    public void onPause() {
        super.onPause();

        ParkingLab.get(getActivity())
                .updateParking(mParking);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        View v = inflater.inflate(R.layout.fragment_parking, container, false);
        mDateText = (TextView)v.findViewById(R.id.parking_date);
        mDateText.setText(mParking.getDate().toString());
        mTitleField = (EditText)v.findViewById(R.id.parking_note);
        mTitleField.setText(mParking.getNote());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mParking.setNote(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });

        mDateButton = (Button) v.findViewById(R.id.parking_picture);
        mDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//add parking to the database
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_parking, menu);
        MenuItem deleteItem = menu.findItem(R.id.delete_button);
        MenuItem updateItem = menu.findItem(R.id.update_button);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_button:
                ParkingLab.get(getActivity()).deleteParking(mParking);
                getActivity().finish();
                return true;
            case R.id.update_button:
                ParkingLab.get(getActivity()).updateParking(mParking);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

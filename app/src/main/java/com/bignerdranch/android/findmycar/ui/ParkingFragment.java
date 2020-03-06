package com.bignerdranch.android.findmycar.ui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_PARKING_ID);
        mParking = ParkingLab.get(getActivity()).getParking(crimeId);
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
        View v = inflater.inflate(R.layout.fragment_recording, container, false);

        mTitleField = (EditText)v.findViewById(R.id.parking_note);
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
        mDateButton.setEnabled(false);

        return v;
    }
}

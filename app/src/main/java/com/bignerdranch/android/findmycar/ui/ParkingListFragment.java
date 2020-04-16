package com.bignerdranch.android.findmycar.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.findmycar.R;
import com.bignerdranch.android.findmycar.model.Parking;
import com.bignerdranch.android.findmycar.model.ParkingLab;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ParkingListFragment extends Fragment {

    private RecyclerView mParkingRecyclerView;
    private ParkingAdapter mAdapter;
    public static String USERNAME;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences settings = getActivity().getSharedPreferences(LoginFragment.OPT_NAME, Context.MODE_PRIVATE);
        USERNAME = settings.getString("stored_username", "defaultValue");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_list, container, false);
        mParkingRecyclerView = (RecyclerView) view
                .findViewById(R.id.parking_recycler_view);
        mParkingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    // UI is not completely working yet
    private void updateUI() {
        ParkingLab parkingLab = ParkingLab.get(getActivity());
        List<Parking> parkings = parkingLab.getParkings(USERNAME);
        if (mAdapter == null) {
            mAdapter = new ParkingAdapter(parkings);
            mParkingRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setParkings(parkings);
            mAdapter.notifyDataSetChanged();
        }
//        ParkingLab parkingLab = ParkingLab.get(getActivity());
//        List<Parking> parkings = parkingLab.getParkings();
//        mAdapter = new ParkingAdapter(parkings);
//        mParkingRecyclerView.setAdapter(mAdapter);
    }

    private class ParkingHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mNoteTextView;
        private TextView mDateTextView;
        private Parking mParking;

        public ParkingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_parking, parent, false));
            itemView.setOnClickListener(this);
            mNoteTextView = (TextView) itemView.findViewById(R.id.parking_note);
            mDateTextView = (TextView) itemView.findViewById(R.id.parking_date);
        }

        public void bind(Parking parking) {
            mParking = parking;
            mNoteTextView.setText(mParking.getNote());
            mDateTextView.setText(mParking.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ParkingActivity.newIntent(getActivity(), mParking.getId());
            startActivity(intent);
        }
    }

    private class ParkingAdapter extends RecyclerView.Adapter<ParkingHolder> {
        private List<Parking> mParkings;
        public ParkingAdapter(List<Parking> parkings) {
            mParkings = parkings;
        }

        @Override
        public ParkingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ParkingHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(ParkingHolder holder, int position) {
            Parking parking = mParkings.get(position);
            holder.bind(parking);
        }
        @Override
        public int getItemCount() {
            return mParkings.size();
        }

        public void setParkings(List<Parking> parkings) {
            mParkings = parkings;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_parking_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_parking:
                Toast.makeText(getActivity().getApplicationContext(), USERNAME, Toast.LENGTH_SHORT).show();
                Parking parking = new Parking();
                ParkingLab.get(getActivity()).addParking(parking);
                Intent intent = ParkingActivity
                        .newIntent(getActivity(), parking.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

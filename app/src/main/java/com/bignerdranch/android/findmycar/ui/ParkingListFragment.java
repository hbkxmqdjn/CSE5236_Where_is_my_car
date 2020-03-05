package com.bignerdranch.android.findmycar.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
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

import java.util.List;

public class ParkingListFragment extends Fragment {



    private RecyclerView mParkingRecyclerView;
    private ParkingAdapter mAdapter;



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

    private void updateUI() {
        ParkingLab parkingLab = ParkingLab.get(getActivity());
        List<Parking> parkings = parkingLab.getParkings();
        mAdapter = new ParkingAdapter(parkings);
        mParkingRecyclerView.setAdapter(mAdapter);
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
            Toast.makeText(getActivity(),
                    mParking.getNote() + " clicked!", Toast.LENGTH_SHORT)
                    .show();
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
    }
    
    
}

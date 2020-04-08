package com.bignerdranch.android.findmycar.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bignerdranch.android.findmycar.R;
import com.bignerdranch.android.findmycar.model.Parking;
import com.bignerdranch.android.findmycar.model.ParkingLab;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


import java.io.File;
import java.util.List;
import java.util.UUID;

public class ParkingFragment extends Fragment {

    private static final String TAG = "RecordingFragment";
    private static final String ARG_PARKING_ID = "parking_id";

    private Parking mParking;
    private EditText mTitleField;
    private TextView mDateText;
    private TextView mLocationText;
    private Button mDirectionButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private static final int REQUEST_PHOTO= 2;

    int PERMISSION_ID = 44;


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
        mParking.setLatitude(ParkingActivity.latitude);
        mParking.setLongitude(ParkingActivity.longitude);
        mPhotoFile = ParkingLab.get(getActivity()).getPhotoFile(mParking);

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
        mLocationText = (TextView)v.findViewById(R.id.location_text);
        mLocationText.setText("Latitude: " + mParking.getLatitude() + ", Longitude: " + mParking.getLongitude());
        mTitleField = (EditText)v.findViewById(R.id.parking_note);
        mTitleField.setText(mParking.getNote());

        mDirectionButton = (Button) v.findViewById(R.id.direction_button);
        mDirectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkAvailable()){
//                    Toast.makeText(getActivity(), R.string.cannot_direct_toast, Toast.LENGTH_SHORT)
//                            .show();

                    new AlertDialog.Builder(getActivity())
                            .setMessage(R.string.network_not_enabled)
                            .setPositiveButton(R.string.open_network_settings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    getActivity().startActivity(new Intent(Settings.ACTION_DATA_USAGE_SETTINGS));
                                }
                            })
                            .setNegativeButton(R.string.Cancel,null)
                            .show();
                }else {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mParking.getLatitude() + "," + mParking.getLongitude());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

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

        PackageManager packageManager = getActivity().getPackageManager();

        mPhotoButton = (ImageButton) v.findViewById(R.id.parking_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);


        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.bignerdranch.android.findmycar.fileprovider",
                        mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity()
                        .getPackageManager().queryIntentActivities(captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });
        mPhotoView = (ImageView) v.findViewById(R.id.parking_photo);

        //added for detail photo dialog
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                ImageFragment dialog = ImageFragment.newInstance(mPhotoFile.getPath());
                dialog.show(manager, "IMAGE_VIEWER");
            }
        });
        updatePhotoView();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.bignerdranch.android.criminalintent.fileprovider",
                    mPhotoFile);
            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
        }
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

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {

            return false;
        }
    }
}

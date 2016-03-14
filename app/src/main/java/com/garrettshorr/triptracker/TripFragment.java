package com.garrettshorr.triptracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by g on 3/4/2016.
 */
public class TripFragment extends Fragment {
    private Button mSaveButton;
    private EditText mTripName;
    private EditText mTripDescription;
    private EditText mTripStartDate;
    private EditText mTripEndDate;
    private CheckBox mPublicCheckbox;
    private String mObjectID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_trip, container, false);
        mSaveButton = (Button) rootView.findViewById(R.id.trip_save_button);
        mTripName = (EditText) rootView.findViewById(R.id.edit_trip_name);
        mTripDescription = (EditText) rootView.findViewById(R.id.edit_trip_description);
        mTripStartDate = (EditText) rootView.findViewById(R.id.edit_trip_start_date);
        mTripEndDate = (EditText) rootView.findViewById(R.id.edit_trip_end_date);
        mPublicCheckbox = (CheckBox) rootView.findViewById(R.id.trip_is_public_checkbox);

        final Calendar startDate = Calendar.getInstance();
        final Calendar endDate = Calendar.getInstance();

        //load the fields with data from the intent, or default values
        Intent i = getActivity().getIntent();
        mTripName.setText(i.getStringExtra(Trip.EXTRA_TRIP_NAME));
        mTripDescription.setText(i.getStringExtra(Trip.EXTRA_TRIP_DESCRIPTION));
        mTripStartDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(
                new Date(i.getLongExtra(Trip.EXTRA_START_DATE, System.currentTimeMillis()))));
        mTripEndDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(
                new Date(i.getLongExtra(Trip.EXTRA_END_DATE, System.currentTimeMillis()))));
        mPublicCheckbox.setChecked(i.getBooleanExtra(Trip.EXTRA_IS_PUBLIC, false));
        mObjectID = i.getStringExtra(Trip.EXTRA_OBJECT_ID);

        mTripStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), createDateSetListener(startDate, mTripStartDate),
                        startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mTripEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), createDateSetListener(endDate, mTripEndDate),
                        startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the current user id
                BackendlessUser currentUser = Backendless.UserService.CurrentUser();
                String userID = currentUser.getUserId();
                //
                Trip t = new Trip(mTripName.getText().toString(),
                                  mTripDescription.getText().toString(),
                                  new Date(startDate.getTimeInMillis()),
                                  mPublicCheckbox.isChecked(),
                                  new Date(endDate.getTimeInMillis()),
                                  userID);
                if(mObjectID != null)
                    t.setObjectId(mObjectID);
                LoadingCallback<Trip> saveTrip = new LoadingCallback<Trip>(getActivity(), "Saving") {
                    @Override
                    public void handleResponse(Trip response) {
                        super.handleResponse(response);
                        getActivity().finish();
                    }
                };
                saveTrip.showLoading();
                Backendless.Persistence.of(Trip.class).save(t, saveTrip);
            }
        });
        return rootView;
    }
    public DatePickerDialog.OnDateSetListener createDateSetListener(final Calendar c, final EditText e) {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                e.setText(df.format(new Date(c.getTimeInMillis())));
            }
        };
    }
}

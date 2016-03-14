package com.garrettshorr.triptracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.backendless.Backendless;

/**
 * Created by g on 3/4/2016.
 */
public class TripActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trip);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("TripFragment")== null)
            fm.beginTransaction()
                    .add(R.id.trip_fragment_container, new TripFragment(), "TripListFragment")
                    .commit();

    }
}

package com.garrettshorr.triptracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.backendless.Backendless;

/**
 * Created by g on 2/24/2016.
 */
public class TripListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_triplist);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        FragmentManager fm = getSupportFragmentManager();
        if(fm.findFragmentByTag("TripListFragment")== null)
            fm.beginTransaction()
                    .add(R.id.trip_list_container, new TripListFragment(), "TripListFragment")
                    .commit();

    }
}

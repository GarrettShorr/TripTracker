package com.garrettshorr.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

        FloatingActionButton addTrip = (FloatingActionButton) findViewById(R.id.add_new_trip_fab);
        addTrip.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TripListActivity.this, TripActivity.class);
                startActivity(i);
            }
        });
    }
}

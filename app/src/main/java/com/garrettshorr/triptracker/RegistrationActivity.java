package com.garrettshorr.triptracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by gshorr on 2/22/16.
 */
public class RegistrationActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration);

        //provide for up-navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}

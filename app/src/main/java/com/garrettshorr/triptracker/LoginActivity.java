package com.garrettshorr.triptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private TextView mRegLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText) findViewById(R.id.enter_login);
        mPassword = (EditText) findViewById(R.id.enter_password);
        mRegLink = (TextView) findViewById(R.id.registration_link);

        mRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch Registration activity
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
    }
}

package com.garrettshorr.triptracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassword;
    private TextView mRegLink;
    private Button mLoginButton;

    private static final int REGISTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Backendless.initApp( this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION );

        mUserName = (EditText) findViewById(R.id.enter_login);
        mPassword = (EditText) findViewById(R.id.enter_password);
        mRegLink = (TextView) findViewById(R.id.registration_link);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mRegLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //launch Registration activity
                Intent registrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivityForResult(registrationIntent, REGISTER_REQUEST_CODE);
            }
        });

        mLoginButton.setOnClickListener(createNewLoginOnClickListener());

    }

    public View.OnClickListener createNewLoginOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence username = mUserName.getText();
                CharSequence password = mPassword.getText();

                if(isLoginValuesValid(username, password)) {
                    LoadingCallback<BackendlessUser> loginCallback = createLoginCallback();
                    loginCallback.showLoading();;
                    loginUser( username.toString(), password.toString(), loginCallback);
                }
            }
        };
    }

    private LoadingCallback<BackendlessUser> createLoginCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_login)) {
            @Override
            public void handleResponse(BackendlessUser loggedInUser) {
                super.handleResponse(loggedInUser);
                Toast.makeText(LoginActivity.this, String.format(getString(R.string.info_logged_in),
                        loggedInUser.getProperty("username")), Toast.LENGTH_LONG).show();
                Intent startTripList = new Intent(LoginActivity.this, TripListActivity.class);
                startActivity(startTripList);
            }
        };
    }

    private boolean isLoginValuesValid(CharSequence username, CharSequence password) {
        return Validator.isUsernameValid(this, username) && Validator.isPasswordValid(this, password);
    }

    private void loginUser(String username, String password, LoadingCallback<BackendlessUser> loginCallback) {
        Backendless.UserService.login(username, password, loginCallback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch(requestCode) {
                case REGISTER_REQUEST_CODE:
                    //fill in the username field then set the focus to the password field
                    String username = data.getStringExtra(BackendlessUser.ID_KEY);
                    mUserName.setText(username);
                    mPassword.requestFocus();
                    Toast.makeText(this, this.getString(R.string.info_registered_success),
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}

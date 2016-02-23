package com.garrettshorr.triptracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;

/**
 * Created by gshorr on 2/22/16.
 */
public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //provide for up-navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //needed for all Backendless Services
        Backendless.initApp(this, BackendSettings.APPLICATION_ID,
                BackendSettings.ANDROID_SECRET_KEY, BackendSettings.VERSION);

        //Add a listener to the submit button
        Button registerButton = (Button) findViewById(R.id.submit_registration);
        registerButton.setOnClickListener(createRegisterButtonClickListener());



    }

    public boolean isRegistrationValid(CharSequence firstName, CharSequence lastName,
                                       CharSequence username, CharSequence email,
                                       CharSequence password, CharSequence confirmPassword) {
        return Validator.isNameValid(this, firstName) &&
                Validator.isNameValid(this, lastName) &&
                Validator.isUsernameValid(this, username) &&
                Validator.isEmailValid(this, email) &&
                Validator.isPasswordValid(this, password) &&
                isPasswordMatch(password, confirmPassword);
    }

    public boolean isPasswordMatch(CharSequence password, CharSequence confirmPassword) {
        if(!TextUtils.equals(password, confirmPassword)) {
            Toast.makeText(this, this.getString(R.string.warning_passwords_do_not_match),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void registerUser(String firstName, String lastName, String username, String email,
                             String password, AsyncCallback<BackendlessUser> registrationCallback) {
        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setProperty("firstName", firstName);
        user.setProperty("lastName", lastName);
        user.setProperty("username", username);

        //the password doesn't have to be hashed because Backendless does it automagically
        Backendless.UserService.register( user, registrationCallback );

    }

    //listener for the register button
    public View.OnClickListener createRegisterButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wire up the edittexts
                EditText firstNameField = (EditText) findViewById(R.id.enter_first_name);
                EditText lastNameField = (EditText) findViewById(R.id.enter_last_name);
                EditText usernameField = (EditText) findViewById(R.id.enter_username);
                EditText emailField = (EditText) findViewById(R.id.enter_email_address);
                EditText passwordField = (EditText) findViewById(R.id.enter_password);
                EditText confirmPasswordField = (EditText) findViewById(R.id.enter_password_confirm);

                //get the text values as CharSequences
                CharSequence firstName = firstNameField.getText();
                CharSequence lastName = lastNameField.getText();
                CharSequence username = usernameField.getText();
                CharSequence email = emailField.getText();
                CharSequence password = passwordField.getText();
                CharSequence confirmPassword = confirmPasswordField.getText();

                //if the values are valid
                if(isRegistrationValid(firstName, lastName, username, email, password,
                        confirmPassword)) {
                    //make a registrationCallback
                    LoadingCallback<BackendlessUser> registrationCallback = createRegistrationCallback();

                    //show the loadscreen of that callback
                    registrationCallback.showLoading();

                    //register the user
                    registerUser(firstName.toString(), lastName.toString(), username.toString(),
                            email.toString(), password.toString(), registrationCallback);

                }


            }
        };
    }

    public LoadingCallback<BackendlessUser> createRegistrationCallback() {
        return new LoadingCallback<BackendlessUser>(this, getString(R.string.loading_register)) {
            @Override
            public void handleResponse(BackendlessUser registeredUser) {
                super.handleResponse(registeredUser);
                Intent registrationResult = new Intent();
                registrationResult.putExtra(BackendlessUser.ID_KEY,
                        registeredUser.getProperty("username").toString());
                setResult(RESULT_OK, registrationResult);
                RegistrationActivity.this.finish();
            }
        };
    }





}

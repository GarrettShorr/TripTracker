package com.garrettshorr.triptracker;

import android.content.Context;
import android.util.Patterns;
import android.widget.Toast;

/**
 * Created by g on 2/22/2016.
 */
public class Validator {
    public static boolean isNameValid(Context currentContext, CharSequence name) {
        if(name.toString().isEmpty()) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_name_empty),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Character.isUpperCase(name.toString().charAt(0))) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_name_lowercase),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(Context currentContext, CharSequence email) {
        if(email.toString().isEmpty()) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_email_empty),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_email_invalid),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(Context currentContext, CharSequence password) {
        if(password.toString().isEmpty()) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_password_empty),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean isUsernameValid(Context currentContext, CharSequence username) {
        if(username.toString().isEmpty()) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_username_empty),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if(username.length() < 3) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_username_too_short),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if(!isAlphaNumeric(username.toString())) {
            Toast.makeText(currentContext, currentContext.getString(R.string.warning_username_invalid),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public static boolean isAlphaNumeric(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isLetterOrDigit(str.charAt(i)))
                return false;
        }
        return true;
    }
}

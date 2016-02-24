package com.garrettshorr.triptracker;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by g on 2/22/2016.
 */
public class LoadingCallback<T> implements AsyncCallback<T> {
    private Context context;
    private ProgressDialog progressDialog;

    //callback with no params gives generic Loading... message
    public LoadingCallback(Context context) {
        this(context, context.getString(R.string.loading_empty));
    }

    public LoadingCallback(Context context, String loadingMessage) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(loadingMessage);
    }

    @Override
    public void handleResponse(T response) {
        progressDialog.dismiss();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        progressDialog.dismiss();
        DialogHelper.createErrorDialog(context, "BackendlessFault", fault.getMessage()).show();
    }

    //show and hide the progress dialog
    public void showLoading() {
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.dismiss();
    }
}

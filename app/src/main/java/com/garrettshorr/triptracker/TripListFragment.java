package com.garrettshorr.triptracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import android.text.format.DateFormat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by g on 2/24/2016.
 */
    public class TripListFragment extends ListFragment {
    private List<Trip> mTrips;
    private ListView listView;
    private TripAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        //final TextView t = (TextView) rootView.findViewById(R.id.test_textview);
        listView = (ListView) rootView.findViewById(android.R.id.list);

        /*    ----------------Testing out the database---------------- */
//        final BackendlessUser user = Backendless.UserService.CurrentUser();
//        t.setText(user.getObjectId() + "\n");
//
//        AsyncCallback<BackendlessCollection<Trip>> getTrips =
//                new AsyncCallback<BackendlessCollection<Trip>>() {
//            @Override
//            public void handleResponse(BackendlessCollection<Trip> response) {
//                Iterator<Trip> trips = response.getCurrentPage().iterator();
//                while(trips.hasNext()) {
//                    Trip trip = trips.next();
//                    //if(trip.getOwner().equals(user.getObjectId()))
//                        t.append(trip.toString() + " ");
//                }
//            }
//            @   Override
//            public void handleFault(BackendlessFault fault) {
//
//            }
//        };
//        BackendlessDataQuery query = new BackendlessDataQuery();
//        query.setWhereClause("owner='"+user.getObjectId()+"'");
//        Backendless.Data.of(Trip.class).find(query, getTrips);
        /*   ----------------- End backend test ------------------- */

        mTrips = new ArrayList<>();
        populateTrips();
        adapter = new TripAdapter(mTrips);
        setListAdapter(adapter);

        return rootView;
    }

    private void populateTrips() {
        LoadingCallback<BackendlessCollection<Trip>> tripCallback = createTripRetrievalCallback();
        final BackendlessUser user = Backendless.UserService.CurrentUser();
        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause("owner='"+user.getObjectId()+"'");
        Backendless.Data.of(Trip.class).find(query, tripCallback);
    }

    private class TripAdapter extends ArrayAdapter<Trip> {

        public TripAdapter(List<Trip> trips) {
            super(getActivity(), 0, trips);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if not given a view, inflate one
            if(convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.triplist_item, null);
            }
            Trip t = getItem(position);

            TextView tripName = (TextView) convertView.findViewById(R.id.list_item_trip_name);
            tripName.setText(t.getTripName());
            TextView startDate = (TextView) convertView.findViewById(R.id.list_item_trip_start_date);
            DateFormat df = new DateFormat();
            startDate.setText(df.format("yyyy-MM-dd", t.getStartDate()));
            return  convertView;
        }
    } 

    private LoadingCallback<BackendlessCollection<Trip>> createTripRetrievalCallback() {
        return new LoadingCallback<BackendlessCollection<Trip>>(getActivity(),
                getString(R.string.loading_trips)) {

            @Override
            public void handleResponse(BackendlessCollection<Trip> trips) {
                super.handleResponse(trips);
                //mTrips = Arrays.asList(trips.getData().toArray(new Trip[trips.getTotalObjects()]));
                mTrips = trips.getData();
                adapter.clear();
                adapter.addAll(mTrips);
                adapter.notifyDataSetChanged();
                Log.e("LOOKATME", mTrips.toString());
            }
        };
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Make an intent
        Intent i = new Intent(getActivity(), TripActivity.class);
        //Set the extras
        Trip t = mTrips.get(position);
        i.putExtra(Trip.EXTRA_TRIP_NAME, t.getTripName());
        i.putExtra(Trip.EXTRA_TRIP_DESCRIPTION, t.getTripDescription());
        i.putExtra(Trip.EXTRA_START_DATE, t.getStartDate().getTime());
        i.putExtra(Trip.EXTRA_END_DATE, t.getEndDate().getTime());
        i.putExtra(Trip.EXTRA_IS_PUBLIC, t.getIsPublic());
        i.putExtra(Trip.EXTRA_OWNER_ID, t.getOwner());
        i.putExtra(Trip.EXTRA_OBJECT_ID, t.getObjectId());
        //Launch the activity
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateTrips();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.trip_list_options_menu, menu);
    }
}


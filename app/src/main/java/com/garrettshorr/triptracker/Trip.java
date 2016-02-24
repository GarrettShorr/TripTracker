package com.garrettshorr.triptracker;

import java.util.Date;

/**
 * Created by g on 2/23/2016.
 */
public class Trip {

    private String mTripId;
    private String mUserId;
    private String mTrip;
    private String mTripDesc;
    private Date mStartDate;
    private Date mEndDate;
    private boolean mIsPublic;

    public Trip(String mTripId, String mUserId, String mTrip, String mTripDesc, Date mStartDate, Date mEndDate, boolean mIsPublic) {
        this.mTripId = mTripId;
        this.mUserId = mUserId;
        this.mTrip = mTrip;
        this.mTripDesc = mTripDesc;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
        this.mIsPublic = mIsPublic;
    }

    public Trip() {
        mUserId = "";
        mTrip = "Mordor";
        mTripDesc = "Destroying the One Ring";
        mStartDate = new Date();
        mEndDate = new Date();
        mIsPublic = false;
    }
}

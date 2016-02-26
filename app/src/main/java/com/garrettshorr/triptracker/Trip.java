package com.garrettshorr.triptracker;

import java.util.Date;

/**
 * Created by g on 2/23/2016.
 */
public class Trip {

    private int tripID;
    private String tripName;
    private String tripDescription;
    private Date startDate;
    private Date endDate;
    private boolean isPublic;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Trip() {

    }

    public Trip(int tripID, String tripName, String tripDescription, Date startDate, boolean isPublic, Date endDate) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPublic = isPublic;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    //    public Trip() {
//        mUserId = "";
//        mTrip = "Mordor";
//        mTripDesc = "Destroying the One Ring";
//        mStartDate = new Date();
//        mEndDate = new Date();
//        mIsPublic = false;
//    }
    public String toString() {
        return tripName;
    }
}

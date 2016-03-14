package com.garrettshorr.triptracker;

import java.util.Date;

/**
 * Created by g on 2/23/2016.
 */
public class Trip {

    public transient static String EXTRA_TRIP_NAME = "trip_name";
    public transient static String EXTRA_TRIP_DESCRIPTION ="trip_desc";
    public transient static String EXTRA_START_DATE = "trip_start_date";
    public transient static String EXTRA_END_DATE = "trip_end_date";
    public transient static String EXTRA_OWNER_ID = "trip_owner_id";
    public transient static String EXTRA_IS_PUBLIC = "trip_is_public";
    public transient static String EXTRA_OBJECT_ID = "trip_object_id";

    private String objectId;
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

    public Trip(String tripName, String tripDescription, Date startDate, boolean isPublic, Date endDate, String owner) {
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPublic = isPublic;
        this.owner = owner;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String toString() {
        return tripName;
    }
}

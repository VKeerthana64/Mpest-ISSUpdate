package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmObject;

public class SchedulerTrackingdetails extends RealmObject {

    private String _id;

    private String Scheduler_Id;

    private String StartTime;

    private String StartLocation;

    private String CreatedBy;

    private String CreatedDate;

    private boolean IsActive;

    private String UpdatedDate;

    private String UpdatedBy;

    private String EndLocation;

    private String EndTime;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getScheduler_Id() {
        return Scheduler_Id;
    }

    public void setScheduler_Id(String Scheduler_Id) {
        this.Scheduler_Id = Scheduler_Id;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getStartLocation() {
        return StartLocation;
    }

    public void setStartLocation(String StartLocation) {
        this.StartLocation = StartLocation;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        this.CreatedBy = createdBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.UpdatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        this.CreatedDate = createdDate;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        this.IsActive = active;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String UpdatedBy) {
        this.UpdatedBy = UpdatedBy;
    }

    public String getEndLocation() {
        return EndLocation;
    }

    public void setEndLocation(String EndLocation) {
        this.EndLocation = EndLocation;
    }
    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

}
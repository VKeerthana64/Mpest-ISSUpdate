package com.amana.MpestISS.restApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Happy on 25-Mar-19.
 */

public class StartTime {

//    private int Mcid;

    private String Scheduler_Id;

    private String StartLocation;

    private String CreatedBy;

    public StartTime(String Scheduler_Id,String StartLocation, String CreatedBy)
    {
        this.Scheduler_Id = Scheduler_Id;
        this.StartLocation = StartLocation;
        this.CreatedBy = CreatedBy;
    }

    public String getScheduler_Id() {
        return Scheduler_Id;
    }

    public void setScheduler_Id(String Scheduler_Id) {
        this.Scheduler_Id = Scheduler_Id;
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

    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }
}
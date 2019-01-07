
package com.amana.MPESTRidpest.model.checkinout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckingRequest {

    @SerializedName("Employee")
    @Expose
    private String employee;
    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;

    public String getEmployeeID() {
        return employee;
    }

    public void setEmployeeID(String employeeID) {
        this.employee = employeeID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}

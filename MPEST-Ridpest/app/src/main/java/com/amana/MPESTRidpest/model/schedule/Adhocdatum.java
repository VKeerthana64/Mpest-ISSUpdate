
package com.amana.MPESTRidpest.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Adhocdatum {

    @SerializedName("Postal")
    @Expose
    private String postal;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("LatLong")
    @Expose
    private String latLong;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Address")
    @Expose
    private String address;

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}


package com.amana.mpest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdhocRequest {

    @SerializedName("_id")
    @Expose
    private String _id="";

    @SerializedName("ServiceId")
    @Expose
    private String ServiceId="";
    @SerializedName("ADHOCServiceId")
    @Expose
    private String ADHOCServiceId;

    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("LatLong")
    @Expose
    private String latLong;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Startsat")
    @Expose
    private String startsat;
    @SerializedName("Endsat")
    @Expose
    private String endsat;
    @SerializedName("ServiceOn")
    @Expose
    private String serviceOn;

    @SerializedName("Team")
    @Expose
    private String Team;

    @SerializedName("TeamLead")
    @Expose
    private String TeamLead;

    @SerializedName("AssignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("SalesPerson_id")
    @Expose
    private String salesPersonId;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("Adhocdata")
    @Expose
    private ArrayList<AdhocModel> adhocdata = null;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartsat() {
        return startsat;
    }

    public void setStartsat(String startsat) {
        this.startsat = startsat;
    }

    public String getEndsat() {
        return endsat;
    }

    public void setEndsat(String endsat) {
        this.endsat = endsat;
    }

    public String getServiceOn() {
        return serviceOn;
    }

    public void setServiceOn(String serviceOn) {
        this.serviceOn = serviceOn;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ArrayList<AdhocModel> getAdhocdata() {
        return adhocdata;
    }

    public void setAdhocdata(ArrayList<AdhocModel> adhocdata) {
        this.adhocdata = adhocdata;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getTeamLead() {
        return TeamLead;
    }

    public void setTeamLead(String teamLead) {
        TeamLead = teamLead;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getADHOCServiceId() {
        return ADHOCServiceId;
    }

    public void setADHOCServiceId(String ADHOCServiceId) {
        this.ADHOCServiceId = ADHOCServiceId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

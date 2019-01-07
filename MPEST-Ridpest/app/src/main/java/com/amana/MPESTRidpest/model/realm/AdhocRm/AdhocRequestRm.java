
package com.amana.MPESTRidpest.model.realm.AdhocRm;

import com.amana.MPESTRidpest.model.AdhocModel;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

public class AdhocRequestRm extends RealmObject {

    private String _id="";

    public String getADHOCServiceId() {
        return ADHOCServiceId;
    }

    private void setADHOCServiceId(String ADHOCServiceId) {
        this.ADHOCServiceId = ADHOCServiceId;
    }

    private String ServiceId="";

    private String ADHOCServiceId;
  
    private String CompanyName;

    private String ContactPerson;

    private String LatLong;

    private String Location;

    private String Startsat;

    
    private String Endsat;

    
    private String ServiceOn;

    
    private String Team;

    
    private String TeamLead;

    
    private String AssignedTo;

    
    private String SalesPerson_id;

    
    private String PestType;


    private RealmList<AdhocModelRm> Adhocdata = null;

    
    private String CreatedBy;
  
    private String Client_Id;

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getStartsat() {
        return Startsat;
    }

    public void setStartsat(String startsat) {
        Startsat = startsat;
    }

    public String getEndsat() {
        return Endsat;
    }

    public void setEndsat(String endsat) {
        Endsat = endsat;
    }

    public String getServiceOn() {
        return ServiceOn;
    }

    public void setServiceOn(String serviceOn) {
        ServiceOn = serviceOn;
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

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getSalesPerson_id() {
        return SalesPerson_id;
    }

    public void setSalesPerson_id(String salesPerson_id) {
        SalesPerson_id = salesPerson_id;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public RealmList<AdhocModelRm> getAdhocdata() {
        return Adhocdata;
    }

    public void setAdhocdata(RealmList<AdhocModelRm> adhocdata) {
        Adhocdata = adhocdata;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

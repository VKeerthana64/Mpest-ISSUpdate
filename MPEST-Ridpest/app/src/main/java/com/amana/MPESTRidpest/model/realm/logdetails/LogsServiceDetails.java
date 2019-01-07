
package com.amana.MPESTRidpest.model.realm.logdetails;

import io.realm.RealmObject;

public class LogsServiceDetails extends RealmObject{

    private String ServiceId;
    private String WorkOrderNo;
    private String ContractNo;
    private String CustomerName;
    private String ScheduledDate;
    private String ActualStartTime;
    private String ActualEndTime;
    private String Team;
    private String TeamMember;
    private String PestType;
    private String JobPerformedBy;
    private String Location;
    private String LatLong;
    private String Types;

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getWorkOrderNo() {
        return WorkOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        WorkOrderNo = workOrderNo;
    }

    public String getContractNo() {
        return ContractNo;
    }

    public void setContractNo(String contractNo) {
        ContractNo = contractNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getScheduledDate() {
        return ScheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        ScheduledDate = scheduledDate;
    }

    public String getActualStartTime() {
        return ActualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        ActualStartTime = actualStartTime;
    }

    public String getActualEndTime() {
        return ActualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        ActualEndTime = actualEndTime;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getTeamMember() {
        return TeamMember;
    }

    public void setTeamMember(String teamMember) {
        TeamMember = teamMember;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getJobPerformedBy() {
        return JobPerformedBy;
    }

    public void setJobPerformedBy(String jobPerformedBy) {
        JobPerformedBy = jobPerformedBy;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }
}

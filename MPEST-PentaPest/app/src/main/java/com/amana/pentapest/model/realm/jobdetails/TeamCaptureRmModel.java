package com.amana.pentapest.model.realm.jobdetails;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TeamCaptureRmModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceId;
    private String TeamLead;
    private String TeamMembers;
    private String Remarks;
    private String TechSign;
    private RealmList<Integer> SelectedPositions;

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getTeamLead() {
        return TeamLead;
    }

    public void setTeamLead(String teamLead) {
        TeamLead = teamLead;
    }

    public String getTeamMembers() {
        return TeamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        TeamMembers = teamMembers;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTechSign() {
        return TechSign;
    }

    public void setTechSign(String techSign) {
        TechSign = techSign;
    }

    public RealmList<Integer> getSelectedPositions() {
        return SelectedPositions;
    }

    public void setSelectedPositions(RealmList<Integer> selectedPositions) {
        SelectedPositions = selectedPositions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

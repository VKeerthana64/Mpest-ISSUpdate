package com.amana.mpest.model.realm.logdetails;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LogServiceMaterialRMModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceID;
    private String PestType;
    private String SM_Type;
    private String SM_remarks;
    private String Others;
    private RealmList<Integer> SelectedPositions;
    private RealmList<LogServicesCapturesRmModel> ServcieCaptures = null;
    private RealmList<LogMaterialsCapturesRmModel> MaterialsCaptures = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getSM_Type() {
        return SM_Type;
    }

    public void setSM_Type(String SM_Type) {
        this.SM_Type = SM_Type;
    }

    public String getSM_remarks() {
        return SM_remarks;
    }

    public void setSM_remarks(String SM_remarks) {
        this.SM_remarks = SM_remarks;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }

    public RealmList<Integer> getSelectedPositions() {
        return SelectedPositions;
    }

    public void setSelectedPositions(RealmList<Integer> selectedPositions) {
        SelectedPositions = selectedPositions;
    }

    public RealmList<LogServicesCapturesRmModel> getServcieCaptures() {
        return ServcieCaptures;
    }

    public void setServcieCaptures(RealmList<LogServicesCapturesRmModel> servcieCaptures) {
        ServcieCaptures = servcieCaptures;
    }

    public RealmList<LogMaterialsCapturesRmModel> getMaterialsCaptures() {
        return MaterialsCaptures;
    }

    public void setMaterialsCaptures(RealmList<LogMaterialsCapturesRmModel> materialsCaptures) {
        MaterialsCaptures = materialsCaptures;
    }
}

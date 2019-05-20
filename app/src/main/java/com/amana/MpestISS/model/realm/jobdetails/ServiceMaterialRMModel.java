package com.amana.MpestISS.model.realm.jobdetails;

import com.amana.MpestISS.model.realm.logdetails.LogMaterialsCapturesRmModel;
import com.amana.MpestISS.model.realm.logdetails.LogServicesCapturesRmModel;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServiceMaterialRMModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceID;
    private String PestType;
    private String SS_Type;
    private String SM_Type;
    private String FM_Type;
    private String RM_Type;
    private String SS_remarks;
    private String SM_remarks;
    private String FM_remarks;
    private String RM_remarks;
    private String Others;
    private RealmList<Integer> ServiceSelectedPositions;
    private RealmList<Integer> SelectedPositions;
    private RealmList<Integer> FindingPositions;
    private RealmList<Integer> RecomPositions;
    private RealmList<ServicesCapturesRmModel> ServcieCaptures = null;
    private RealmList<MaterialsCapturesRmModel> MaterialsCaptures = null;

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

    public String getSS_remarks() {
        return SS_remarks;
    }

    public void setSS_remarks(String SS_remarks) {
        this.SS_remarks = SS_remarks;
    }

    public String getSS_Type() {
        return SS_Type;
    }

    public void setSS_Type(String SS_Type) {
        this.SS_Type = SS_Type;
    }

    public String getFM_Type() {
        return FM_Type;
    }

    public void setFM_Type(String FM_Type) {
        this.FM_Type = FM_Type;
    }

    public String getRM_Type() {
        return RM_Type;
    }

    public void setRM_Type(String RM_Type) {
        this.RM_Type = RM_Type;
    }

    public String getSM_remarks() {
        return SM_remarks;
    }

    public void setSM_remarks(String SM_remarks) {
        this.SM_remarks = SM_remarks;
    }

    public String getFM_remarks() {
        return FM_remarks;
    }

    public void setFM_remarks(String FM_remarks) {
        this.FM_remarks = FM_remarks;
    }

    public String getRM_remarks() {
        return RM_remarks;
    }

    public void setRM_remarks(String RM_remarks) {
        this.RM_remarks = RM_remarks;
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

    public RealmList<Integer> getServiceSelectedPositions() {
        return ServiceSelectedPositions;
    }

    public void setServiceSelectedPositions(RealmList<Integer> serviceSelectedPositions) {
        ServiceSelectedPositions = serviceSelectedPositions;
    }

    public RealmList<Integer> getFindingPositions() {
        return FindingPositions;
    }

    public void setFindingPositions(RealmList<Integer> findingPositions) {
        FindingPositions = findingPositions;
    }

    public RealmList<Integer> getRecomPositions() {
        return RecomPositions;
    }

    public void setRecomPositions(RealmList<Integer> recomPositions) {
        RecomPositions = recomPositions;
    }

    public RealmList<ServicesCapturesRmModel> getServcieCaptures() {
        return ServcieCaptures;
    }

    public void setServcieCaptures(RealmList<ServicesCapturesRmModel> servcieCaptures) {
        ServcieCaptures = servcieCaptures;
    }

    public RealmList<MaterialsCapturesRmModel> getMaterialsCaptures() {
        return MaterialsCaptures;
    }

    public void setMaterialsCaptures(RealmList<MaterialsCapturesRmModel> materialsCaptures) {
        MaterialsCaptures = materialsCaptures;
    }
}

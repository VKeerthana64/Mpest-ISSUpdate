package com.amana.CLEANSolutions.model.realm.jobdetails;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PhotoRemarkRMModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceID;
    private String BeforeAfter;
    private String PestType;
    private String Remarks;
    private String ImgBase64;
    private String Others;
    private RealmList<Integer> SelectedPositions;

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getBeforeAfter() {
        return BeforeAfter;
    }

    public void setBeforeAfter(String beforeAfter) {
        BeforeAfter = beforeAfter;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getImgBase64() {
        return ImgBase64;
    }

    public void setImgBase64(String imgBase64) {
        ImgBase64 = imgBase64;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RealmList<Integer> getSelectedPositions() {
        return SelectedPositions;
    }

    public void setSelectedPositions(RealmList<Integer> selectedPositions) {
        SelectedPositions = selectedPositions;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }
}

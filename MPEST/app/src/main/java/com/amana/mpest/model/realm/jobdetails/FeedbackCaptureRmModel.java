package com.amana.mpest.model.realm.jobdetails;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FeedbackCaptureRmModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceId;
    private String CustomerName;
    private String EmailID;
    private String Rating;
    private String Remarks;
    private String CustomerSign;
    private RealmList<Integer> SelectedPositions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCustomerSign() {
        return CustomerSign;
    }

    public void setCustomerSign(String customerSign) {
        CustomerSign = customerSign;
    }

    public RealmList<Integer> getSelectedPositions() {
        return SelectedPositions;
    }

    public void setSelectedPositions(RealmList<Integer> selectedPositions) {
        SelectedPositions = selectedPositions;
    }
}

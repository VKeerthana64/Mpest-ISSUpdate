package com.amana.MpestISS.model.realm.logdetails;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LogPaymentCaptureRmModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String ServiceId;
    private String PaymentMode;
    private String TotalPayment;
    private String PaymentNote_chequeNo;
    private String PaymentRemarks;
    private String TotalAreaPlanned;
    private String TotalAreaCompleted;
    private String SOR;
    private Boolean IsFollowUp = false;
    private String FollowUpDate;
    private String FollowUpPestType;
    private String FollowUpServiceArea;
    private String FollowUpJobNotes;
    private Boolean IsServiceCall;
    private Boolean IsContinueJob;
    private Boolean IsAdhoc;
    private String Amount;
    private String serviceFollowUpDate;
    private String serviceFollowUpPestType;
    private String serviceFollowUpServiceArea;
    private String serviceFollowUpJobNotes;

    private String ctneFollowUpDate;
    private String ctneFollowUpPestType;
    private String ctneFollowUpServiceArea;
    private String ctneFollowUpJobNotes;

    private String adhocFollowUpDate;
    private String adhocFollowUpPestType;
    private String adhocFollowUpServiceArea;
    private String adhocFollowUpJobNotes;

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

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getTotalPayment() {
        return TotalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        TotalPayment = totalPayment;
    }

    public String getPaymentNote_chequeNo() {
        return PaymentNote_chequeNo;
    }

    public void setPaymentNote_chequeNo(String paymentNote_chequeNo) {
        PaymentNote_chequeNo = paymentNote_chequeNo;
    }

    public String getTotalAreaPlanned() {
        return TotalAreaPlanned;
    }

    public void setTotalAreaPlanned(String totalAreaPlanned) {
        TotalAreaPlanned = totalAreaPlanned;
    }

    public String getTotalAreaCompleted() {
        return TotalAreaCompleted;
    }

    public void setTotalAreaCompleted(String totalAreaCompleted) {
        TotalAreaCompleted = totalAreaCompleted;
    }

    public String getPaymentRemarks() {
        return PaymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        PaymentRemarks = paymentRemarks;
    }

    public String getSOR() {
        return SOR;
    }

    public void setSOR(String SOR) {
        this.SOR = SOR;
    }

    public Boolean getFollowUp() {
        return IsFollowUp;
    }

    public void setFollowUp(Boolean followUp) {
        IsFollowUp = followUp;
    }

    public String getFollowUpDate() {
        return FollowUpDate;
    }

    public void setFollowUpDate(String followUpDate) {
        FollowUpDate = followUpDate;
    }

    public String getFollowUpJobNotes() {
        return FollowUpJobNotes;
    }

    public void setFollowUpJobNotes(String followUpJobNotes) {
        FollowUpJobNotes = followUpJobNotes;
    }

    public String getFollowUpPestType() {
        return FollowUpPestType;
    }

    public void setFollowUpPestType(String followUpPestType) {
        FollowUpPestType = followUpPestType;
    }

    public String getFollowUpServiceArea() {
        return FollowUpServiceArea;
    }

    public void setFollowUpServiceArea(String followUpServiceArea) {
        FollowUpServiceArea = followUpServiceArea;
    }

    public Boolean getAdhoc() {
        return IsAdhoc;
    }

    public void setAdhoc(Boolean adhoc) {
        IsAdhoc = adhoc;
    }

    public void setContinueJob(Boolean continueJob) {
        IsContinueJob = continueJob;
    }

    public Boolean getContinueJob() {
        return IsContinueJob;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAmount() {
        return Amount;
    }


    public void setServiceCall(Boolean serviceCall) {
        IsServiceCall = serviceCall;
    }

    public Boolean getServiceCall() {
        return IsServiceCall;
    }


    public String getAdhocFollowUpDate() {
        return adhocFollowUpDate;
    }

    public void setAdhocFollowUpDate(String adhocFollowUpDate) {
        this.adhocFollowUpDate = adhocFollowUpDate;
    }

    public String getAdhocFollowUpJobNotes() {
        return adhocFollowUpJobNotes;
    }

    public void setAdhocFollowUpJobNotes(String adhocFollowUpJobNotes) {
        this.adhocFollowUpJobNotes = adhocFollowUpJobNotes;
    }

    public String getAdhocFollowUpPestType() {
        return adhocFollowUpPestType;
    }

    public void setAdhocFollowUpPestType(String adhocFollowUpPestType) {
        this.adhocFollowUpPestType = adhocFollowUpPestType;
    }

    public String getAdhocFollowUpServiceArea() {
        return adhocFollowUpServiceArea;
    }

    public void setAdhocFollowUpServiceArea(String adhocFollowUpServiceArea) {
        this.adhocFollowUpServiceArea = adhocFollowUpServiceArea;
    }

    public String getCtneFollowUpDate() {
        return ctneFollowUpDate;
    }

    public void setCtneFollowUpDate(String ctneFollowUpDate) {
        this.ctneFollowUpDate = ctneFollowUpDate;
    }

    public String getCtneFollowUpJobNotes() {
        return ctneFollowUpJobNotes;
    }

    public void setCtneFollowUpJobNotes(String ctneFollowUpJobNotes) {
        this.ctneFollowUpJobNotes = ctneFollowUpJobNotes;
    }

    public String getCtneFollowUpPestType() {
        return ctneFollowUpPestType;
    }

    public void setCtneFollowUpPestType(String ctneFollowUpPestType) {
        this.ctneFollowUpPestType = ctneFollowUpPestType;
    }

    public String getCtneFollowUpServiceArea() {
        return ctneFollowUpServiceArea;
    }

    public void setCtneFollowUpServiceArea(String ctneFollowUpServiceArea) {
        this.ctneFollowUpServiceArea = ctneFollowUpServiceArea;
    }

    public String getServiceFollowUpDate() {
        return serviceFollowUpDate;
    }

    public void setServiceFollowUpDate(String serviceFollowUpDate) {
        this.serviceFollowUpDate = serviceFollowUpDate;
    }

    public String getServiceFollowUpJobNotes() {
        return serviceFollowUpJobNotes;
    }

    public void setServiceFollowUpJobNotes(String serviceFollowUpJobNotes) {
        this.serviceFollowUpJobNotes = serviceFollowUpJobNotes;
    }

    public String getServiceFollowUpPestType() {
        return serviceFollowUpPestType;
    }

    public void setServiceFollowUpPestType(String serviceFollowUpPestType) {
        this.serviceFollowUpPestType = serviceFollowUpPestType;
    }

    public String getServiceFollowUpServiceArea() {
        return serviceFollowUpServiceArea;
    }

    public void setServiceFollowUpServiceArea(String serviceFollowUpServiceArea) {
        this.serviceFollowUpServiceArea = serviceFollowUpServiceArea;
    }
}

package com.amana.mpest.model.realm.logdetails;

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
}

package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmObject;

public class JobOrdersdetail extends RealmObject {

    private String _id;

    private String SalesOrderNo;

    private String OrderConfirmationDate;

    private String ConfirmationReceivedBy;

    private String ConfirmationReceivedDate;

    private String PestType;

    private String CreatedBy;

    private String EnquiryID;

    private String SalesPerson_id;

    private String EnquiryReferenceNo;

    private String ContactPerson;

    private String Customer_id;

    private String ServiceStartDate;

    private String ServiceEndDate;

    private String Frequency;

    private String OrderType;

    private String Types;

    private String ContractDuration;

    private String ContractStartDate;

    private String ContractEndDate;

    private String JobsPerFrequency;

    private String ServicesRequired;

    private int TotalArea=0;

    private String Client_Id;

    private String CreatedDate;

    private Boolean IsActive;

    private Integer __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSalesOrderNo() {
        return SalesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        SalesOrderNo = salesOrderNo;
    }

    public String getOrderConfirmationDate() {
        return OrderConfirmationDate;
    }

    public void setOrderConfirmationDate(String orderConfirmationDate) {
        OrderConfirmationDate = orderConfirmationDate;
    }

    public String getConfirmationReceivedBy() {
        return ConfirmationReceivedBy;
    }

    public void setConfirmationReceivedBy(String confirmationReceivedBy) {
        ConfirmationReceivedBy = confirmationReceivedBy;
    }

    public String getConfirmationReceivedDate() {
        return ConfirmationReceivedDate;
    }

    public void setConfirmationReceivedDate(String confirmationReceivedDate) {
        ConfirmationReceivedDate = confirmationReceivedDate;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getEnquiryID() {
        return EnquiryID;
    }

    public void setEnquiryID(String enquiryID) {
        EnquiryID = enquiryID;
    }

    public String getSalesPerson_id() {
        return SalesPerson_id;
    }

    public void setSalesPerson_id(String salesPerson_id) {
        SalesPerson_id = salesPerson_id;
    }

    public String getEnquiryReferenceNo() {
        return EnquiryReferenceNo;
    }

    public void setEnquiryReferenceNo(String enquiryReferenceNo) {
        EnquiryReferenceNo = enquiryReferenceNo;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    public String getServiceStartDate() {
        return ServiceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        ServiceStartDate = serviceStartDate;
    }

    public String getServiceEndDate() {
        return ServiceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        ServiceEndDate = serviceEndDate;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }

    public String getContractDuration() {
        return ContractDuration;
    }

    public void setContractDuration(String contractDuration) {
        ContractDuration = contractDuration;
    }

    public String getContractStartDate() {
        return ContractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        ContractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return ContractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        ContractEndDate = contractEndDate;
    }

    public String getJobsPerFrequency() {
        return JobsPerFrequency;
    }

    public void setJobsPerFrequency(String jobsPerFrequency) {
        JobsPerFrequency = jobsPerFrequency;
    }

    public String getServicesRequired() {
        return ServicesRequired;
    }

    public void setServicesRequired(String servicesRequired) {
        ServicesRequired = servicesRequired;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public int getTotalArea() {
        return TotalArea;
    }

    public void setTotalArea(int totalArea) {
        TotalArea = totalArea;
    }
}

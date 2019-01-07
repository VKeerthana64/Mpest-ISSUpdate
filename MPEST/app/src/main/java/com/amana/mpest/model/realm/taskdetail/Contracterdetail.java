
package com.amana.mpest.model.realm.taskdetail;

import io.realm.RealmObject;

public class Contracterdetail extends RealmObject{

    private String _id;

    private String ContractReferenceNo;

    private String PestType;

    private String SalesPerson_id;

    private String ContractDuration;

    private String ContactPerson;

    private String ContractStartDate;

    private String ContractEndDate;

    private String ServiceEndDate;

    private String ServiceStartDate;

    private String CreatedBy;

    private String EnquiryID;

    private String Customer_id;

    private String SalesOrderID;

    private String EnquiryReferenceNo;

    private String ContractStatus;

    private String Status;

    private Integer StatusID;

    private String Frequency;

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

    public String getContractReferenceNo() {
        return ContractReferenceNo;
    }

    public void setContractReferenceNo(String contractReferenceNo) {
        ContractReferenceNo = contractReferenceNo;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getSalesPerson_id() {
        return SalesPerson_id;
    }

    public void setSalesPerson_id(String salesPerson_id) {
        SalesPerson_id = salesPerson_id;
    }

    public String getContractDuration() {
        return ContractDuration;
    }

    public void setContractDuration(String contractDuration) {
        ContractDuration = contractDuration;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
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

    public String getServiceEndDate() {
        return ServiceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        ServiceEndDate = serviceEndDate;
    }

    public String getServiceStartDate() {
        return ServiceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        ServiceStartDate = serviceStartDate;
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

    public String getCustomer_id() {
        return Customer_id;
    }

    public void setCustomer_id(String customer_id) {
        Customer_id = customer_id;
    }

    public String getSalesOrderID() {
        return SalesOrderID;
    }

    public void setSalesOrderID(String salesOrderID) {
        SalesOrderID = salesOrderID;
    }

    public String getEnquiryReferenceNo() {
        return EnquiryReferenceNo;
    }

    public void setEnquiryReferenceNo(String enquiryReferenceNo) {
        EnquiryReferenceNo = enquiryReferenceNo;
    }

    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getStatusID() {
        return StatusID;
    }

    public void setStatusID(Integer statusID) {
        StatusID = statusID;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
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
}

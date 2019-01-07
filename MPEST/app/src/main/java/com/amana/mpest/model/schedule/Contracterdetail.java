
package com.amana.mpest.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contracterdetail {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("ContractReferenceNo")
    @Expose
    private String contractReferenceNo;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("SalesPerson_id")
    @Expose
    private String salesPersonId;
    @SerializedName("ContractDuration")
    @Expose
    private String contractDuration;
    @SerializedName("ContractStartDate")
    @Expose
    private String contractStartDate;
    @SerializedName("ContractEndDate")
    @Expose
    private String contractEndDate;
    @SerializedName("ServiceEndDate")
    @Expose
    private String serviceEndDate;
    @SerializedName("ServiceStartDate")
    @Expose
    private String serviceStartDate;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Customer_id")
    @Expose
    private String customerId;
    @SerializedName("SalesOrderID")
    @Expose
    private String salesOrderID;
    @SerializedName("ContractStatus")
    @Expose
    private String contractStatus;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("StatusID")
    @Expose
    private Integer statusID;
    @SerializedName("Frequency")
    @Expose
    private String frequency;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractReferenceNo() {
        return contractReferenceNo;
    }

    public void setContractReferenceNo(String contractReferenceNo) {
        this.contractReferenceNo = contractReferenceNo;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getContractDuration() {
        return contractDuration;
    }

    public void setContractDuration(String contractDuration) {
        this.contractDuration = contractDuration;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public String getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSalesOrderID() {
        return salesOrderID;
    }

    public void setSalesOrderID(String salesOrderID) {
        this.salesOrderID = salesOrderID;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public void setStatusID(Integer statusID) {
        this.statusID = statusID;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

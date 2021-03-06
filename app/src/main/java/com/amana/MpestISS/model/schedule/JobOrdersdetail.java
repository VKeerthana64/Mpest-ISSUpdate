
package com.amana.MpestISS.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobOrdersdetail {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("SalesOrderNo")
    @Expose
    private String salesOrderNo;
    @SerializedName("OrderConfirmationDate")
    @Expose
    private String orderConfirmationDate;
    @SerializedName("ConfirmationMode")
    @Expose
    private Object confirmationMode;
    @SerializedName("ConfirmationBy")
    @Expose
    private String confirmationBy;
    @SerializedName("ConfirmationReceivedBy")
    @Expose
    private Object confirmationReceivedBy;
    @SerializedName("ConfirmationReceivedDate")
    @Expose
    private String confirmationReceivedDate;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("PONumber")
    @Expose
    private Object pONumber;
    @SerializedName("SalesPerson_id")
    @Expose
    private String salesPersonId;
    @SerializedName("EnquiryReferenceNo")
    @Expose
    private String enquiryReferenceNo;
    @SerializedName("Customer_id")
    @Expose
    private String customerId;
    @SerializedName("ServiceStartDate")
    @Expose
    private String serviceStartDate;
    @SerializedName("ServiceEndDate")
    @Expose
    private String serviceEndDate;
    @SerializedName("Frequency")
    @Expose
    private String frequency;
    @SerializedName("OrderType")
    @Expose
    private String orderType;
    @SerializedName("TotalOrderValue")
    @Expose
    private Object totalOrderValue;
    @SerializedName("PaymentTerms")
    @Expose
    private Object paymentTerms;
    @SerializedName("BillingFrequency")
    @Expose
    private Object billingFrequency;
    @SerializedName("Types")
    @Expose
    private String types;
    @SerializedName("TotalArea")
    @Expose
    private Object totalArea;
    @SerializedName("ContractDuration")
    @Expose
    private String contractDuration;
    @SerializedName("ContractStartDate")
    @Expose
    private String contractStartDate;
    @SerializedName("ContractEndDate")
    @Expose
    private String contractEndDate;
    @SerializedName("JobsPerFrequency")
    @Expose
    private String jobsPerFrequency;
    @SerializedName("ServicesRequired")
    @Expose
    private String servicesRequired;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getOrderConfirmationDate() {
        return orderConfirmationDate;
    }

    public void setOrderConfirmationDate(String orderConfirmationDate) {
        this.orderConfirmationDate = orderConfirmationDate;
    }

    public Object getConfirmationMode() {
        return confirmationMode;
    }

    public void setConfirmationMode(Object confirmationMode) {
        this.confirmationMode = confirmationMode;
    }

    public String getConfirmationBy() {
        return confirmationBy;
    }

    public void setConfirmationBy(String confirmationBy) {
        this.confirmationBy = confirmationBy;
    }

    public Object getConfirmationReceivedBy() {
        return confirmationReceivedBy;
    }

    public void setConfirmationReceivedBy(Object confirmationReceivedBy) {
        this.confirmationReceivedBy = confirmationReceivedBy;
    }

    public String getConfirmationReceivedDate() {
        return confirmationReceivedDate;
    }

    public void setConfirmationReceivedDate(String confirmationReceivedDate) {
        this.confirmationReceivedDate = confirmationReceivedDate;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getPONumber() {
        return pONumber;
    }

    public void setPONumber(Object pONumber) {
        this.pONumber = pONumber;
    }

    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public String getEnquiryReferenceNo() {
        return enquiryReferenceNo;
    }

    public void setEnquiryReferenceNo(String enquiryReferenceNo) {
        this.enquiryReferenceNo = enquiryReferenceNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(String serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public String getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(String serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Object getTotalOrderValue() {
        return totalOrderValue;
    }

    public void setTotalOrderValue(Object totalOrderValue) {
        this.totalOrderValue = totalOrderValue;
    }

    public Object getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(Object paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public Object getBillingFrequency() {
        return billingFrequency;
    }

    public void setBillingFrequency(Object billingFrequency) {
        this.billingFrequency = billingFrequency;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Object getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Object totalArea) {
        this.totalArea = totalArea;
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

    public String getJobsPerFrequency() {
        return jobsPerFrequency;
    }

    public void setJobsPerFrequency(String jobsPerFrequency) {
        this.jobsPerFrequency = jobsPerFrequency;
    }

    public String getServicesRequired() {
        return servicesRequired;
    }

    public void setServicesRequired(String servicesRequired) {
        this.servicesRequired = servicesRequired;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}


package com.amana.CLEANSolutions.joblist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InProgressData {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("JobsOrders")
    @Expose
    private String jobsOrders;
    @SerializedName("AssignedTo")
    @Expose
    private String assignedTo;
    @SerializedName("Frequency")
    @Expose
    private String frequency;
    @SerializedName("ServiceOn")
    @Expose
    private String serviceOn;
    @SerializedName("Types")
    @Expose
    private String types;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("Startsat")
    @Expose
    private String startsat;
    @SerializedName("Endsat")
    @Expose
    private String endsat;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("ContractOrder")
    @Expose
    private String contractOrder;
    @SerializedName("ServiceID")
    @Expose
    private String serviceID;
    @SerializedName("BillingAmount")
    @Expose
    private Object billingAmount;
    @SerializedName("JobContinues")
    @Expose
    private String jobContinues;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("LatLong")
    @Expose
    private String latLong;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;
    @SerializedName("CustomerService_Id")
    @Expose
    private String customerServiceId;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsInvoiceRaised")
    @Expose
    private Boolean isInvoiceRaised;
    @SerializedName("EmailSent")
    @Expose
    private Boolean emailSent;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("InvoiceStatus")
    @Expose
    private String invoiceStatus;
    @SerializedName("JobOrdersdetails")
    @Expose
    private ArrayList<JobOrdersdetail> jobOrdersdetails = null;
    @SerializedName("customerdetails")
    @Expose
    private ArrayList<Customerdetail> customerdetails = null;
    @SerializedName("teamdetails")
    @Expose
    private ArrayList<Teamdetail> teamdetails = null;
    @SerializedName("Contracterdetails")
    @Expose
    private ArrayList<Contracterdetail> contracterdetails = null;
    @SerializedName("Commentsdetails")
    @Expose
    private ArrayList<Object> commentsdetails = null;
    @SerializedName("Adhocdata")
    @Expose
    private ArrayList<Object> adhocdata = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobsOrders() {
        return jobsOrders;
    }

    public void setJobsOrders(String jobsOrders) {
        this.jobsOrders = jobsOrders;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getServiceOn() {
        return serviceOn;
    }

    public void setServiceOn(String serviceOn) {
        this.serviceOn = serviceOn;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStartsat() {
        return startsat;
    }

    public void setStartsat(String startsat) {
        this.startsat = startsat;
    }

    public String getEndsat() {
        return endsat;
    }

    public void setEndsat(String endsat) {
        this.endsat = endsat;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getContractOrder() {
        return contractOrder;
    }

    public void setContractOrder(String contractOrder) {
        this.contractOrder = contractOrder;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public Object getBillingAmount() {
        return billingAmount;
    }

    public void setBillingAmount(Object billingAmount) {
        this.billingAmount = billingAmount;
    }

    public String getJobContinues() {
        return jobContinues;
    }

    public void setJobContinues(String jobContinues) {
        this.jobContinues = jobContinues;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCustomerServiceId() {
        return customerServiceId;
    }

    public void setCustomerServiceId(String customerServiceId) {
        this.customerServiceId = customerServiceId;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsInvoiceRaised() {
        return isInvoiceRaised;
    }

    public void setIsInvoiceRaised(Boolean isInvoiceRaised) {
        this.isInvoiceRaised = isInvoiceRaised;
    }

    public Boolean getEmailSent() {
        return emailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        this.emailSent = emailSent;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public ArrayList<JobOrdersdetail> getJobOrdersdetails() {
        return jobOrdersdetails;
    }

    public void setJobOrdersdetails(ArrayList<JobOrdersdetail> jobOrdersdetails) {
        this.jobOrdersdetails = jobOrdersdetails;
    }

    public ArrayList<Customerdetail> getCustomerdetails() {
        return customerdetails;
    }

    public void setCustomerdetails(ArrayList<Customerdetail> customerdetails) {
        this.customerdetails = customerdetails;
    }

    public ArrayList<Teamdetail> getTeamdetails() {
        return teamdetails;
    }

    public void setTeamdetails(ArrayList<Teamdetail> teamdetails) {
        this.teamdetails = teamdetails;
    }

    public ArrayList<Contracterdetail> getContracterdetails() {
        return contracterdetails;
    }

    public void setContracterdetails(ArrayList<Contracterdetail> contracterdetails) {
        this.contracterdetails = contracterdetails;
    }

    public ArrayList<Object> getCommentsdetails() {
        return commentsdetails;
    }

    public void setCommentsdetails(ArrayList<Object> commentsdetails) {
        this.commentsdetails = commentsdetails;
    }

    public ArrayList<Object> getAdhocdata() {
        return adhocdata;
    }

    public void setAdhocdata(ArrayList<Object> adhocdata) {
        this.adhocdata = adhocdata;
    }

}

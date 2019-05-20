
package com.amana.MpestISS.model.schedule;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

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
    @SerializedName("AdhocType")
    @Expose
    private String adhocType;
    @SerializedName("AdhocCustomerName")
    @Expose
    private String adhocCustomerName;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsInvoiceRaised")
    @Expose
    private Boolean isInvoiceRaised;
    @SerializedName("Adhocdata")
    @Expose
    private List<Adhocdatum> adhocdata = null;
    @SerializedName("EmailSent")
    @Expose
    private Boolean emailSent;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("JobOrdersdetails")
    @Expose
    private List<JobOrdersdetail> jobOrdersdetails = null;
    @SerializedName("customerdetails")
    @Expose
    private List<Customerdetail> customerdetails = null;
    @SerializedName("teamdetails")
    @Expose
    private List<Teamdetail> teamdetails = null;
    @SerializedName("Contracterdetails")
    @Expose
    private List<Contracterdetail> contracterdetails = null;
    @SerializedName("BillingAmount")
    @Expose
    private Object billingAmount;
    @SerializedName("JobContinues")
    @Expose
    private String jobContinues;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("ContactNumber")
    @Expose
    private Object contactNumber;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("InProgressedBy")
    @Expose
    private String inProgressedBy;
    @SerializedName("EmailSentDate")
    @Expose
    private String emailSentDate;
    @SerializedName("EmailSentTo")
    @Expose
    private String emailSentTo;

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

    public String getAdhocType() {
        return adhocType;
    }

    public void setAdhocType(String adhocType) {
        this.adhocType = adhocType;
    }

    public String getAdhocCustomerName() {
        return adhocCustomerName;
    }

    public void setAdhocCustomerName(String adhocCustomerName) {
        this.adhocCustomerName = adhocCustomerName;
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

    public List<Adhocdatum> getAdhocdata() {
        return adhocdata;
    }

    public void setAdhocdata(List<Adhocdatum> adhocdata) {
        this.adhocdata = adhocdata;
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

    public List<JobOrdersdetail> getJobOrdersdetails() {
        return jobOrdersdetails;
    }

    public void setJobOrdersdetails(List<JobOrdersdetail> jobOrdersdetails) {
        this.jobOrdersdetails = jobOrdersdetails;
    }

    public List<Customerdetail> getCustomerdetails() {
        return customerdetails;
    }

    public void setCustomerdetails(List<Customerdetail> customerdetails) {
        this.customerdetails = customerdetails;
    }

    public List<Teamdetail> getTeamdetails() {
        return teamdetails;
    }

    public void setTeamdetails(List<Teamdetail> teamdetails) {
        this.teamdetails = teamdetails;
    }

    public List<Contracterdetail> getContracterdetails() {
        return contracterdetails;
    }

    public void setContracterdetails(List<Contracterdetail> contracterdetails) {
        this.contracterdetails = contracterdetails;
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

    public Object getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Object contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getInProgressedBy() {
        return inProgressedBy;
    }

    public void setInProgressedBy(String inProgressedBy) {
        this.inProgressedBy = inProgressedBy;
    }

    public String getEmailSentDate() {
        return emailSentDate;
    }

    public void setEmailSentDate(String emailSentDate) {
        this.emailSentDate = emailSentDate;
    }

    public String getEmailSentTo() {
        return emailSentTo;
    }

    public void setEmailSentTo(String emailSentTo) {
        this.emailSentTo = emailSentTo;
    }

}

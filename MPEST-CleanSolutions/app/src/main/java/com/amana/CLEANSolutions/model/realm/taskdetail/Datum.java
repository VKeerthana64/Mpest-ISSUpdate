
package com.amana.CLEANSolutions.model.realm.taskdetail;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Datum extends RealmObject {

    private String _id;

    private String JobsOrders;

    private String AssignedTo;

    private String Frequency;

    private String serviceOn;

    private String Types;

    private String Status;

    private String UploadStatus="";

    private String Customer;

    private String Startsat;

    private String Endsat;

    private String CreatedBy;

    private String PestType;

    private String ContractOrder;

    private String ServiceID;

    private String BillingAmount;

    private String JobContinues;

    private String Location;

    private String LatLong;

    private String Client_Id;

    private String CustomerService_Id;

    private String ContactPerson;

    private String ContactNumber;

    private String CreateDate;

    private Boolean IsActive;

    private Boolean IsInvoiceRaised;

    private Boolean EmailSent;

    private Integer __v;

    private String UpdatedDate;

    private String UpdatedBy;

    private String InProgressedBy;

    private String EmailSentDate;

    private String EmailSentTo;

    private int CommentCount = 0;

    private String AdhocType="";

    private String AdhocCustomerName="";

    private RealmList<Adhocdata> Adhocdata = null;

    private RealmList<JobOrdersdetail> JobOrdersdetails = null;

    private RealmList<Customerdetail> customerdetails = null;

    private RealmList<Teamdetail> teamdetails = null;

    private RealmList<Contracterdetail> Contracterdetails = null;

    private RealmList<Commentsdetails> Commentsdetails = null;

    private CustomerServicedetails CustomerServicedetails = null;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJobsOrders() {
        return JobsOrders;
    }

    public void setJobsOrders(String jobsOrders) {
        JobsOrders = jobsOrders;
    }

    public String getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        AssignedTo = assignedTo;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getServiceOn() {
        return serviceOn;
    }

    public void setServiceOn(String serviceOn) {
        this.serviceOn = serviceOn;
    }

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public String getStartsat() {
        return Startsat;
    }

    public void setStartsat(String startsat) {
        Startsat = startsat;
    }

    public String getEndsat() {
        return Endsat;
    }

    public void setEndsat(String endsat) {
        Endsat = endsat;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getContractOrder() {
        return ContractOrder;
    }

    public void setContractOrder(String contractOrder) {
        ContractOrder = contractOrder;
    }

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
    }

    public String getBillingAmount() {
        return BillingAmount;
    }

    public void setBillingAmount(String billingAmount) {
        BillingAmount = billingAmount;
    }

    public String getJobContinues() {
        return JobContinues;
    }

    public void setJobContinues(String jobContinues) {
        JobContinues = jobContinues;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getCustomerService_Id() {
        return CustomerService_Id;
    }

    public void setCustomerService_Id(String customerService_Id) {
        CustomerService_Id = customerService_Id;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public Boolean getInvoiceRaised() {
        return IsInvoiceRaised;
    }

    public void setInvoiceRaised(Boolean invoiceRaised) {
        IsInvoiceRaised = invoiceRaised;
    }

    public Boolean getEmailSent() {
        return EmailSent;
    }

    public void setEmailSent(Boolean emailSent) {
        EmailSent = emailSent;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getInProgressedBy() {
        return InProgressedBy;
    }

    public void setInProgressedBy(String inProgressedBy) {
        InProgressedBy = inProgressedBy;
    }

    public String getEmailSentDate() {
        return EmailSentDate;
    }

    public void setEmailSentDate(String emailSentDate) {
        EmailSentDate = emailSentDate;
    }

    public String getEmailSentTo() {
        return EmailSentTo;
    }

    public void setEmailSentTo(String emailSentTo) {
        EmailSentTo = emailSentTo;
    }

    public RealmList<JobOrdersdetail> getJobOrdersdetails() {
        return JobOrdersdetails;
    }

    public void setJobOrdersdetails(RealmList<JobOrdersdetail> jobOrdersdetails) {
        JobOrdersdetails = jobOrdersdetails;
    }

    public RealmList<Customerdetail> getCustomerdetails() {
        return customerdetails;
    }

    public void setCustomerdetails(RealmList<Customerdetail> customerdetails) {
        this.customerdetails = customerdetails;
    }

    public RealmList<Teamdetail> getTeamdetails() {
        return teamdetails;
    }

    public void setTeamdetails(RealmList<Teamdetail> teamdetails) {
        this.teamdetails = teamdetails;
    }

    public RealmList<Contracterdetail> getContracterdetails() {
        return Contracterdetails;
    }

    public void setContracterdetails(RealmList<Contracterdetail> contracterdetails) {
        Contracterdetails = contracterdetails;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getAdhocType() {
        return AdhocType;
    }

    public void setAdhocType(String adhocType) {
        AdhocType = adhocType;
    }

    public String getAdhocCustomerName() {
        return AdhocCustomerName;
    }

    public void setAdhocCustomerName(String adhocCustomerName) {
        AdhocCustomerName = adhocCustomerName;
    }

    public RealmList<Adhocdata> getAdhocdata() {
        return Adhocdata;
    }

    public void setAdhocdata(RealmList<Adhocdata> adhocdata) {
        Adhocdata = adhocdata;
    }

    public RealmList<com.amana.CLEANSolutions.model.realm.taskdetail.Commentsdetails> getCommentsdetails() {
        return Commentsdetails;
    }

    public void setCommentsdetails(RealmList<com.amana.CLEANSolutions.model.realm.taskdetail.Commentsdetails> commentsdetails) {
        Commentsdetails = commentsdetails;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
    }

    public com.amana.CLEANSolutions.model.realm.taskdetail.CustomerServicedetails getCustomerServicedetails() {
        return CustomerServicedetails;
    }

    public void setCustomerServicedetails(com.amana.CLEANSolutions.model.realm.taskdetail.CustomerServicedetails customerServicedetails) {
        CustomerServicedetails = customerServicedetails;
    }
}

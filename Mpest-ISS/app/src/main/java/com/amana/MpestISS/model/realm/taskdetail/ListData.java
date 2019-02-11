
package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmList;
import io.realm.RealmObject;

public class ListData extends RealmObject{

    private String _id;

    private String JobsOrders;

    private String AssignedTo;

    private String Frequency;

    private String ServiceOn;

    private String Status;

    private String Customer;

    private String Startsat;

    private String Endsat;

    private String CreatedBy;

    private String PestType;

    private String ServiceID;

    private String JobContinues;

    private String Location;

    private int CommentCount = 0;

    private String ContactPerson;

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    private String LatLong;

    private String Client_Id;

    private String CustomerService_Id;

    private String ScheduleType;

    private String CreateDate;

    private Boolean IsActive;

    private Boolean ImagesAvaliable;

    private Boolean Verified;

    private String InvoiceStatus;

    private Boolean AllowBillableExport;

    private Boolean IsInvoiceRaised;

    private RealmList<Team> Teams = null;

    private RealmList<String> TeamMembers_Id = null;

    private RealmList<Adhocdata> Adhocdata = null;

    private String AdhocType="";

    private String AdhocCustomerName="";

    private Integer __v;
    
    private String UploadStatus="";

    private RealmList<JobOrdersdetail> JobOrdersdetails = null;

    private RealmList<Customerdetail> customerdetails = null;

    private RealmList<Teamdetail> teamdetails = null;

    private RealmList<Contracterdetail> Contracterdetails = null;

    private RealmList<Commentsdetails> Commentsdetails = null;

    private RealmList<CustomerServicedetails> CustomerServicedetails = null;

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

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getServiceOn() {
        return ServiceOn;
    }

    public void setServiceOn(String serviceOn) {
        ServiceOn = serviceOn;
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

    public String getServiceID() {
        return ServiceID;
    }

    public void setServiceID(String serviceID) {
        ServiceID = serviceID;
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

    public String getScheduleType() {
        return ScheduleType;
    }

    public void setScheduleType(String scheduleType) {
        ScheduleType = scheduleType;
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

    public Boolean getImagesAvaliable() {
        return ImagesAvaliable;
    }

    public void setImagesAvaliable(Boolean imagesAvaliable) {
        ImagesAvaliable = imagesAvaliable;
    }

    public Boolean getVerified() {
        return Verified;
    }

    public void setVerified(Boolean verified) {
        Verified = verified;
    }

    public String getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }

    public Boolean getAllowBillableExport() {
        return AllowBillableExport;
    }

    public void setAllowBillableExport(Boolean allowBillableExport) {
        AllowBillableExport = allowBillableExport;
    }

    public Boolean getInvoiceRaised() {
        return IsInvoiceRaised;
    }

    public void setInvoiceRaised(Boolean invoiceRaised) {
        IsInvoiceRaised = invoiceRaised;
    }

    public RealmList<Team> getTeams() {
        return Teams;
    }

    public void setTeams(RealmList<Team> teams) {
        Teams = teams;
    }

    public RealmList<String> getTeamMembers_Id() {
        return TeamMembers_Id;
    }

    public void setTeamMembers_Id(RealmList<String> teamMembers_Id) {
        TeamMembers_Id = teamMembers_Id;
    }

    public RealmList<Adhocdata> getAdhocdata() {
        return Adhocdata;
    }

    public void setAdhocdata(RealmList<Adhocdata> adhocdata) {
        Adhocdata = adhocdata;
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

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
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

    public RealmList<com.amana.MpestISS.model.realm.taskdetail.Commentsdetails> getCommentsdetails() {
        return Commentsdetails;
    }

    public void setCommentsdetails(RealmList<com.amana.MpestISS.model.realm.taskdetail.Commentsdetails> commentsdetails) {
        Commentsdetails = commentsdetails;
    }

    public RealmList<com.amana.MpestISS.model.realm.taskdetail.CustomerServicedetails> getCustomerServicedetails() {
        return CustomerServicedetails;
    }

    public void setCustomerServicedetails(RealmList<com.amana.MpestISS.model.realm.taskdetail.CustomerServicedetails> customerServicedetails) {
        CustomerServicedetails = customerServicedetails;
    }
}

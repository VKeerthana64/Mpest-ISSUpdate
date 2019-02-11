
package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmObject;

public class JobOrdersdetail extends RealmObject{
   
    private String _id;
    
    private String Client_Id;
  
    private String JobCode;
 
    private String JobPlanningNo;
 
    private String ProductOffering;
  
    private String PestType;
   
    private String SiteCardNo;
   
    private String CustomerID;
  
    private String SiteName;
  
    private String PostalCode;
  
    private String Address;
  
    private String ContactPerson;
   
    private String Phone1;
    
    private String Phone2;
   
    private String Email;

    private String ServiceArea;
  
    private String AreaSize;
   
    private String AGNO;
   
    private String AGID;


    private String StartDate;
 
    private String EndDate;

    private String Frequency;

    private String JobFrequency;

    private String JobQuantity;
 
    private String JobDescription;
  
    private String PostPre;
  
    private String PONO;

    private String Status;
   
    private String CreatedBy;
  
    private String Technician;
 
    private String InitialStartDate;
  
    private String StartTime;
   
    private String EndTime;
   
    private String Amount;
  
    private String AmountGST;
    
    private String JobType;
    
    private String PosttoNAV;
    
    private String ServiceLineCreated;
    
    private Boolean BatchEmail;
  
    private String UpdatedDate;
   
    private String CreatedDate;
    
    private Boolean IsActive;

    private String Types;

    public String getTypes() {
        return Types;
    }

    public void setTypes(String types) {
        Types = types;
    }

    private Integer __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getJobCode() {
        return JobCode;
    }

    public void setJobCode(String jobCode) {
        JobCode = jobCode;
    }

    public String getJobPlanningNo() {
        return JobPlanningNo;
    }

    public void setJobPlanningNo(String jobPlanningNo) {
        JobPlanningNo = jobPlanningNo;
    }

    public String getProductOffering() {
        return ProductOffering;
    }

    public void setProductOffering(String productOffering) {
        ProductOffering = productOffering;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getSiteCardNo() {
        return SiteCardNo;
    }

    public void setSiteCardNo(String siteCardNo) {
        SiteCardNo = siteCardNo;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getServiceArea() {
        return ServiceArea;
    }

    public void setServiceArea(String serviceArea) {
        ServiceArea = serviceArea;
    }

    public String getAreaSize() {
        return AreaSize;
    }

    public void setAreaSize(String areaSize) {
        AreaSize = areaSize;
    }

    public String getAGNO() {
        return AGNO;
    }

    public void setAGNO(String AGNO) {
        this.AGNO = AGNO;
    }

    public String getAGID() {
        return AGID;
    }

    public void setAGID(String AGID) {
        this.AGID = AGID;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }

    public String getJobFrequency() {
        return JobFrequency;
    }

    public void setJobFrequency(String jobFrequency) {
        JobFrequency = jobFrequency;
    }

    public String getJobQuantity() {
        return JobQuantity;
    }

    public void setJobQuantity(String jobQuantity) {
        JobQuantity = jobQuantity;
    }

    public String getJobDescription() {
        return JobDescription;
    }

    public void setJobDescription(String jobDescription) {
        JobDescription = jobDescription;
    }

    public String getPostPre() {
        return PostPre;
    }

    public void setPostPre(String postPre) {
        PostPre = postPre;
    }

    public String getPONO() {
        return PONO;
    }

    public void setPONO(String PONO) {
        this.PONO = PONO;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getTechnician() {
        return Technician;
    }

    public void setTechnician(String technician) {
        Technician = technician;
    }

    public String getInitialStartDate() {
        return InitialStartDate;
    }

    public void setInitialStartDate(String initialStartDate) {
        InitialStartDate = initialStartDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAmountGST() {
        return AmountGST;
    }

    public void setAmountGST(String amountGST) {
        AmountGST = amountGST;
    }

    public String getJobType() {
        return JobType;
    }

    public void setJobType(String jobType) {
        JobType = jobType;
    }

    public String getPosttoNAV() {
        return PosttoNAV;
    }

    public void setPosttoNAV(String posttoNAV) {
        PosttoNAV = posttoNAV;
    }

    public String getServiceLineCreated() {
        return ServiceLineCreated;
    }

    public void setServiceLineCreated(String serviceLineCreated) {
        ServiceLineCreated = serviceLineCreated;
    }

    public Boolean getBatchEmail() {
        return BatchEmail;
    }

    public void setBatchEmail(Boolean batchEmail) {
        BatchEmail = batchEmail;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
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

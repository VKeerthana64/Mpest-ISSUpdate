
package com.amana.MpestISS.model.realm.taskdetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Customerdetail extends RealmObject{

    
    private String _id;
  
    private String CustomerID;
 
    private String CustomerType;
 
    private String CustomerReference;
 
    private String Salutation;
   
    private String FirstName;
 
    private String CompanyName;
  
    private String Address;
 
    private String Postal;
   
    private String Country;
    
    private String ContactPerson;
   
    private String StreetName;
  
    private String BuildingName;
  
    private String Client_Id;
  
    private String CreditTerm;
    
    private String MarketSection;
  
    private String SSREmail;

    private String FixedTeam;
   
    private String InvoiceEmail;
   
    private String InvoiceCC;
  
    private String CreatedBy;
 
    private String CreatedDate;

    private Boolean IsActive;

    private String State;
 
    private Boolean MultipleJobs;

    private Integer __v;
  
    private String UpdatedDate;

    private String UpdatedBy;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getCustomerReference() {
        return CustomerReference;
    }

    public void setCustomerReference(String customerReference) {
        CustomerReference = customerReference;
    }

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getClient_Id() {
        return Client_Id;
    }

    public void setClient_Id(String client_Id) {
        Client_Id = client_Id;
    }

    public String getCreditTerm() {
        return CreditTerm;
    }

    public void setCreditTerm(String creditTerm) {
        CreditTerm = creditTerm;
    }

    public String getMarketSection() {
        return MarketSection;
    }

    public void setMarketSection(String marketSection) {
        MarketSection = marketSection;
    }

    public String getSSREmail() {
        return SSREmail;
    }

    public void setSSREmail(String SSREmail) {
        this.SSREmail = SSREmail;
    }

    public String getFixedTeam() {
        return FixedTeam;
    }

    public void setFixedTeam(String fixedTeam) {
        FixedTeam = fixedTeam;
    }

    public String getInvoiceEmail() {
        return InvoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        InvoiceEmail = invoiceEmail;
    }

    public String getInvoiceCC() {
        return InvoiceCC;
    }

    public void setInvoiceCC(String invoiceCC) {
        InvoiceCC = invoiceCC;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
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

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public Boolean getMultipleJobs() {
        return MultipleJobs;
    }

    public void setMultipleJobs(Boolean multipleJobs) {
        MultipleJobs = multipleJobs;
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
}

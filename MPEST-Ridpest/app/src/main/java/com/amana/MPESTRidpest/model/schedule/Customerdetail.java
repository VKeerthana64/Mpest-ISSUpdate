
package com.amana.MPESTRidpest.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customerdetail {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("CustomerID")
    @Expose
    private String customerID;
    @SerializedName("CustomerType")
    @Expose
    private String customerType;
    @SerializedName("CustomerReference")
    @Expose
    private String customerReference;
    @SerializedName("Website")
    @Expose
    private String website;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Postal")
    @Expose
    private String postal;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("BLK")
    @Expose
    private String bLK;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("StreetName")
    @Expose
    private String streetName;
    @SerializedName("BuildingName")
    @Expose
    private String buildingName;
    @SerializedName("Client_Id")
    @Expose
    private String clientId;
    @SerializedName("MarketSection")
    @Expose
    private String marketSection;
    @SerializedName("TypeofHouse")
    @Expose
    private String typeofHouse;
    @SerializedName("OtherType")
    @Expose
    private String otherType;
    @SerializedName("InvoiceCC")
    @Expose
    private String invoiceCC;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("InvoiceEmail")
    @Expose
    private String invoiceEmail;
    @SerializedName("FixedTeamdata")
    @Expose
    private Object fixedTeamdata;
    @SerializedName("FixedTeam")
    @Expose
    private String fixedTeam;
    @SerializedName("SSREmailTo")
    @Expose
    private Object sSREmailTo;
    @SerializedName("SSREmail")
    @Expose
    private String sSREmail;
    @SerializedName("UpdatedBy")
    @Expose
    private String updatedBy;
    @SerializedName("Category")
    @Expose
    private Object category;
    @SerializedName("bEmail")
    @Expose
    private Object bEmail;
    @SerializedName("bMobile")
    @Expose
    private Object bMobile;
    @SerializedName("CreditTerm")
    @Expose
    private Object creditTerm;
    @SerializedName("Email")
    @Expose
    private Object email;
    @SerializedName("Mobile")
    @Expose
    private Object mobile;
    @SerializedName("Fax")
    @Expose
    private Object fax;
    @SerializedName("Phone")
    @Expose
    private Object phone;
    @SerializedName("Address1")
    @Expose
    private Object address1;
    @SerializedName("LastName")
    @Expose
    private Object lastName;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("Salutation")
    @Expose
    private Object salutation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerReference() {
        return customerReference;
    }

    public void setCustomerReference(String customerReference) {
        this.customerReference = customerReference;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getBLK() {
        return bLK;
    }

    public void setBLK(String bLK) {
        this.bLK = bLK;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMarketSection() {
        return marketSection;
    }

    public void setMarketSection(String marketSection) {
        this.marketSection = marketSection;
    }

    public String getTypeofHouse() {
        return typeofHouse;
    }

    public void setTypeofHouse(String typeofHouse) {
        this.typeofHouse = typeofHouse;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getInvoiceCC() {
        return invoiceCC;
    }

    public void setInvoiceCC(String invoiceCC) {
        this.invoiceCC = invoiceCC;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getInvoiceEmail() {
        return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        this.invoiceEmail = invoiceEmail;
    }

    public Object getFixedTeamdata() {
        return fixedTeamdata;
    }

    public void setFixedTeamdata(Object fixedTeamdata) {
        this.fixedTeamdata = fixedTeamdata;
    }

    public String getFixedTeam() {
        return fixedTeam;
    }

    public void setFixedTeam(String fixedTeam) {
        this.fixedTeam = fixedTeam;
    }

    public Object getSSREmailTo() {
        return sSREmailTo;
    }

    public void setSSREmailTo(Object sSREmailTo) {
        this.sSREmailTo = sSREmailTo;
    }

    public String getSSREmail() {
        return sSREmail;
    }

    public void setSSREmail(String sSREmail) {
        this.sSREmail = sSREmail;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public Object getBEmail() {
        return bEmail;
    }

    public void setBEmail(Object bEmail) {
        this.bEmail = bEmail;
    }

    public Object getBMobile() {
        return bMobile;
    }

    public void setBMobile(Object bMobile) {
        this.bMobile = bMobile;
    }

    public Object getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(Object creditTerm) {
        this.creditTerm = creditTerm;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getAddress1() {
        return address1;
    }

    public void setAddress1(Object address1) {
        this.address1 = address1;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getSalutation() {
        return salutation;
    }

    public void setSalutation(Object salutation) {
        this.salutation = salutation;
    }

}

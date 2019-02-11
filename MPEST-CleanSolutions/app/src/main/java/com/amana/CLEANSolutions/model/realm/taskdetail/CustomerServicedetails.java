
package com.amana.CLEANSolutions.model.realm.taskdetail;

import io.realm.RealmObject;

public class CustomerServicedetails extends RealmObject {


    private String _id;

    private String CustomerID;

    private String sBLK;

    private String sCountry;

    private String sContactPerson;

    private String CreatedBy;

    private String Description;

    private String UpdatedDate;

    private String CreatedDate;

    private Boolean IsActive;

    private Integer __v;

    private String sStreetName;

    private String sPostal;

    private String sBuildingType;

    private String sEmail;

    private String Type;

    private String sName;

    private Integer sDisplayOrder;

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

    public String getsBLK() {
        return sBLK;
    }

    public void setsBLK(String sBLK) {
        this.sBLK = sBLK;
    }

    public String getsCountry() {
        return sCountry;
    }

    public void setsCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getsContactPerson() {
        return sContactPerson;
    }

    public void setsContactPerson(String sContactPerson) {
        this.sContactPerson = sContactPerson;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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

    public String getsStreetName() {
        return sStreetName;
    }

    public void setsStreetName(String sStreetName) {
        this.sStreetName = sStreetName;
    }

    public String getsPostal() {
        return sPostal;
    }

    public void setsPostal(String sPostal) {
        this.sPostal = sPostal;
    }

    public String getsBuildingType() {
        return sBuildingType;
    }

    public void setsBuildingType(String sBuildingType) {
        this.sBuildingType = sBuildingType;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public Integer getsDisplayOrder() {
        return sDisplayOrder;
    }

    public void setsDisplayOrder(Integer sDisplayOrder) {
        this.sDisplayOrder = sDisplayOrder;
    }
}

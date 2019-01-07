
package com.amana.MPESTPestoff.model.realm.AdhocRm;

import io.realm.RealmObject;

public class AdhocModelRm extends RealmObject{

    private String CompanyName;

    private String Address;

    private String Postal;

    private String Email;

    private String LatLong;

    private String Location;

    private String SalesPerson_id;

    private String PestType;

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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLatLong() {
        return LatLong;
    }

    public void setLatLong(String latLong) {
        LatLong = latLong;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSalesPerson_id() {
        return SalesPerson_id;
    }

    public void setSalesPerson_id(String salesPerson_id) {
        SalesPerson_id = salesPerson_id;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }
}

package com.amana.MPESTRidpest.model.realm.logdetails;

import io.realm.RealmObject;

public class LogMaterialsCapturesRmModel extends RealmObject {

    private String MaterialName;
    private String Quantity;
    private String unit;

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

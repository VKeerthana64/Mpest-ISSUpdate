package com.amana.CLEANSolutions.model;

import io.realm.RealmObject;

public class MaterialsCapturesModel{

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

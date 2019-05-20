package com.amana.MpestISS.model.realm.jobdetails;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MaterialsCapturesRmModel extends RealmObject {

    private String MaterialName;
    private String Quantity;
    private String unit;
    private String Dilution= "";
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

    public String getDilution() {
        return Dilution;
    }

    public void setDilution(String dilution) {
        Dilution = dilution;
    }
}

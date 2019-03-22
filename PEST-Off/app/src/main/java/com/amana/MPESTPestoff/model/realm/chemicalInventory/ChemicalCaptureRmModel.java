package com.amana.MPESTPestoff.model.realm.chemicalInventory;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ChemicalCaptureRmModel  extends RealmObject {

    @PrimaryKey
    private int id;
    private String Type;
    private String Name;
    private String Quantity;
    private String unit;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
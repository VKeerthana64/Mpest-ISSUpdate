package com.amana.MPESTPestoff.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class MaterialPreviewModel {

    private String pestType;
    private String Materials;
    private String Quantity;
    private String unit;


    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getMaterials() {
        return Materials;
    }

    public void setMaterials(String materials) {
        Materials = materials;
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

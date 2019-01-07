
package com.amana.MPESTPestoff.model.finalUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialsRequest {

    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("MaterialName")
    @Expose
    private String materialName;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Unit")
    @Expose
    private String unit;

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}

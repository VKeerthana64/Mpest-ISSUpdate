
package com.amana.MpestISS.model.masterdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaterialsResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("MaterialsName")
    @Expose
    private String materialsName;
    @SerializedName("Materialscode")
    @Expose
    private String materialscode;

    @SerializedName("PestType")
    @Expose
    private String pestType;

    @SerializedName("Description")
    @Expose
    private String description;

    @SerializedName("Quantity")
    @Expose
    private String quantity;

    @SerializedName("Unit")
    @Expose
    private String unit;

    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;

    @SerializedName("CreateDate")
    @Expose
    private String createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public String getMaterialscode() {
        return materialscode;
    }

    public void setMaterialscode(String materialscode) {
        this.materialscode = materialscode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
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

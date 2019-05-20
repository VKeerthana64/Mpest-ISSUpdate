package com.amana.MpestISS.model.realm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Happy on 16-Apr-19.
 */

public class RecommendationResponse extends RealmObject
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("PestType")
    @Expose
    private String PestType;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;

    @SerializedName("UpdatedBy")
    @Expose
    private String UpdatedBy;

    @SerializedName("UpdatedDate")
    @Expose
    private String UpdatedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPestType() {
        return PestType;
    }

    public void setPestType(String pestType) {
        PestType = pestType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
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

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }
}
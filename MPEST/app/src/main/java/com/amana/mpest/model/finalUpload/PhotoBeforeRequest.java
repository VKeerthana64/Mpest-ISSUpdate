
package com.amana.mpest.model.finalUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoBeforeRequest {

    @SerializedName("schimgbefore")
    @Expose
    private String schimgbefore;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("Remarks")
    @Expose
    private String remarks;

    public String getSchimgbefore() {
        return schimgbefore;
    }

    public void setSchimgbefore(String schimgbefore) {
        this.schimgbefore = schimgbefore;
    }

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}


package com.amana.pentapest.model.finalUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotoAfterRequest {

    @SerializedName("schimgafter")
    @Expose
    private String schimgafter;
    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("Remarks")
    @Expose
    private String remarks;

    public String getSchimgafter() {
        return schimgafter;
    }

    public void setSchimgafter(String schimgafter) {
        this.schimgafter = schimgafter;
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

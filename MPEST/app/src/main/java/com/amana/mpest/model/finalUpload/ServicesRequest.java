
package com.amana.mpest.model.finalUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesRequest {

    @SerializedName("PestType")
    @Expose
    private String pestType;
    @SerializedName("Services")
    @Expose
    private String services;

    public String getPestType() {
        return pestType;
    }

    public void setPestType(String pestType) {
        this.pestType = pestType;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

}


package com.amana.MpestISS.model.finalUpload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesRequest {

    @SerializedName("PestType")
    @Expose
    private String pestType;

    @SerializedName("Services")
    @Expose
    private String services;

    @SerializedName("Finding")
    @Expose
    private String Finding;

    @SerializedName("Recommendation")
    @Expose
    private String Recommendation;


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

    public String getFinding() {
        return Finding;
    }

    public void setFinding(String finding) {
        Finding = finding;
    }

    public String getRecommendation() {
        return Recommendation;
    }

    public void setRecommendation(String recommendation) {
        Recommendation = recommendation;
    }
}

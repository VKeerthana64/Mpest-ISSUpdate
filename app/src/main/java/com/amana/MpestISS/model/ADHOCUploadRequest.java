
package com.amana.MpestISS.model;

import com.amana.MpestISS.model.finalUpload.MaterialsRequest;
import com.amana.MpestISS.model.finalUpload.PhotoAfterRequest;
import com.amana.MpestISS.model.finalUpload.PhotoBeforeRequest;
import com.amana.MpestISS.model.finalUpload.ServicesRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ADHOCUploadRequest {

    @SerializedName("ServiceOn")
    @Expose
    private String serviceOn;

    @SerializedName("AssignedTo")
    @Expose
    private String assignedTo ;

    @SerializedName("Team")
    @Expose
    private String team= "";

    @SerializedName("WorkorderNo")
    @Expose
    private String workorderNo= "";
    @SerializedName("ContractOrderNo")
    @Expose
    private String contractOrderNo= "";
    @SerializedName("CustomerName")
    @Expose
    private String customerName= "";
    @SerializedName("Location")
    @Expose
    private String location= "";
    @SerializedName("Startsat")
    @Expose
    private String startsat= "";
    @SerializedName("Endsat")
    @Expose
    private String endsat= "";
    @SerializedName("Pesttype")
    @Expose
    private String pesttype= "";
    @SerializedName("Services")
    @Expose
    private String services= "";
    @SerializedName("TeamLead")
    @Expose
    private String teamLead= "";
    @SerializedName("TeamMember")
    @Expose
    private String teamMember= "";
    @SerializedName("TeamRemarks")
    @Expose
    private String teamRemarks= "";
    @SerializedName("TeamSignature")
    @Expose
    private String teamSignature= "";
    @SerializedName("StartLocation")
    @Expose
    private String startLocation= "";
    @SerializedName("EndLocation")
    @Expose
    private String endLocation= "";
    @SerializedName("BreedingFound")
    @Expose
    private String breedingFound= "";
    @SerializedName("Species")
    @Expose
    private String species= "";
    @SerializedName("MistingCariedOutNo")
    @Expose
    private String mistingCariedOutNo= "";
    @SerializedName("MistingCariedOut")
    @Expose
    private String mistingCariedOut= "";
    @SerializedName("Density")
    @Expose
    private String density= "";
    @SerializedName("Instar")
    @Expose
    private String instar= "";
    @SerializedName("NoOfCulls")
    @Expose
    private String noOfCulls= "";
    @SerializedName("NoOfBurrows")
    @Expose
    private String noOfBurrows= "";
    @SerializedName("NoOfDead")
    @Expose
    private String noOfDead= "";
    @SerializedName("Habitat")
    @Expose
    private String habitat= "";
    @SerializedName("Action")
    @Expose
    private String action= "";
    @SerializedName("Recommendation")
    @Expose
    private String recommendation= "";
    @SerializedName("SOR")
    @Expose
    private String sor= "";

    @SerializedName("Others")
    @Expose
    private String others= "";
    @SerializedName("Reason")
    @Expose
    private String reason= "";
    @SerializedName("Rating")
    @Expose
    private String rating= "";
    @SerializedName("Materials")
    @Expose
    private String materials= "";
    @SerializedName("CustomerRemarks")
    @Expose
    private String customerRemarks= "";
    @SerializedName("CustomerSignature")
    @Expose
    private String customerSignature= "";
    @SerializedName("Paymentmethod")
    @Expose
    private String paymentmethod= "";
    @SerializedName("Totalpayment")
    @Expose
    private String totalpayment= "";
    @SerializedName("PaymentNotes")
    @Expose
    private String paymentNotes= "";
    @SerializedName("Totalarea")
    @Expose
    private String totalarea= "";
    @SerializedName("EmailId")
    @Expose
    private String emailId= "";
    @SerializedName("PaymentRemarks")
    @Expose
    private String paymentRemarks= "";
    @SerializedName("CustomerCurrentDate")
    @Expose
    private String customerCurrentDate= "";
    @SerializedName("Totalcompleted")
    @Expose
    private String totalcompleted= "";
    @SerializedName("createdBy")
    @Expose
    private String createdBy= "";
    @SerializedName("Client_Id")
    @Expose
    private String clientId= "";
    @SerializedName("PhotosBeforeArray")
    @Expose
    private ArrayList<PhotoBeforeRequest> photosBeforeArray = null;
    @SerializedName("PhotosAfterArray")
    @Expose
    private ArrayList<PhotoAfterRequest> photosAfterArray = null;
    @SerializedName("ServicesArray")
    @Expose
    private ArrayList<ServicesRequest> servicesArray = null;
    @SerializedName("MaterialsArray")
    @Expose
    private ArrayList<MaterialsRequest> materialsArray = null;

    @SerializedName("Adhocdata")
    @Expose
    private ArrayList<AdhocModel> Adhocdata = null;


    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getWorkorderNo() {
        return workorderNo;
    }

    public void setWorkorderNo(String workorderNo) {
        this.workorderNo = workorderNo;
    }

    public String getContractOrderNo() {
        return contractOrderNo;
    }

    public void setContractOrderNo(String contractOrderNo) {
        this.contractOrderNo = contractOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartsat() {
        return startsat;
    }

    public void setStartsat(String startsat) {
        this.startsat = startsat;
    }

    public String getEndsat() {
        return endsat;
    }

    public void setEndsat(String endsat) {
        this.endsat = endsat;
    }

    public String getPesttype() {
        return pesttype;
    }

    public void setPesttype(String pesttype) {
        this.pesttype = pesttype;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(String teamLead) {
        this.teamLead = teamLead;
    }

    public String getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(String teamMember) {
        this.teamMember = teamMember;
    }

    public String getTeamRemarks() {
        return teamRemarks;
    }

    public void setTeamRemarks(String teamRemarks) {
        this.teamRemarks = teamRemarks;
    }

    public String getTeamSignature() {
        return teamSignature;
    }

    public void setTeamSignature(String teamSignature) {
        this.teamSignature = teamSignature;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getBreedingFound() {
        return breedingFound;
    }

    public void setBreedingFound(String breedingFound) {
        this.breedingFound = breedingFound;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getMistingCariedOutNo() {
        return mistingCariedOutNo;
    }

    public void setMistingCariedOutNo(String mistingCariedOutNo) {
        this.mistingCariedOutNo = mistingCariedOutNo;
    }

    public String getMistingCariedOut() {
        return mistingCariedOut;
    }

    public void setMistingCariedOut(String mistingCariedOut) {
        this.mistingCariedOut = mistingCariedOut;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getInstar() {
        return instar;
    }

    public void setInstar(String instar) {
        this.instar = instar;
    }

    public String getNoOfCulls() {
        return noOfCulls;
    }

    public void setNoOfCulls(String noOfCulls) {
        this.noOfCulls = noOfCulls;
    }

    public String getNoOfBurrows() {
        return noOfBurrows;
    }

    public void setNoOfBurrows(String noOfBurrows) {
        this.noOfBurrows = noOfBurrows;
    }

    public String getNoOfDead() {
        return noOfDead;
    }

    public void setNoOfDead(String noOfDead) {
        this.noOfDead = noOfDead;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public String getCustomerSignature() {
        return customerSignature;
    }

    public void setCustomerSignature(String customerSignature) {
        this.customerSignature = customerSignature;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getTotalpayment() {
        return totalpayment;
    }

    public void setTotalpayment(String totalpayment) {
        this.totalpayment = totalpayment;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public String getTotalarea() {
        return totalarea;
    }

    public void setTotalarea(String totalarea) {
        this.totalarea = totalarea;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPaymentRemarks() {
        return paymentRemarks;
    }

    public void setPaymentRemarks(String paymentRemarks) {
        this.paymentRemarks = paymentRemarks;
    }

    public String getCustomerCurrentDate() {
        return customerCurrentDate;
    }

    public void setCustomerCurrentDate(String customerCurrentDate) {
        this.customerCurrentDate = customerCurrentDate;
    }

    public String getTotalcompleted() {
        return totalcompleted;
    }

    public void setTotalcompleted(String totalcompleted) {
        this.totalcompleted = totalcompleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ArrayList<PhotoBeforeRequest> getPhotosBeforeArray() {
        return photosBeforeArray;
    }

    public void setPhotosBeforeArray(ArrayList<PhotoBeforeRequest> photosBeforeArray) {
        this.photosBeforeArray = photosBeforeArray;
    }

    public ArrayList<PhotoAfterRequest> getPhotosAfterArray() {
        return photosAfterArray;
    }

    public void setPhotosAfterArray(ArrayList<PhotoAfterRequest> photosAfterArray) {
        this.photosAfterArray = photosAfterArray;
    }

    public ArrayList<ServicesRequest> getServicesArray() {
        return servicesArray;
    }

    public void setServicesArray(ArrayList<ServicesRequest> servicesArray) {
        this.servicesArray = servicesArray;
    }

    public ArrayList<MaterialsRequest> getMaterialsArray() {
        return materialsArray;
    }

    public void setMaterialsArray(ArrayList<MaterialsRequest> materialsArray) {
        this.materialsArray = materialsArray;
    }

    public String getSor() {
        return sor;
    }

    public void setSor(String sor) {
        this.sor = sor;
    }

    public String getServiceOn() {
        return serviceOn;
    }

    public void setServiceOn(String serviceOn) {
        this.serviceOn = serviceOn;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public ArrayList<AdhocModel> getAdhocdata() {
        return Adhocdata;
    }

    public void setAdhocdata(ArrayList<AdhocModel> adhocdata) {
        Adhocdata = adhocdata;
    }

}

package com.amana.MPESTPestoff.model.realm.jobdetails;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ServicesCapturesRmModel extends RealmObject {
    @PrimaryKey
    private int id;
    private String NoCulls;
    private String NoBorrows;
    private String NoDead;
    private String Habitat;
    private String Reason;
    private String Action;
    private String Recommendation;
    private String Breeding;
    private String Speices;
    private String Density;
    private String Instar;
    private String MistingCarriedOut;
    private String MistingCarriedIFNo;
    private String MistingCarriedOthers;

    public String getNoCulls() {
        return NoCulls;
    }

    public void setNoCulls(String noCulls) {
        NoCulls = noCulls;
    }

    public String getNoBorrows() {
        return NoBorrows;
    }

    public void setNoBorrows(String noBorrows) {
        NoBorrows = noBorrows;
    }

    public String getNoDead() {
        return NoDead;
    }

    public void setNoDead(String noDead) {
        NoDead = noDead;
    }

    public String getHabitat() {
        return Habitat;
    }

    public void setHabitat(String habitat) {
        Habitat = habitat;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getRecommendation() {
        return Recommendation;
    }

    public void setRecommendation(String recommendation) {
        Recommendation = recommendation;
    }

    public String getBreeding() {
        return Breeding;
    }

    public void setBreeding(String breeding) {
        Breeding = breeding;
    }

    public String getSpeices() {
        return Speices;
    }

    public void setSpeices(String speices) {
        Speices = speices;
    }

    public String getDensity() {
        return Density;
    }

    public void setDensity(String density) {
        Density = density;
    }

    public String getInstar() {
        return Instar;
    }

    public void setInstar(String instar) {
        Instar = instar;
    }

    public String getMistingCarriedOut() {
        return MistingCarriedOut;
    }

    public void setMistingCarriedOut(String mistingCarriedOut) {
        MistingCarriedOut = mistingCarriedOut;
    }

    public String getMistingCarriedIFNo() {
        return MistingCarriedIFNo;
    }

    public void setMistingCarriedIFNo(String mistingCarriedIFNo) {
        MistingCarriedIFNo = mistingCarriedIFNo;
    }

    public String getMistingCarriedOthers() {
        return MistingCarriedOthers;
    }

    public void setMistingCarriedOthers(String mistingCarriedOthers) {
        MistingCarriedOthers = mistingCarriedOthers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

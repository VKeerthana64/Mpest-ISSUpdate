package com.amana.CLEANSolutions.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class PestModel {
    private Integer selectedPosition;
    private String title;

    public PestModel(Integer selectedPosition, String title) {
        this.selectedPosition = selectedPosition;
        this.title = title;
    }


    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(Integer selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

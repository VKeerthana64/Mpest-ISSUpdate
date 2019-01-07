package com.amana.MPESTRidpest.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class RemarskModel {
    private Integer id;
    private String name;
    private Boolean isSelected = false;

    public RemarskModel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}

package com.amana.MpestISS.announcement.model;

/**
 * Created by Happy on 25-Mar-19.
 */
public class Count {

    private int mId;
    private String counts;


    public Count()
    {

    }
    public Count(String counts)
    {
        this.counts = counts;
    }

    public Count(int mId, String counts)
    {
        this.mId = mId;
        this.counts = counts;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getCounts( ) {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

}
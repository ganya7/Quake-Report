package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by Arvind on 16/10/17.
 */

public class Quake {
    private double mMagnitude;
    private String mLocation;
    private long mDate;
    private String mUrl;

   /* public Quake(double mag, String loc, long dt){
        mMagnitude = mag;
        mLocation = loc;
        mDate = dt;

    }*/

    public Quake(double mag, String loc, long dt, String url) {
        mMagnitude = mag;
        mLocation = loc;
        mDate = dt;
        mUrl = url;

    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }
}

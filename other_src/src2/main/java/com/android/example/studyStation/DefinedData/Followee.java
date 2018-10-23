package com.android.example.studyStation.DefinedData;

/**
 * Created by AmmarRabie on 22/11/2017.
 */

/**
 * this class is a user defined data that represent the followee that will be displayed at followersFragment
 */
public class Followee {

    private String mName;
    private String mEmail;
    private String mUniName;
    private String mFacName;
    private String mDepName;

    public Followee() {
    }

    public Followee(String mName, String mEmail, String mUniName, String mFacName, String mDepName) {
        this.mName = mName;
        this.mEmail = mEmail;
        this.mUniName = mUniName;
        this.mFacName = mFacName;
        this.mDepName = mDepName;
    }

    ////////////////////////////////// getters ////////////////////////////////

    public String getmName() {
        return mName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public String getmUniName() {
        return mUniName;
    }

    public String getmFacName() {
        return mFacName;
    }

    public String getmDepName() {
        return mDepName;
    }
}

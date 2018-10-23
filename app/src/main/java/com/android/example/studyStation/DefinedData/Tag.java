package com.android.example.studyStation.DefinedData;

/**
 * Created by ramym on 12/12/2017.
 */

public class Tag {

    private String name;
    private String des;

    public Tag() {
    }

    public Tag(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return des;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String des) {
        this.des = des;
    }

}

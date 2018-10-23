package com.android.example.studyStation.DefinedData;

/**
 * Created by Loai Ali on 12/16/2017.
 */

public class CourseLabel {
    private  String code;
    private  String description;
    private String facultyName;
    private String uniName;
    private String  label;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public CourseLabel(String CODE, String DESCRIPTION, String FACULTY, String UNI, String LABEL) {
        this.code = CODE;
        this.description = DESCRIPTION;
        this.uniName = UNI;
        this.label = LABEL;
        this.facultyName = FACULTY;

    }


}

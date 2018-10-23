package com.android.example.studyStation.DefinedData;

/**
 * Created by AmmarRabie on 18/12/2017.
 */

import java.io.Serializable;

/**
 * this represent a single course notes with tag name and creator email
 */
public class CourseNotes implements Serializable{

    private String creatorEmail;
    private String CourseLabel_code;
    private String facName;
    private String uniName;
    private String creationDate;
    private String courseLabel;
    private String tagName;


    public CourseNotes(String creatorEmail, String courseLabel_code, String facName, String uniName, String creationDate, String courseLabel) {
        this.creatorEmail = creatorEmail;
        CourseLabel_code = courseLabel_code;
        this.facName = facName;
        this.uniName = uniName;
        this.creationDate = creationDate;
        this.courseLabel = courseLabel;
    }

    public CourseNotes() {
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public String getCourseLabel_code() {
        return CourseLabel_code;
    }

    public void setCourseLabel_code(String courseLabel_code) {
        CourseLabel_code = courseLabel_code;
    }

    public String getFacName() {
        return facName;
    }

    public void setFacName(String facName) {
        this.facName = facName;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCourseLabel() {
        return courseLabel;
    }

    public void setCourseLabel(String courseLabel) {
        this.courseLabel = courseLabel;
    }
}


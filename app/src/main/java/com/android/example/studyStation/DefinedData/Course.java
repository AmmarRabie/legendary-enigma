package com.android.example.studyStation.DefinedData;

import java.io.Serializable;

/**
 * Created by Loai Ali on 12/9/2017.
 */

public class Course implements Serializable {
    //    private  String code;
    private String departmentName;
    private String facultyName;
    private String univesityName;
    private String description;
    private String label;
    private String type;
    private String code;
    private String course_label_code;
    private String creatorEmail;

    public String getCode() {
        return code;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Course(String departmentName, String facultyName, String univesityName, String description, String label, String courseType, String CreatorEmail) {
        this.departmentName = departmentName;
        this.facultyName = facultyName;
        this.univesityName = univesityName;
        this.description = description;
        this.label = label;
        this.type=courseType;
        this.creatorEmail=CreatorEmail;
    }


    public Course(String departmentName, String facultyName, String univesityName, String description, String label, String courseType) {
        this.departmentName = departmentName;
        this.facultyName = facultyName;
        this.univesityName = univesityName;
        this.description = description;
        this.label = label;
        this.type = courseType;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public void setUnivesityName(String univesityName) {
        this.univesityName = univesityName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public String getUnivesityName() {
        return univesityName;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourse_label_code() {
        return course_label_code;
    }

    public void setCourse_label_code(String course_label_code) {
        this.course_label_code = course_label_code;
    }

    public Course() {
    }
}

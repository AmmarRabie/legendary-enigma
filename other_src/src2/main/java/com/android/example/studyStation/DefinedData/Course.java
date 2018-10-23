package com.android.example.studyStation.DefinedData;

/**
 * Created by Loai Ali on 12/9/2017.
 */

public class Course {
//    private  String code;
    private String departmentName;
    private String facultyName;
    private String univesityName;
    private String description;
    private String label;
    private  String type;
    private String creatorEmail;


    public void setType(String type) {
        this.type = type;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
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
    public String getType(){return type;}

    public String getLabel() {
        return label;
    }

    public  Course(){}
}

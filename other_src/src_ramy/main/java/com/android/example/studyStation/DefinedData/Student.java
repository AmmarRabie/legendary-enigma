package com.android.example.studyStation.DefinedData;

/**
 * Created by AmmarRabie on 07/12/2017.
 */

public class Student {
    private String name;
    private String email;
    private String pas;
    private String dep;
    private String fac;
    private String uni;

    public Student() {
    }

    public Student(String name, String email, String pas, String dep, String fac, String uni) {
        this.name = name;
        this.email = email;
        this.pas = pas;
        this.dep = dep;
        this.fac = fac;
        this.uni = uni;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPas() {
        return pas;
    }

    public String getDep() {
        return dep;
    }

    public String getFac() {
        return fac;
    }

    public String getUni() {
        return uni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }
}

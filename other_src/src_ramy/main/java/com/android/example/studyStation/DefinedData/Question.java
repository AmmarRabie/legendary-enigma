package com.android.example.studyStation.DefinedData;

/**
 * Created by AmmarRabie on 07/12/2017.
 */

public class Question {
    private String askerEmail;
    private String creationdate;
    private String header;

    public Question() {
    }

    public Question(String askerEmail,String creationdate, String header) {
        this.askerEmail = askerEmail;
        this.creationdate = creationdate;
        this.header = header;

    }

    public String getAskerEmail() {
        return askerEmail;
    }

    public String getcreationDate() {
        return creationdate;
    }

    public String getHeader() {
        return header;
    }



    public void setaskerEmail(String askerEmail) {
        this.askerEmail = askerEmail;
    }

    public void setcreationdate(String askerEmail) { this.askerEmail = askerEmail;}

    public void setHeader(String header) { this.header = header;}

}


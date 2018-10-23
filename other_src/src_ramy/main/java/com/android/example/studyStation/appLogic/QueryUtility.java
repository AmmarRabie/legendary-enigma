package com.android.example.studyStation.appLogic;

import android.text.TextUtils;
import android.util.Log;

import com.android.example.studyStation.DefinedData.Answer;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.DefinedData.Tag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AmmarRabie on 18/08/2017.
 */

public final class QueryUtility {


    private QueryUtility() {
    }


    /**
     * @param std the student as object in java
     * @return the string json of this student
     */
    static public String unparseStudent(Student std) {
        JSONObject base = new JSONObject();
        try {
            base.put("email", std.getEmail());
            base.put("name", std.getName());
            base.put("pass", std.getPas());
            base.put("dep", std.getDep());
            base.put("fac", std.getFac());
            base.put("uni", std.getUni());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return base.toString();
    }


    /**
     * take the response of followees and parse to list<{@link Followee}>
     *
     * @param JSONResponse
     * @return
     */
    static public List<Followee> parseFollowees(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to
        List<Followee> followees = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray followeeArray = baseJsonResponse.getJSONArray("followees");

            // For each followee in the followeeArray, create an Followee object
            for (int i = 0; i < followeeArray.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentFollowee = followeeArray.getJSONObject(i);

                // Extract the value for the key called "email"
                String email = currentFollowee.getString("email");

                // Extract the value for the key called "dep"
                String dep = currentFollowee.getString("dep");

                // Extract the value for the key called "fac"
                String fac = currentFollowee.getString("fac");

                // Extract the value for the key called "uni"
                String uni = currentFollowee.getString("uni");

                // Extract the value for the key called "name"
                String name = currentFollowee.getString("name");


                // Create a new Followee object
                Followee followee = new Followee(name, email, uni, fac, dep);

                // Add the new {@link Earthquake} to the list of followees.
                followees.add(followee);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the followees JSON results", e);
        }

        // Return the list of followees
        return followees;
    }


    /**
     * take the response of followees and parse to list<{@link Followee}>
     *
     * @param JSONResponse
     * @return
     */
    static public ArrayList<String> parseDepartments(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to
        ArrayList<String> departments = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray departmentsArray = baseJsonResponse.getJSONArray("departments");

            // For each followee in the departmentsArray, create an Followee object
            for (int i = 0; i < departmentsArray.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentDepartment = departmentsArray.getJSONObject(i);

                // Extract the value for the key called "email"
                String depName = currentDepartment.getString("department");

                // Add the new {@link Earthquake} to the list of followees.
                departments.add(depName);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the followees JSON results", e);
        }

        // Return the list of followees
        return departments;
    }


    /**
     * take the response of followees and parse to list<{@link Followee}>
     *
     * @param JSONResponse
     * @return
     */
    static public ArrayList<String> parseUniversities(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to
        ArrayList<String> universities = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray universitiesArray = baseJsonResponse.getJSONArray("universities");

            // For each followee in the universitiesArray, create an Followee object
            for (int i = 0; i < universitiesArray.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentDepartment = universitiesArray.getJSONObject(i);

                // Extract the value for the key called "email"
                String uniName = currentDepartment.getString("university");

                // Add the new {@link Earthquake} to the list of followees.
                universities.add(uniName);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the followees JSON results", e);
        }

        // Return the list of followees
        return universities;
    }


    /**
     * take the response of followees and parse to list<{@link Followee}>
     *
     * @param JSONResponse
     * @return
     */
    static public ArrayList<String> parseFaculties(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to
        ArrayList<String> faculties = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray facultyArray = baseJsonResponse.getJSONArray("faculties");

            // For each followee in the facultyArray, create an Followee object
            for (int i = 0; i < facultyArray.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentFaculty = facultyArray.getJSONObject(i);

                // Extract the value for the key called "email"
                String facName = currentFaculty.getString("faculty");

                // Add the new {@link Earthquake} to the list of followees.
                faculties.add(facName);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the followees JSON results", e);
        }

        // Return the list of followees
        return faculties;
    }


    static public String parseToken(String JSONResponse) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }
        try {
            return new JSONObject(JSONResponse).getString("token");

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the token JSON results", e);
        }
        return null;
    }

    public static List<Student> parseStudents(String JSONResponse) {
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to
        List<Student> Studentslist = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray students = baseJsonResponse.getJSONArray("students");

            // For each followee in the followeeArray, create an Followee object
            for (int i = 0; i < students.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentStudent = students.getJSONObject(i);

                // Extract the value for the key called "email"
                String email = currentStudent.getString("email");

                // Extract the value for the key called "dep"
                String dep = currentStudent.getString("dep");

                // Extract the value for the key called "fac"
                String fac = currentStudent.getString("fac");

                // Extract the value for the key called "uni"
                String uni = currentStudent.getString("uni");

                // Extract the value for the key called "name"
                String name = currentStudent.getString("name");


                // Create a new Followee object
                Student student = new Student(name, email, uni,"000", fac, dep);

                // Add the new {@link Earthquake} to the list of followees.
                Studentslist.add(student);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the student JSON results", e);
        }

        // Return the list of followees
        return Studentslist;
    }
////////////////////////////////////RRRRRRRRRRRRRAAAAAAAAAAAAAAAAMMMMMMMMMMMMMMYYYYYYYYYY//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////Ramy
    public static List<Tag> parsetags(String response) {

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        List<Tag> tagslist = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(response);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray tags = baseJsonResponse.getJSONArray("tags");

            // For each followee in the followeeArray, create an Followee object
            for (int i = 0; i < tags.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentTag = tags.getJSONObject(i);

                // Extract the value for the key called "email"
                String des = currentTag.getString("description");

                // Extract the value for the key called "dep"
                String name = currentTag.getString("name");



                // Create a new Followee object
                Tag tag = new Tag(name, des);

                // Add the new {@link Earthquake} to the list of followees.
                tagslist.add(tag);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the tag JSON results", e);
        }

        // Return the list of followees
        return tagslist;
    }


    /////////////////////////////////////////////////////////////////Ramy
    public static List<Question> parseQuestion(String response) {

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        List<Question> Questionlist = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(response);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray Questions = baseJsonResponse.getJSONArray("questions");

            // For each followee in the followeeArray, create an Followee object
            for (int i = 0; i < Questions.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentQuestion = Questions.getJSONObject(i);

                String askerEmail = currentQuestion.getString("askerEmail");

                String content = currentQuestion.getString("content");

                String creationDate = currentQuestion.getString("creationDate");

                Question question = new Question(askerEmail,creationDate,content);

                // Add the new {@link Earthquake} to the list of followees.
                Questionlist.add(question);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Question JSON results", e);
        }

        // Return the list of followees
        return Questionlist;
    }

    public static String parseStudentName(String JSONResponse) {
        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding followees to

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        String name=null;
        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray students = baseJsonResponse.getJSONArray("students");
            // For each followee in the followeeArray, create an Followee object
                // Get a single earthquake at position i within the list of followees
                JSONObject currentStudent = students.getJSONObject(0);
                // Extract the value for the key called "name"
                 name = currentStudent.getString("name");


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the student name  JSON results", e);
        }

        // Return the list of followees
        return name;
    }

    public static List<Answer> parseAnswers(String response) {

        if (TextUtils.isEmpty(response)) {
            return null;
        }

        List<Answer> Answerlist = new ArrayList<>();

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(response);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray Answers = baseJsonResponse.getJSONArray("answers");

            // For each followee in the followeeArray, create an Followee object
            for (int i = 0; i < Answers.length(); i++) {

                // Get a single earthquake at position i within the list of followees
                JSONObject currentAnswer = Answers.getJSONObject(i);

                String askerEmail = currentAnswer.getString("askerEmail");

                String content = currentAnswer.getString("content");

                String creationDate = currentAnswer.getString("QuestionCreationDate");

                String replyierEmail=currentAnswer.getString("replierEmail");

                String replyingDate=currentAnswer.getString("replyingDate");


                Answer answer = new Answer(askerEmail,creationDate,content,replyierEmail,replyingDate);

                // Add the new {@link Earthquake} to the list of followees.
                Answerlist.add(answer);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Answer JSON results", e);
        }

        // Return the list of followees
        return Answerlist;

    }


    public static int  getAnswersVotesSize(String response) {

        if (TextUtils.isEmpty(response))
        {
            return 0;
        }

        int size=0;
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(response);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray Answers = baseJsonResponse.getJSONArray("answervotes");

            size=Answers.length();

        }catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Answsers votes JSON results", e);
        }


        return size;

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}




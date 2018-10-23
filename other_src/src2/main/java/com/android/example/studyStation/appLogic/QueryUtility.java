package com.android.example.studyStation.appLogic;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.CourseLabel;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.Student;

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
    static public String unparseCourseNotesInfo(Course course){
        JSONObject base=new JSONObject();
        try{
            base.put("label",course.getLabel());
            base.put("facName",course.getFacultyName());
            base.put("uniName",course.getUnivesityName());
        }catch (JSONException e){
            e.printStackTrace();
        }

        return base.toString();
    }
static public String unparseselfStudyCourse(Course course){
        JSONObject base=new JSONObject();
        try{
            base.put("label",course.getLabel());

        }
        catch (JSONException e){
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

static  public List<CourseLabel>parseCourseList(String JSONResponse){
    // If the JSON string is empty or null, then return early.
    if (TextUtils.isEmpty(JSONResponse)) {
        return null;
    }


    // Create an empty ArrayList that we can start adding followees to
    List<CourseLabel> courses = new ArrayList<>();

    // Try to parse the JSON response string. If there's a problem with the way the JSON
    // is formatted, a JSONException exception object will be thrown.
    // Catch the exception so the app doesn't crash, and print the error message to the logs.
    try {

        // Create a JSONObject from the JSON response string
        JSONObject baseJsonResponse = new JSONObject(JSONResponse);

        // Extract the JSONArray associated with the key called "features",
        // which represents a list of features (or followees).
        JSONArray CourseArray = baseJsonResponse.getJSONArray("courses");

        // For each followee in the CourseArray, create an Followee object
        for (int i = 0; i < CourseArray.length(); i++) {

            // Get a single earthquake at position i within the list of followees
            JSONObject currentCourse = CourseArray.getJSONObject(i);

            // Extract the value for the key called "email"
            String description = currentCourse.getString("description");

            // Extract the value for the key called "dep"
            String label = currentCourse.getString("label");

            // Extract the value for the key called "fac"
            String code = currentCourse.getString("code");

            // Extract the value for the key called "uni"
            String uniName = currentCourse.getString("uniName");

            // Extract the value for the key called "name"
            String facName = currentCourse.getString("facName");


            // Create a new Followee object
            CourseLabel course = new CourseLabel(code,description,facName,uniName,label);

            // Add the new {@link Earthquake} to the list of followees.
            courses.add(course);
        }

    } catch (JSONException e) {
        // If an error is thrown when executing any of the above statements in the "try" block,
        // catch the exception here, so the app doesn't crash. Print a log message
        // with the message from the exception.
        Log.e("QueryUtils", "Problem parsing the followees JSON results", e);
    }

    // Return the list of followees
    return courses;

}
static public ArrayAdapter<Course> parseSelfStudyCourse(String JSONResponse){
    if (TextUtils.isEmpty(JSONResponse)) {
        return null;
    }
    ArrayAdapter<String> SelfStudyCourses =new ArrayAdapter<String >();
    try{
        JSONObject baseJsonResponse=new JSONObject((JSONResponse));
        JSONArray coursesarray = baseJsonResponse.getJSONArray("courses");
        for(int i=0;i<coursesarray.length();i++)
        {
            JSONObject currentcourse=coursesarray.getJSONObject(i);
            String creatorEmail=currentcourse.getString("creatorEmail");
            String label=currentcourse.getString("label");
            Course newcourse=new Course("","","","",label,"SelfStudy",creatorEmail);
            SelfStudyCourses.add(newcourse);
        }

    }
    catch (JSONException e) {
        // If an error is thrown when executing any of the above statements in the "try" block,
        // catch the exception here, so the app doesn't crash. Print a log message
        // with the message from the exception.
        Log.e("QueryUtils", "Problem parsing the MyStudy JSON results", e);
    }

    return  SelfStudyCourses;
}
static public List<Course> parseCourseNotes(String JSONResponse)
{
    if (TextUtils.isEmpty(JSONResponse)) {
        return null;
    }
    List<Course>CourseNotes=new ArrayList<>();
    try {
        JSONObject baseJsonResponse=new JSONObject(JSONResponse);
        JSONArray coursearray=baseJsonResponse.getJSONArray("courses");
         for(int i=0;i<coursearray.length();i++)
         {
             JSONObject currentcourse=coursearray.getJSONObject(i);
             String creatorEmail=currentcourse.getString("creatorEmail");
             String courseLabelCode=currentcourse.getString("courseLabelCode");
             String facName=currentcourse.getString("facName");
             String uniName=currentcourse.getString("uniName");
             String label=currentcourse.getString("label");
             String description=currentcourse.getString("description");
             Course newcourse=new Course("",facName,uniName,description,label,"CourseNotes",creatorEmail);
             CourseNotes.add(newcourse);
         }

    }
    catch (JSONException e)
    {
        Log.e("QueryUtils", "Problem parsing the MyStudy JSON results", e);
    }
    return CourseNotes;
}

    static  public List<Course> parseMyStudy(String JSONResponse){

        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }
        List<Course> mystudy = new ArrayList<>();
        try
        {
            JSONObject baseJsonResponse = new JSONObject(JSONResponse);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or followees).
            JSONArray coursesarray = baseJsonResponse.getJSONArray("My Courses");
            for(int i=0;i<coursesarray.length();i++)
            {
                JSONObject currentcourse = coursesarray.getJSONObject(i);

                // Extract the value for the key called "email"
                String facultyName = currentcourse.getString("facName");

                // Extract the value for the key called "dep"
                String uniName = currentcourse.getString("uniName");

                // Extract the value for the key called "fac"
                String label = currentcourse.getString("label");

                // Extract the value for the key called "uni"
                String type = currentcourse.getString("type");


                String depName=currentcourse.getString("depName");

                String description=currentcourse.getString("description");
                String creatorEmail=currentcourse.getString("creatorEmail");


                // Create a new Course object
               Course newcourse=new Course(depName,facultyName,uniName,description,label,type,creatorEmail);
                // Followee followee = new Followee(facultyName, email, uni, fac, dep);

                // Add the new {@link Earthquake} to the list of followees.
                mystudy.add(newcourse);
            }


        }
        catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the MyStudy JSON results", e);
        }
        return  mystudy;
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
}




package com.android.example.studyStation.appLogic;

/**
 * Created by AmmarRabie on 26/10/2017.
 */

import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.example.studyStation.DefinedData.Answer;
import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.CourseLabel;
import com.android.example.studyStation.DefinedData.CourseNotes;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.NewFeed;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.DefinedData.Tag;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.server.ServerUtility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.example.studyStation.DefinedData.UserInfo.email;
import static com.android.example.studyStation.DefinedData.UserInfo.pass;
import static com.android.example.studyStation.DefinedData.UserInfo.token;
import static com.android.example.studyStation.server.ServerUtility.getResponse;

/**
 * this class handle the app logic
 */
public class Logic {
    private Logic() {
    }

    ////////////////////////////////////// log in activity //////////////////////////////////////////////////

    /**
     * @param email the email of the user to log in (can be invalid)
     * @param pass  the password of the user to log in (can be invalid)
     * @return the access token to be used again
     */
    public static String logIn(String[] DataError, String email, String pass) {
        DataError[0] = null;
        DataError[1] = null;
        if (email == null || pass == null)
            return null;
        String[] response = ServerUtility.getToken("/login", email, pass);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseToken(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    /**
     * tell the caller if this email pattern is correct, this function doesn't check if this password match in the database
     *
     * @param email the email to check out
     * @return true if this pattern is correct, false otherwise
     */
    public static boolean validateEmail(String email) {
        if (email == null || TextUtils.isEmpty(email)) return false;

        String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        // here it is a vlid email

        // check also that it has .edu (educational)
        return email.contains(".edu");
    }


    /**
     * get the the followees to specific user
     *
     * @return list of all students this user follow
     */
    public static List<Followee> getFollowees(String token, String[] DataError) {
        DataError[0] = null;
        DataError[1] = null;
        String[] jsonResponse = getResponse("/followees", token);
        if (jsonResponse[1] != null)
            DataError[0] = QueryUtility.parseMes(jsonResponse[1]);
        if (jsonResponse[0] != null)
            return QueryUtility.parseFollowees(jsonResponse[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    /**
     * tell the caller if this pass pattern is correct (if it too short or not), this function doesn't check if this password match in the database
     *
     * @param pass the email to check out
     * @return true if this pattern is correct, false otherwise
     */
    boolean validatePass(String[] DataError, String pass) {
        return true;
    }


    /**
     * @param std
     * @return the token
     */
    public static String signUp(String[] DataError, Student std) {
        DataError[0] = null;
        DataError[1] = null;
        if (!validateEmail(std.getEmail())) {
            DataError[0] = "invalid email";
            return null;
        }
        String inputJson = QueryUtility.unparseStudent(std);
        String url = "/signup";
        String[] response = ServerUtility.getResponse(url, inputJson, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseToken(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static String insertAnswer(String[] DataError, String token, Answer ans) {
        DataError[0] = null;
        DataError[1] = null;

        String inputJson = QueryUtility.unparseAnswer(ans);
        String url = "/insertanswer";
        String[] response = ServerUtility.getResponse(url, inputJson, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static String deleteCourseNotes(String[] DataError, String token, Course course) {
        DataError[0] = null;
        DataError[1] = null;
        String inputJson = QueryUtility.unparseCourseNotesInfo(course, null); // null as this should come with this student
        String[] response = getResponse("/deleteCourseNotes", inputJson, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null) {
            JSONObject object = null;
            String message = "Invalid";
            try {
                object = new JSONObject(response[0]);
                message = object.getString("Message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        }
        DataError[1] = "can't connect!";
        return null;
    }


    public static String deleteCourseNoteFromModerator(String[] DataError, String token, CourseNotes course) {
        DataError[0] = null;
        DataError[1] = null;
        Course c = new Course();
        c.setCourse_label_code(course.getCourseLabel_code());
        c.setFacultyName(course.getFacName());
        c.setUnivesityName(course.getUniName());
        c.setLabel(course.getCourseLabel());
        String inputJson = QueryUtility.unparseCourseNotesInfo(c, course.getCreatorEmail()); // null as this should come with this student

        String[] response = getResponse("/deleteCourseNote", inputJson, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null) {
            JSONObject object = null;
            String message = "Invalid";
            try {
                object = new JSONObject(response[0]);
                message = object.getString("mes");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        }
        DataError[1] = "can't connect!";
        return null;
    }

    public static List<Course> getMyStudyCourses(String[] DataError, String token, @Nullable String email) {
        DataError[0] = null;
        DataError[1] = null;

        String[] response;
        if (email == null)
            response = getResponse("/mystudy", token);
        else
            response = getResponse("/mystudy?email=" + email, token);

        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMyStudy(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static String deleteSelfStudyCourse(String[] DataError, String token, Course course) {
        DataError[0] = null;
        DataError[1] = null;
        String inputJson = QueryUtility.unparseselfStudyCourse(course);

        String[] response = getResponse("/deleteSelfStudyCourse/" + course.getLabel(), inputJson, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null) {
            JSONObject object = null;
            String message = "Invalid";
            try {
                object = new JSONObject(response[0]);
                message = object.getString("Message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return message;
        }
        DataError[1] = "can't connect!";
        return null;
    }


    /**
     * return all contents of specific course
     *
     * @param token  current user, dont affect the result bu should be valid
     * @param course the course to get all contents of it
     * @return all contents
     */
    public static List<NewFeed> getContents(String[] DataError, String token, @Nullable Course course, String email) {
        DataError[0] = null;
        DataError[1] = null;
        // TODO: edit this:
        return QueryUtility.parseNewsFeed("{\n" +
                "    \"newsfeed\": [\n" +
                "        {\n" +
                "            \"contentLabel\": \"alda7eee7\",\n" +
                "            \"content_link\": \"https://www.youtube.com/watch?v=OGxgnH8y2NM\",\n" +
                "            \"creationDate\": \"Sat, 16 Dec 2017 23:43:44 GMT\",\n" +
                "            \"creatorDep\": \"D111\",\n" +
                "            \"creatorEmail\": \"followee2@gmail.com\",\n" +
                "            \"creatorFac\": \"F11\",\n" +
                "            \"creatorName\": \"ammar alsayed\",\n" +
                "            \"creatorUni\": \"U1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"contentLabel\": \"cnt2_f2\",\n" +
                "            \"content_link\": \"https://www.youtube.com/watch?v=NjCIq58rZ8I\",\n" +
                "            \"creationDate\": \"Sat, 16 Dec 2017 19:28:33 GMT\",\n" +
                "            \"creatorDep\": \"D111\",\n" +
                "            \"creatorEmail\": \"followee2@gmail.com\",\n" +
                "            \"creatorFac\": \"F11\",\n" +
                "            \"creatorName\": \"ammar alsayed\",\n" +
                "            \"creatorUni\": \"U1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"contentLabel\": \"alda7eee7\",\n" +
                "            \"content_link\": \"https://www.youtube.com/watch?v=VUdN7puocNY\",\n" +
                "            \"creationDate\": \"Sat, 16 Dec 2017 19:28:33 GMT\",\n" +
                "            \"creatorDep\": \"D111\",\n" +
                "            \"creatorEmail\": \"followee2@gmail.com\",\n" +
                "            \"creatorFac\": \"F11\",\n" +
                "            \"creatorName\": \"ammar alsayed\",\n" +
                "            \"creatorUni\": \"U1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"contentLabel\": \"cnt1_f2\",\n" +
                "            \"content_link\": \"https://www.youtube.com/watch?v=-6RHk1pzCSE\",\n" +
                "            \"creationDate\": \"Sat, 16 Dec 2017 19:27:50 GMT\",\n" +
                "            \"creatorDep\": \"D111\",\n" +
                "            \"creatorEmail\": \"followee2@gmail.com\",\n" +
                "            \"creatorFac\": \"F11\",\n" +
                "            \"creatorName\": \"ammar alsayed\",\n" +
                "            \"creatorUni\": \"U1\"\n" +
                "        }\n" +
                "    ]\n" +
                "}");
//        String imuptJson = QueryUtility.unparseCourseNotesInfo(course,email);
//        String jsonResponse;
//        if (course.getType().equals("Course Notes"))
//            // TODO: update urls
//            jsonResponse = getResponse("/getCourseNotes contents",imuptJson, token);
//        else
//            jsonResponse = getResponse("/getSelfCourseContents",imuptJson, token);
//        return QueryUtility.parseNewsFeed(jsonResponse);
    }


    /**
     * get all departments in specific university and specific faculty
     *
     * @param university the university the departments belong to
     * @param faculty    the faculty the departments belong to
     * @return list of all departments name exist in the database and has this university and faculty
     */
    public static ArrayList<String> getDepartmentList(String[] DataError, String university, String faculty) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/departmentsOfFac/" + university + "/" + faculty;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseDepartments(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    /**
     * @param token
     * @return
     */
    public static ArrayList<NewFeed> getNewsFeed(String[] DataError, String token) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/newsfeed";

        String[] response = ServerUtility.getResponse(url, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseNewsFeed(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    /**
     * @return the list of all universities exist in the database
     */
    public static ArrayList<String> getUniversityList(String[] DataError) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/universities";

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseUniversities(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    /**
     * @param university the university the departments belong to
     * @return the list of all faculties exist in specific university
     */
    public static ArrayList<String> getFacultyList(String[] DataError, String university) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/facultiesOfUni/" + university;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseFaculties(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static List<CourseLabel> getCourseList(String[] DataError, String token) {

        String[] response = getResponse("/courseList", token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseCourseList(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static List<CourseLabel> getAllCouresList(String token, String[] DataError) {
        DataError[0] = null;
        DataError[1] = null;
        String[] jsonResponse = getResponse("/allcourseList", token);
        if (jsonResponse[1] != null)
            DataError[0] = QueryUtility.parseMes(jsonResponse[1]);
        if (jsonResponse[0] != null)
            return QueryUtility.parseCourseList(jsonResponse[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static Student getProfile(String[] DataError, String token, @Nullable String email) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/profile";
        if (email != null)
            url += "?email=" + email;

        String[] response = ServerUtility.getResponse(url, token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseStudent(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static String updateStudentData(String token, String[] DataError, String pass, String dep, String name) {
        DataError[0] = null;
        DataError[1] = null;
        JSONObject base = new JSONObject();
        try {
            if (pass != null)
                base.put("password", pass);
            if (dep != null)
                base.put("dep", dep);
            if (name != null)
                base.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // TODO: ASSIGN CORRECT URL
        String url = "/updateStudent";
        String[] response = ServerUtility.getResponse(url, base.toString(), token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static List<Course> getCoursesForSave(String[] DataError, int position, String email) {
        DataError[0] = null;
        DataError[1] = null;
        if (position == 1) {

            String[] response = getResponse("/getSelfStudyCourses/" + email, null);
            if (response[1] != null)
                DataError[0] = QueryUtility.parseMes(response[1]);
            if (response[0] != null)
                return QueryUtility.parseSelfStudyCourse(response[0]);
            DataError[1] = "can't connect!";
            return null;
        }
        String[] response = getResponse("/getCourseNotes/" + email, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseCourseNotes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static String insertvideo(String[] DataError, Course course, String token, String link) {

        DataError[0] = null;
        DataError[1] = null;

        if (course.getType().equals("SelfStudy Course")) {
            String inputJson = QueryUtility.unparseselfStudyCourseVideo(course, link);

            String[] response = getResponse("/insertSelfStudyCourseVideo", inputJson, token);
            if (response[1] != null)
                DataError[0] = QueryUtility.parseMes(response[1]);
            if (response[0] != null)
                return QueryUtility.parseMes(response[0]);
            DataError[1] = "can't connect!";
            return null;
        } else {


            String inputJson = QueryUtility.unparseCourseNotesInfoVideo(course, link);

            String[] response = getResponse("/insertCourseNotesVideo", inputJson, token);
            if (response[1] != null)
                DataError[0] = QueryUtility.parseMes(response[1]);
            if (response[0] != null)
                return QueryUtility.parseMes(response[0]);
            DataError[1] = "can't connect!";
            return null;
        }
    }


    public static String insertSelfStudyCourse(String[] DataError, String token, String selfstudyLabel) {

        DataError[0] = null;
        DataError[1] = null;

        JSONObject base = new JSONObject();

        try {
            base.put("label", "label1");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String[] response = getResponse("/insertSelfStudyCourse", base.toString(), token);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////Ramy
    public static List<Student> searchStudent(String name, String[] DataError) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/searchstudent/" + name;
        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseStudents(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static List<Tag> getTags(String[] DataError) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/gettags";
        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parsetags(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static List<Question> searchQuestionByHeader(String[] DataError, String header) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/searchquestion/" + header;
        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseQuestion(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static List<Question> getQuestionByTag(String[] DataError, String Tag) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/getquestionbytag/" + Tag;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseQuestion(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static List<Question> searchQuestionByTag(String[] DataError, String header, String Tag) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/searchquestionbytag/" + Tag + "/" + header;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseQuestion(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }

    public static String getAskerName(String[] DataError, String askerEmail) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/getstudentbyemail/" + askerEmail;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseStudentName(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static List<Answer> getQuestionAnswers(String[] DataError, String askerEmail, String qcreationdate) {
        DataError[0] = null;
        DataError[1] = null;
        String url = "/getquestionanswers/" + askerEmail + "/" + qcreationdate;

        String[] response = ServerUtility.getResponse(url, null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseAnswers(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    public static String votesAnswer(String[] DataError, String replierEmail, String replingDate, String VoterEmail) {

        DataError[0] = null;
        DataError[1] = null;

//        //String url = "/checkanswervote/"+VoterEmail +"/" +replierEmail+"/" +replingDate;
//        String response = ServerUtility.getResponse(url, null);
//        int size = QueryUtility.getAnswersVotesSize(response);


//        if (size == 0) {
        JSONObject jo = new JSONObject();
        try {
            jo.putOpt("replierEmail", replierEmail);
            jo.putOpt("replingDate", replingDate);
            jo.putOpt("VoterEmail", VoterEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "/voteanswer";

        String[] response = ServerUtility.getResponse(url, jo.toString(), null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    ///////////////////////////////////


    public static String votesQuestion(String[] DataError, String AskerEmail, String CreationDate, String VoterEmail) {
        DataError[0] = null;
        DataError[1] = null;
        JSONObject jo = new JSONObject();
        try {
            jo.putOpt("AskerEmail", AskerEmail);
            jo.putOpt("CreationDate", CreationDate);
            jo.putOpt("VoterEmail", VoterEmail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "/votequestion";

        String[] response = ServerUtility.getResponse(url, jo.toString(), null);
        if (response[1] != null)
            DataError[0] = QueryUtility.parseMes(response[1]);
        if (response[0] != null)
            return QueryUtility.parseMes(response[0]);
        DataError[1] = "can't connect!";
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////Rsmy

}

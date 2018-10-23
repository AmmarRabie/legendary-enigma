package com.android.example.studyStation.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class SaveVideoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText linktext = null;
    ArrayList<String> CourseTypes = null;
    List<Course> courses;
    Spinner courseTypesSpinner, coursesSpinner;
    ArrayAdapter<String> spinner2data;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_video);


        CourseTypes = new ArrayList<String>();

        CourseTypes.add("SelfStudy Course");
        CourseTypes.add("CourseNotes");

        courseTypesSpinner = (Spinner) findViewById(R.id.coursesTypes);
        ArrayAdapter<String> courseTypesAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        courseTypesAdapter.addAll(CourseTypes);
        spinner2data = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item);
        linktext = (EditText) findViewById(R.id.Linktext);
        courseTypesSpinner.setAdapter(courseTypesAdapter);
        coursesSpinner = (Spinner) findViewById(R.id.Courses);
        courseTypesSpinner.setOnItemSelectedListener(this);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInsertTask != null) return;
                if (coursesSpinner.getSelectedView() == null) {
                    Toast.makeText(SaveVideoActivity.this, "You didn't choose Course", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(Patterns.WEB_URL.matcher(linktext.getText().toString()).matches()) || !(linktext.getText().toString().contains("www.youtube")) || !(linktext.getText().toString().contains("v="))) {
                    Toast.makeText(SaveVideoActivity.this, "Didnt add link or wrong link format", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String linkStr = linktext.getText().toString();

                mInsertTask = new AsyncTask<Integer, Void, String>() {
                    @Override
                    protected String doInBackground(Integer... ints) {
                        return Logic.insertvideo(DataError, courses.get(ints[0]), UserInfo.token, linkStr);

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (DataError[0] == null && s == null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (DataError[0] != null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(SaveVideoActivity.this, s, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }.execute(coursesSpinner.getSelectedItemPosition());

            }
        });
    }

    private AsyncTask<Integer, Void, String> mInsertTask = null;
    private AsyncTask<Void, Void, List<Course>> mTask = null;

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long id) {

        String sp1 = String.valueOf(courseTypesSpinner.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if (sp1.contentEquals("SelfStudy Course")) {
            if (mTask == null) {
                mTask = new AsyncTask<Void, Void, List<Course>>() {

                    @Override
                    protected List<Course> doInBackground(Void... voids) {
                        return Logic.getCoursesForSave(DataError, 1, UserInfo.email);
                    }

                    @Override
                    protected void onPostExecute(List<Course> aVoid) {
                        super.onPostExecute(aVoid);
                        mTask = null;

                        if (DataError[0] == null && aVoid == null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (DataError[0] != null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                            return;
                        }

                        spinner2data.clear();
                        for (int i = 0; i < aVoid.size(); i++) {

                            spinner2data.add(aVoid.get(i).getLabel());
                        }
                        coursesSpinner.setAdapter(spinner2data);
                    }
                }.execute();
            }


        } else {

            if (mTask == null) {
                mTask = new AsyncTask<Void, Void, List<Course>>() {

                    @Override
                    protected List<Course> doInBackground(Void... voids) {
                        courses = Logic.getCoursesForSave(DataError, 2, UserInfo.email);
                        return courses;
                    }

                    @Override
                    protected void onPostExecute(List<Course> aVoid) {
                        super.onPostExecute(aVoid);
                        mTask = null;
                        if (DataError[0] == null && aVoid == null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (DataError[0] != null) {
                            Toast.makeText(SaveVideoActivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (int i = 0; i < courses.size(); i++) {

                            spinner2data.add(courses.get(i).getLabel());
                        }
                        coursesSpinner.setAdapter(spinner2data);
                    }
                }.execute();
            }

        }
    }


    public void onNothingSelected(AdapterView<?> arg0) {
    }


    private String[] DataError = {null, null};

}
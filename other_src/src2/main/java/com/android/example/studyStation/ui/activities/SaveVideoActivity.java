package com.android.example.studyStation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;

import java.util.List;


public class SaveVideoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String []CourseTypes={"SelfStudy Course","Course Notes"};
    List<Course>courses;
    Spinner courseTypesSpinner,coursesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_video);

    courseTypesSpinner =(Spinner)findViewById(R.id.coursesTypes);
    coursesSpinner=(Spinner)findViewById(R.id.Courses);
    courseTypesSpinner.setOnItemClickListener(this);
        //Spinner spin = (Spinner) findViewById(R.id.coursesTypes);
        //spin.setOnItemSelectedListener(this);
        //ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,CourseTypes);
        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        //spin.setAdapter(aa);

    }
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long id) {

        String sp1=String.valueOf(courseTypesSpinner.getSelectedItem());
        Toast.makeText(this, sp1, Toast.LENGTH_SHORT).show();
        if(sp1.contentEquals("SelfStudy Course"))
        {
            ArrayAdapter<Course> dataAdapter=Logic.getCoursesForSave(1,UserInfo.email);
        }

        //  Toast.makeText(getApplicationContext(), CourseTypes[position], Toast.LENGTH_LONG).show();
       // courses= Logic.getCoursesForSave(position, UserInfo.email);

        //Spinner spin=(Spinner)findViewById(R.id.Courses);
        //spin.setOnItemSelectedListener(this);
        //ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,courses);

        //aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        //spin.setAdapter(aa);

    }
    public void onNothingSelected(AdapterView<?> arg0) {


    }
    public void insertLink(View view){
        Spinner CourseTypes=(Spinner)findViewById(R.id.coursesTypes);
        Spinner Courses=(Spinner)findViewById(R.id.Courses);


        //Course actualdataofselectedcoursetype="";
        int SelectedSpinnerPositionofCourseTypes=CourseTypes.getSelectedItemPosition();
        int SelectedSpinnerPositionofCourses=Courses.getSelectedItemPosition();

        String actualdataofselectedcourse=(String)actualdataofselectedcourse.getItemAtPosition(SelectedSpinnerPositionofCourses);

    }

}
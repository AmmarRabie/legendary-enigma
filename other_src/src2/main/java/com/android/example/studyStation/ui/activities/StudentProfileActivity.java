package com.android.example.studyStation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.example.studyStation.R;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        // Debug
        Toast.makeText(this, "hello" + getIntent().getExtras().getString("Email"), Toast.LENGTH_LONG).show();
    }
}

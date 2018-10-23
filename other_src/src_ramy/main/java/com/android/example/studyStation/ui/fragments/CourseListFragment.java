package com.android.example.studyStation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.studyStation.R;

/**
 * Created by AmmarRabie on 25/10/2017.
 */

public class CourseListFragment extends Fragment {

    public CourseListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_course_list, container, false);


        return view;
    }
}

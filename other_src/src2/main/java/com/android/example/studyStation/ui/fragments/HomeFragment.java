package com.android.example.studyStation.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.studyStation.R;
import com.android.example.studyStation.ui.activities.LoginActivity;
import com.android.example.studyStation.ui.activities.MainActivity;
import com.android.example.studyStation.ui.activities.PlayCourseActivity;

/**
 * Created by AmmarRabie on 28/09/2017.
 */

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        return view;
    }


}

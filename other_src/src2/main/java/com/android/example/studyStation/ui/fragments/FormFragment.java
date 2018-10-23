package com.android.example.studyStation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.example.studyStation.R;

/**
 * Created by AmmarRabie on 30/09/2017.
 */

public class FormFragment extends Fragment {



    public FormFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form, container, false);




        return view;
    }

}

package com.android.example.studyStation.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.CourseLabel;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.uiSupport.editcourseListMod;

import java.util.List;

/**
 * Created by AmmarRabie on 25/10/2017.
 */

public class EditCourseListFragment extends Fragment {

    public EditCourseListFragment() {
    }

    private RecyclerView mRecyclerView;

    private getCourseListsTask mTask = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_course_list, container, false);

        mRecyclerView = root.findViewById(R.id.fragmentEditCourseList_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        if (mTask == null) {
            mTask = new getCourseListsTask();
            mTask.execute();


        }
        return root;
    }


    class getCourseListsTask extends AsyncTask<Void, Void, List<CourseLabel>> {

        @Override
        protected List<CourseLabel> doInBackground(Void... voids) {
            return Logic.getAllCouresList(UserInfo.token, DataError);
        }

        @Override
        protected void onPostExecute(List<CourseLabel> courseLabels) {
            super.onPostExecute(courseLabels);
            if (DataError[0] == null && courseLabels == null) {
                Toast.makeText(getContext(), DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }
            if (DataError[0] != null) {
                Toast.makeText(getContext(), DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            editcourseListMod adapter = new editcourseListMod(getContext(), courseLabels);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private String[] DataError = {null, null};
}


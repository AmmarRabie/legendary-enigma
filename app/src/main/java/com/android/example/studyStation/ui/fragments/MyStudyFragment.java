package com.android.example.studyStation.ui.fragments;

import android.content.Context;
import android.content.Intent;
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

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.SignupActivity;
import com.android.example.studyStation.ui.uiSupport.MyStudyRecyclerAdapter;

import java.util.List;

import static android.R.attr.description;
import static android.R.attr.label;


public class MyStudyFragment extends Fragment {

    private GetMyStudyTask mTask = null;

    private RecyclerView mRecyclerView;

    private String mEnail = null;

    public MyStudyFragment() {

    }

    private String[] DataError = {null, null};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_study, container, false);
        if (savedInstanceState != null)
            mEnail = getArguments().getString("email", null);
        mRecyclerView = root.findViewById(R.id.fragmentMyStudy_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        if (mTask == null) {
            mTask = new GetMyStudyTask(root.getContext());
            mTask.execute(UserInfo.email);
        }
        return root;
    }


    class GetMyStudyTask extends AsyncTask<String, Void, List<Course>> implements MyStudyRecyclerAdapter.OnItemClickListener {

        private Context mContext;
        private MyStudyRecyclerAdapter mMyStudyRecyclerAdapter = null;

        public GetMyStudyTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected List<Course> doInBackground(String... strings) {
            return Logic.getMyStudyCourses(DataError, UserInfo.token, mEnail);
        }

        protected void onPostExecute(List<Course> returnedCourseList) {
            super.onPostExecute(returnedCourseList);
            mTask = null;

            if (DataError[0] == null && returnedCourseList == null) {
                Toast.makeText(getContext(), DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(getContext(), DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            mMyStudyRecyclerAdapter = new MyStudyRecyclerAdapter(mContext, returnedCourseList);
//            mMyStudyRecyclerAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mMyStudyRecyclerAdapter);
        }


        public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedViewHolder) {
            // if there is a task operates wait and dont perform the click
            if (mTask != null)
                return;
            Intent PlayCourseActivity = new Intent(getContext(), com.android.example.studyStation.ui.activities.PlayCourseActivity.class);
            Course course = ((Course) clickedViewHolder.itemView.getTag());

            PlayCourseActivity.putExtra("course", course);
            PlayCourseActivity.putExtra("email", UserInfo.email);
            // TODO: this email is null
            if (mEnail != null)
                PlayCourseActivity.putExtra("email", mEnail);

            startActivity(PlayCourseActivity);
        }
    }
}
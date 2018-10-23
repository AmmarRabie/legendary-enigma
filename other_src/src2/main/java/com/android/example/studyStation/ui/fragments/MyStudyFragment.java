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

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.PlayCourseActivity;
import com.android.example.studyStation.ui.activities.StudentProfileActivity;
import com.android.example.studyStation.ui.uiSupport.FollowersRecyclerAdapter;
import com.android.example.studyStation.ui.uiSupport.MyStudyRecyclerAdapter;

import java.util.List;


public class MyStudyFragment extends Fragment {

    private GetMyStudyTask mTask = null;

    private RecyclerView mRecyclerView;


    public MyStudyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_study, container, false);

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
            String param = strings[0];
            return Logic.getMyStudyCourses(UserInfo.token);
        }

        protected void onPostExecute(List<Course> returnedCourseList) {
            super.onPostExecute(returnedCourseList);
            mTask = null;
            mMyStudyRecyclerAdapter = new MyStudyRecyclerAdapter(mContext, returnedCourseList);
            mMyStudyRecyclerAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mMyStudyRecyclerAdapter);
        }


        public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedViewHolder) {
            // if there is a task operates wait and dont perform the click
            if (mTask != null)
                return;
            Intent PlayCourseActivity = new Intent(getContext(), com.android.example.studyStation.ui.activities.PlayCourseActivity.class);
            String depName = ((String) clickedViewHolder.itemView.getTag(1));
            String uniName = ((String) clickedViewHolder.itemView.getTag(2));
            String description = ((String) clickedViewHolder.itemView.getTag(3));
            String facName = ((String) clickedViewHolder.itemView.getTag(4));
            String label = ((String) clickedViewHolder.itemView.getTag(5));
            String type = ((String) clickedViewHolder.itemView.getTag(6));
            String creatorEmail=((String)clickedViewHolder.itemView.getTag(7));


            PlayCourseActivity.putExtra("depName",depName );
            PlayCourseActivity.putExtra("uniName", uniName);
            PlayCourseActivity.putExtra("description", description);
            PlayCourseActivity.putExtra("facName", facName);
            PlayCourseActivity.putExtra("label", label);
            PlayCourseActivity.putExtra("type", type);
            PlayCourseActivity.putExtra("creatorEmail",creatorEmail);

            startActivity(PlayCourseActivity);
        }
    }
}

/*
        FollowersRecyclerAdapter adapter = new FollowersRecyclerAdapter(getContext(),null);
        adapter.setOnItemClickListener(new FollowersRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView) {

            }
        });


        if (mTask == null) {
            mTask = new FolloweesFragment.GetFolloweeTask(root.getContext());
            mTask.execute(UserInfo.email);
        }
        return root;




        return view;

    }
}
*/
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

import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.StudentProfileActivity;
import com.android.example.studyStation.ui.uiSupport.FollowersRecyclerAdapter;

import java.util.List;

/**
 * Created by AmmarRabie on 26/10/2017.
 */

public class FolloweesFragment extends Fragment{

    private GetFolloweeTask mTask = null;

    private RecyclerView mRecyclerView;


    public FolloweesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_followers, container, false);

        mRecyclerView = root.findViewById(R.id.fragmentFollowers_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        if (mTask == null) {
            mTask = new GetFolloweeTask(root.getContext());
            mTask.execute(UserInfo.email);
        }
        return root;
    }




    class GetFolloweeTask extends AsyncTask<String, Void, List<Followee>>  implements FollowersRecyclerAdapter.OnItemClickListener {

        private Context mContext;
        private FollowersRecyclerAdapter mFollowersRecyclerAdapter = null;

        public GetFolloweeTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected List<Followee> doInBackground(String... strings) {
            String param = strings[0];
            return Logic.getFollowees(UserInfo.token);
        }

        @Override
        protected void onPostExecute(List<Followee> returnedFoloweeList) {
            super.onPostExecute(returnedFoloweeList);
            mTask = null;
            mFollowersRecyclerAdapter = new FollowersRecyclerAdapter(mContext, returnedFoloweeList);
            mFollowersRecyclerAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mFollowersRecyclerAdapter);
        }


        @Override
        public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedViewHolder) {
            // if there is a task operates wait and dont perform the click
            if (mTask != null)
                return;
            Intent followeeProfileActivity = new Intent(getContext(), StudentProfileActivity.class);
            String email = ((String) clickedViewHolder.itemView.getTag());
            followeeProfileActivity.putExtra("Email", email);
            startActivity(followeeProfileActivity);
        }
    }


}
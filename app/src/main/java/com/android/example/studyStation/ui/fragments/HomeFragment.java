package com.android.example.studyStation.ui.fragments;

import android.content.Context;
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

import com.android.example.studyStation.DefinedData.NewFeed;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.SignupActivity;
import com.android.example.studyStation.ui.uiSupport.NewsFeedRecyclerAdapter;

import java.util.List;

/**
 * Created by AmmarRabie on 28/09/2017.
 */

public class HomeFragment extends Fragment {

    private GetContentsTask mTask = null;

    private RecyclerView mRecyclerView;

    private String[] DataError = {null, null};

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        mRecyclerView = view.findViewById(R.id.fragmentNewsFeed_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mTask == null && mRecyclerView.getAdapter() == null) {
            mTask = new GetContentsTask(view.getContext());
            mTask.execute((Void) null);
        }
        return view;
    }


    private class GetContentsTask extends AsyncTask<Void, Void, List<NewFeed>> {

        private NewsFeedRecyclerAdapter mNewsFeedsRecyclerAdapter = null;
        private Context myContext;

        public GetContentsTask(Context context) {
            myContext = context;
        }

        @Override
        protected List<NewFeed> doInBackground(Void... voids) {
            return Logic.getNewsFeed(DataError, UserInfo.token);
        }

        @Override
        protected void onPostExecute(List<NewFeed> returnedNewsFeedsList) {
            super.onPostExecute(returnedNewsFeedsList);
            mTask = null;

            if (DataError[0] == null && returnedNewsFeedsList == null) {
                Toast.makeText(getContext(), DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(getContext(), DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            mNewsFeedsRecyclerAdapter = new NewsFeedRecyclerAdapter(myContext, returnedNewsFeedsList);
            mRecyclerView.setAdapter(mNewsFeedsRecyclerAdapter);
        }

    }


}

package com.android.example.studyStation.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.NewFeed;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.uiSupport.CourseContentsRecyclerAdapter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import static android.R.attr.label;

public class PlayCourseActivity extends YouTubeBaseActivity {


    private String creatorEmail;
    private Course course;
    private GetContentsTask mTask = null;
    private YouTubePlayer mPlayer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_course);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.activityPlayCourse_yourupePlayer);
        recyclerView = findViewById(R.id.activityPlayCourse_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        youTubePlayerView.initialize(getString(R.string.Youtube_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mPlayer = youTubePlayer;
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
        creatorEmail = getIntent().getExtras().getString("email");
        course = ((Course) getIntent().getExtras().getSerializable("course"));
        setTitle(course.getLabel());
        if (course.getType().equals("Course Notes"))
            ;
        Toast.makeText(this, creatorEmail + "\n" + label, Toast.LENGTH_SHORT).show();
        if (mTask != null) return;
        mTask = new GetContentsTask(this);
        mTask.execute();

    }


    private class GetContentsTask extends AsyncTask<Void, Void, List<NewFeed>> {

        private CourseContentsRecyclerAdapter mContentsRecyclerAdapter = null;
        private Context myContext;
        private String[] DataError ={null,null};

        public GetContentsTask(Context context) {
            myContext = context;
        }

        @Override
        protected List<NewFeed> doInBackground(Void... voids) {
            return Logic.getContents(DataError, UserInfo.token, course, creatorEmail);
        }

        @Override
        protected void onPostExecute(final List<NewFeed> returnedNewsFeedsList) {
            super.onPostExecute(returnedNewsFeedsList);
            mTask = null;
            if (DataError[0] == null && returnedNewsFeedsList == null) {
                Toast.makeText(PlayCourseActivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(PlayCourseActivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            mContentsRecyclerAdapter = new CourseContentsRecyclerAdapter(myContext, returnedNewsFeedsList);
            mContentsRecyclerAdapter.setOnItemClickListener(new CourseContentsRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView) {
                    String vLnik = (String) clickedView.itemView.getTag();
//                    mPlayer.release();
                    mPlayer.loadVideo(vLnik);
                    returnedNewsFeedsList.get(positionInTheList);
                }
            });
            recyclerView.setAdapter(mContentsRecyclerAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
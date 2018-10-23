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
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.QuestionActivity;
import com.android.example.studyStation.ui.activities.SearchActivity;
import com.android.example.studyStation.ui.activities.SignupActivity;
import com.android.example.studyStation.ui.activities.StudentProfileActivity;
import com.android.example.studyStation.ui.uiSupport.FollowersRecyclerAdapter;
import com.android.example.studyStation.ui.uiSupport.StudentRecyclerAdapter;
import com.android.example.studyStation.ui.uiSupport.QuestionRecyclerAdapter;

/**
 * Created by AmmarRabie on 25/10/2017.
 */

public class SearchResultFragment extends Fragment {

    private SearchStudentTask mTask = null;
    private SearchQuestionTask qTask = null;
    RecyclerView Rv;
    List<Student> studentList;

    public SearchResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search_results, container, false);

        Rv = root.findViewById(R.id.fragmentSearchResult_recyclerView);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(root.getContext()));
        Rv.setItemAnimator(new DefaultItemAnimator());


        Bundle arg = getArguments();

        String searchInput = arg.getString("SearchInput");
        String type = arg.getString("SearchType");
        if (type.equals("StudentSearch")) {
            if (mTask == null) {
                mTask = new SearchStudentTask(root.getContext());
                mTask.execute(searchInput);
            }
        } else {
            if (qTask == null) {
                qTask = new SearchQuestionTask(root.getContext());
                qTask.execute(searchInput, type);
            }
        }
        //Bundle arg=getArguments();

        // String name=arg.getString("SearchInput");
        // studentList = Logic.searchStudent(name);

        //Student ramt = new Student();
        //ramt.setName("ramy");
        //ramt.setEmail("ramy.m.saied@hotmail.com");
        //ls.add(ramt);
        //a = new StudentRecyclerAdapter(studentList, this.getContext());
        // Rv.setAdapter(a);

        return root;
    }


    private String[] DataError = {null, null};
    private String[] DataError2 = {null, null};

    class SearchStudentTask extends AsyncTask<String, Void, List<Student>> implements StudentRecyclerAdapter.OnItemClickListener {

        private Context mContext;
        private StudentRecyclerAdapter mStudentRecyclerAdapter = null;

        public SearchStudentTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected List<Student> doInBackground(String... strings) {
            String param = strings[0];
            return Logic.searchStudent(param, DataError);
        }

        @Override
        protected void onPostExecute(List<Student> returnedStudentList) {
            super.onPostExecute(returnedStudentList);
            mTask = null;

            if (DataError[0] == null && returnedStudentList == null) {
                Toast.makeText(getContext(), DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(getContext(), DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            if (returnedStudentList.size() == 0) {

                ((SearchActivity) getActivity()).setNoResult();
                return;
            }
            mStudentRecyclerAdapter = new StudentRecyclerAdapter(returnedStudentList, mContext);
            mStudentRecyclerAdapter.setOnItemClickListener(this);
            Rv.setAdapter(mStudentRecyclerAdapter);
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

    class SearchQuestionTask extends AsyncTask<String, Void, List<Question>> implements QuestionRecyclerAdapter.OnItemClickListener {

        private Context mContext;
        private QuestionRecyclerAdapter mQuestionRecyclerAdapter = null;

        public SearchQuestionTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected List<Question> doInBackground(String... strings) {
            String sInput = strings[0];
            String type = strings[1];

            if (type.equals("QuestionByHeader")) {
                return Logic.searchQuestionByHeader(DataError2, sInput);
            } else {
                if (sInput.equals("")) {
                    return Logic.getQuestionByTag(DataError2, type);
                } else {
                    return Logic.searchQuestionByTag(DataError2, sInput, type);
                }

            }
        }

        @Override
        protected void onPostExecute(List<Question> returnedStudentList) {
            super.onPostExecute(returnedStudentList);
            qTask = null;

            if (DataError2[0] == null && returnedStudentList == null) {
                Toast.makeText(getContext(), DataError2[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError2[0] != null) {
                Toast.makeText(getContext(), DataError2[0], Toast.LENGTH_SHORT).show();
                return;
            }

            if (returnedStudentList.size() == 0) {
                ((SearchActivity) getActivity()).setNoResult();
                return;
            }
            mQuestionRecyclerAdapter = new QuestionRecyclerAdapter(returnedStudentList, mContext);
            mQuestionRecyclerAdapter.setOnItemClickListener(this);
            Rv.setAdapter(mQuestionRecyclerAdapter);
        }


        @Override
        public void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedViewHolder) {
            // if there is a task operates wait and dont perform the click
            if (qTask != null)
                return;
            Intent QuestionActivity = new Intent(getContext(), com.android.example.studyStation.ui.activities.QuestionActivity.class);

            Bundle Para = new Bundle();
            Para.putString("AskerEmail", (((QuestionRecyclerAdapter.QuestionViewHolder) clickedViewHolder).questionAskerEmail.getText().toString()));
            Para.putString("CreationDate", (((QuestionRecyclerAdapter.QuestionViewHolder) clickedViewHolder).questionCreationDate.getText().toString()));
            Para.putString("QuestionHeader", (((QuestionRecyclerAdapter.QuestionViewHolder) clickedViewHolder).questionHeader.getText().toString()));
            QuestionActivity.putExtras(Para);

            startActivity(QuestionActivity);
            Toast.makeText(getContext(), "Item Clicked", Toast.LENGTH_LONG).show();
        }
    }
}
package com.android.example.studyStation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Answer;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.Tag;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.fragments.DoSearchFragment;
import com.android.example.studyStation.ui.uiSupport.AnswerRecyclerAdapter;
import com.android.example.studyStation.ui.uiSupport.QuestionRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static java.security.AccessController.getContext;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    TextView AskerEmail;

    TextView CreationDate;

    TextView QuestionContetent;

    TextView AskerName;

    Button voteQuestionButton;

    VoteQuestionTask voteQuesTask = null;


    AnswerRecyclerAdapter mAnswerRecyclerAdapter = null;
    RecyclerView Rv;
    getAskerNameTask NameTask = null;
    getAnswersTask AnswersTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        /// get id of the xml component
        AskerName = (TextView) findViewById(R.id.activity_question_askername);

        CreationDate = (TextView) findViewById(R.id.activity_question_QCreationDate);

        QuestionContetent = (TextView) findViewById(R.id.activity_question_header);

        AskerEmail = (TextView) findViewById(R.id.activity_question_AskerEmail);

        voteQuestionButton = (Button) findViewById(R.id.activity_question_vote);

        voteQuestionButton.setOnClickListener(this);

        Rv = (RecyclerView) findViewById(R.id.activity_question_recyclerView);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this));
        Rv.setItemAnimator(new DefaultItemAnimator());

        ///////////////////////////////////////////////////////

        Bundle Para = getIntent().getExtras();


        AskerEmail.setText(Para.getString("AskerEmail"));
        CreationDate.setText(Para.getString("CreationDate"));
        QuestionContetent.setText(Para.getString("QuestionHeader"));

        if (NameTask == null) {
            NameTask = new getAskerNameTask(this);
        }
        NameTask.execute(Para.getString("AskerEmail"));


        if (AnswersTask == null) {
            AnswersTask = new getAnswersTask(this);
        }
        AnswersTask.execute(Para.getString("AskerEmail"), Para.getString("CreationDate"));

    }

    private class getAskerNameTask extends AsyncTask<String, Void, String> {
        private Context mContext;

        public getAskerNameTask(Context mContext) {
            this.mContext = mContext;
        }

        protected String doInBackground(String... params) {
            String askerEmail = params[0];
            return Logic.getAskerName(DataErrorNameTask, askerEmail);
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            NameTask = null;

            if (DataErrorNameTask[0] == null && result == null) {
                Toast.makeText(QuestionActivity.this, DataErrorNameTask[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataErrorNameTask[0] != null) {
                Toast.makeText(QuestionActivity.this, DataErrorNameTask[0], Toast.LENGTH_SHORT).show();
                return;
            }
            AskerName.setText(result);
        }
    }


    public void onClick(View v) {

        if (voteQuesTask == null) {
            voteQuesTask = new VoteQuestionTask(this);

            voteQuesTask.execute(AskerEmail.getText().toString(), CreationDate.getText().toString(), "ramy@gh.edu.eg");
        }

    }

    private class getAnswersTask extends AsyncTask<String, Void, List<Answer>> {
        private Context mContext;

        public getAnswersTask(Context mContext) {
            this.mContext = mContext;
        }

        protected List<Answer> doInBackground(String... params) {
            String askerEmail = params[0];
            String Qcreationdate = params[1];

            SimpleDateFormat simpleFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
            simpleFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            ParsePosition startPos = new ParsePosition(0);
            Date parsedDate = simpleFormatter.parse(Qcreationdate, startPos);

            SimpleDateFormat simplerFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            simplerFormatter2.setTimeZone(TimeZone.getTimeZone("GMT"));

            String d = simplerFormatter2.format(parsedDate);  // transform the given format to sql data format

            return Logic.getQuestionAnswers(DataErrorAnsTask, askerEmail, d);
        }


        protected void onPostExecute(List<Answer> result) {
            super.onPostExecute(result);
            NameTask = null;

            if (DataErrorAnsTask[0] == null && result == null) {
                Toast.makeText(QuestionActivity.this, DataErrorAnsTask[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataErrorAnsTask[0] != null) {
                Toast.makeText(QuestionActivity.this, DataErrorAnsTask[0], Toast.LENGTH_SHORT).show();
                return;
            }
            mAnswerRecyclerAdapter = new AnswerRecyclerAdapter(result, mContext);
            Rv.setAdapter(mAnswerRecyclerAdapter);
        }
    }

    private class VoteQuestionTask extends AsyncTask<String, Void, String> {
        private Context mContext;

        public VoteQuestionTask(Context mContext) {
            this.mContext = mContext;
        }

        protected String doInBackground(String... params) {
            String AskerEmail = params[0];
            String CreationDate = params[1];
            String VoterEmail = params[2];

            //    Toast.makeText(mContext, "in voteAnswer Task Before vote!",Toast.LENGTH_LONG).show();

            SimpleDateFormat simpleFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
            simpleFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            ParsePosition startPos = new ParsePosition(0);
            Date parsedDate = simpleFormatter.parse(CreationDate, startPos);

            SimpleDateFormat simplerFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            simplerFormatter2.setTimeZone(TimeZone.getTimeZone("GMT"));

            String d = simplerFormatter2.format(parsedDate);  // transform the given format to sql data format


            return Logic.votesQuestion(DataErrorVoteTask, AskerEmail, d, VoterEmail);

            // Toast.makeText(mContext, "vote Sucess !",Toast.LENGTH_LONG).show();


        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            voteQuesTask = null;

            if (DataErrorVoteTask[0] == null && result == null) {
                Toast.makeText(QuestionActivity.this, DataErrorVoteTask[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataErrorVoteTask[0] != null) {
                Toast.makeText(QuestionActivity.this, DataErrorVoteTask[0], Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        }
    }

    private String[] DataErrorNameTask = {null,null};
    private String[] DataErrorAnsTask = {null,null};
    private String[] DataErrorVoteTask = {null,null};



    public void answerclick  (View view)
    {
        Intent intent = new Intent(this,InsertAnswerACtivity.class);
        intent.putExtra("question", new Question(AskerEmail.getText().toString(),this.CreationDate.getText().toString(),QuestionContetent.getText().toString()));
        startActivity(intent);
    }

}

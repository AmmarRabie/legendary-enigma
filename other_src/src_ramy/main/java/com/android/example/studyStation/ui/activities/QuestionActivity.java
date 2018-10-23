package com.android.example.studyStation.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Answer;
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

public class QuestionActivity extends AppCompatActivity {

    TextView AskerEmail;

    TextView CreationDate;

    TextView QuestionContetent;

    TextView AskerName;

    AnswerRecyclerAdapter mAnswerRecyclerAdapter=null;
    RecyclerView Rv;
    getAskerNameTask NameTask=null;
    getAnswersTask   AnswersTask=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

         /// get id of the xml component
        AskerName=(TextView)findViewById(R.id.activity_question_askername);

        CreationDate=(TextView)findViewById(R.id.activity_question_QCreationDate);

        QuestionContetent=(TextView)findViewById(R.id.activity_question_header);

        AskerEmail=(TextView)findViewById(R.id.activity_question_AskerEmail);

        Rv = (RecyclerView)findViewById(R.id.activity_question_recyclerView);
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this));
        Rv.setItemAnimator(new DefaultItemAnimator());

        ///////////////////////////////////////////////////////

        Bundle Para=getIntent().getExtras();


        AskerEmail.setText(Para.getString("AskerEmail"));
        CreationDate.setText(Para.getString("CreationDate"));
        QuestionContetent.setText(Para.getString("QuestionHeader"));

        if (NameTask == null) {
            NameTask = new QuestionActivity.getAskerNameTask(this);
        }
        NameTask.execute(Para.getString("AskerEmail"));


        if (AnswersTask==null)
        {
            AnswersTask = new QuestionActivity.getAnswersTask(this);
        }
        AnswersTask.execute(Para.getString("AskerEmail"),Para.getString("CreationDate"));

    }

    private class getAskerNameTask extends AsyncTask<String, Void, String> {
        private Context mContext;

        public getAskerNameTask(Context mContext) {
            this.mContext = mContext;
        }

        protected String doInBackground(String... params)
        {
            String askerEmail=params[0];
            return Logic.getAskerName(askerEmail);
        }


        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            NameTask = null;

            AskerName.setText(result);
        }
    }

    private class getAnswersTask extends AsyncTask<String, Void, List<Answer>> {
        private Context mContext;

        public getAnswersTask(Context mContext) {
            this.mContext = mContext;
        }

        protected List<Answer> doInBackground(String... params)
        {
            String askerEmail=params[0];
            String Qcreationdate=params[1];

            SimpleDateFormat simpleFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
            simpleFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            ParsePosition startPos = new ParsePosition(0);
            Date parsedDate = simpleFormatter.parse(Qcreationdate, startPos);

            SimpleDateFormat simplerFormatter2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            simplerFormatter2.setTimeZone(TimeZone.getTimeZone("GMT"));

            String d=simplerFormatter2.format(parsedDate);  // transform the given format to sql data format

            return Logic.getQuestionAnswers(askerEmail,d);
        }


        protected void onPostExecute(List<Answer> result) {

            super.onPostExecute(result);
            NameTask = null;

            mAnswerRecyclerAdapter = new AnswerRecyclerAdapter( result,mContext);
            Rv.setAdapter(mAnswerRecyclerAdapter);
        }
    }
}

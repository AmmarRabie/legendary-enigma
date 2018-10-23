package com.android.example.studyStation.ui.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Answer;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.uiSupport.AnswerRecyclerAdapter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ramym on 12/21/2017.
 */

public class InsertAnswerACtivity extends AppCompatActivity  implements   View.OnClickListener{

    EditText insert_edit;

    InsertAnswerTask insAnsTask=null;

    String CreationDate;
    String AskerEmail;
    String replierEmail;
    String Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_answer);
        /// get id of the xml component
        insert_edit=(EditText) findViewById(R.id.INSERT_ANSWER_EDIT_TEXT);

        ///////////////////////////////////////////////////////

        Bundle Para=getIntent().getExtras();



        String AskerEmail=Para.getString("AskerEmail");
        String CreationDate=(Para.getString("CreationDate"));
        String replierEmail=(Para.getString("replierEmail"));
        String Content=Para.getString("Content");  ;


    }



    public void onClick(View v) {

        Question question = ((Question) getIntent().getExtras().getSerializable("question"));
        if (insAnsTask==null)

        {
            insAnsTask = new InsertAnswerTask(this);
        }
        insAnsTask.execute(AskerEmail,CreationDate,replierEmail,question.getHeader());


    }




    private class InsertAnswerTask extends AsyncTask<String, Void,String> {
        private Context mContext;

        public InsertAnswerTask(Context mContext) {
            this.mContext = mContext;
        }

        protected String doInBackground(String... params)
        {
            String AskerEmail= params[0];   // of the question
            String CreationDate= params[1];
            String replierEmail=params[2];
            String Content=params[3];


            SimpleDateFormat simpleFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
            simpleFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            ParsePosition startPos = new ParsePosition(0);
            Date parsedDate = simpleFormatter.parse(CreationDate, startPos);

            SimpleDateFormat simplerFormatter2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            simplerFormatter2.setTimeZone(TimeZone.getTimeZone("GMT"));

            parsedDate = simpleFormatter.parse(CreationDate, startPos);

            String QuestionCreationDate=simplerFormatter2.format(parsedDate);  // transform the given format to sql data format


            return  Logic.insertAnswer(DataError, UserInfo.token,new Answer(AskerEmail,CreationDate,Content,replierEmail,"sd"));




        }


        protected void onPostExecute(String result)
        {

            super.onPostExecute(result);
            insAnsTask = null;

            if (DataError[0] == null && result == null) {
                Toast.makeText(InsertAnswerACtivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(InsertAnswerACtivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }

             Toast.makeText(mContext, "insert answer  !",Toast.LENGTH_LONG).show();
        }
    }

    String[] DataError = null;

}

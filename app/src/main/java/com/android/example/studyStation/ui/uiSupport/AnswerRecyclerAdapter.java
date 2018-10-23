package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Answer;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.QuestionActivity;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ramym on 12/15/2017.
 */

public class AnswerRecyclerAdapter extends RecyclerView.Adapter<AnswerRecyclerAdapter.AnswerViewHolder> {


    private List<Answer> mAnswerList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    VoteAnswerTask voteAnsTask = null;

    public interface OnItemClickListener {

        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }


    public AnswerRecyclerAdapter(List<Answer> list, Context context) {
        // this.layout=LayoutInflater.from(context);
        this.mContext = context;
        this.mAnswerList = list;
    }

    @Override
    public AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);

        return new AnswerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AnswerViewHolder holder, int position) {

        final Answer item = mAnswerList.get(position);

        holder.answerContent.setText(item.getContent());
        holder.ReplyierEmail.setText(item.getReplyierEmail());
        holder.ReplyingDate.setText(item.getReplyingdate());

        holder.askerEmail = item.getAskerEmail();
        holder.qCreationDate = item.getCreationdate();

        if (!holder.voteButton.callOnClick())
            holder.voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "you clicked vote this answer ", Toast.LENGTH_SHORT).show();

                    if (voteAnsTask == null) {
                        voteAnsTask = new VoteAnswerTask(mContext);
                        String ReplyierEmail = item.getReplyierEmail();
                        String ReplyingDate = item.getReplyingdate();
                        voteAnsTask.execute(ReplyierEmail, ReplyingDate, "ramy@gh.edu.eg");

                    }
                }
            });

    }


    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    public class AnswerViewHolder extends RecyclerView.ViewHolder {

        public TextView answerContent;
        public TextView ReplyingDate;
        public TextView ReplyierEmail;
        public Button voteButton;

        public String askerEmail;
        public String qCreationDate;
        private View container;

        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public AnswerViewHolder(View itemView) {
            super(itemView);

            answerContent = itemView.findViewById(R.id.answerLayout_answerContent);
            ReplyingDate = itemView.findViewById(R.id.answerLayout_replyingDate);
            ReplyierEmail = itemView.findViewById(R.id.answerLayout_replyierEmail);
            voteButton = itemView.findViewById(R.id.answerLayout_vote);

        }

    }

    private class VoteAnswerTask extends AsyncTask<String, Void, String> {
        private Context mContext;

        public VoteAnswerTask(Context mContext) {
            this.mContext = mContext;
        }

        protected String doInBackground(String... params) {
            String replierEmail = params[0];
            String replingDate = params[1];
            String VoterEmail = params[2];

            //    Toast.makeText(mContext, "in voteAnswer Task Before vote!",Toast.LENGTH_LONG).show();

            SimpleDateFormat simpleFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
            simpleFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            ParsePosition startPos = new ParsePosition(0);
            Date parsedDate = simpleFormatter.parse(replingDate, startPos);

            SimpleDateFormat simplerFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            simplerFormatter2.setTimeZone(TimeZone.getTimeZone("GMT"));

            String d = simplerFormatter2.format(parsedDate);  // transform the given format to sql data format


            return Logic.votesAnswer(DataError, replierEmail, d, VoterEmail);

            // Toast.makeText(mContext, "vote Sucess !",Toast.LENGTH_LONG).show();


        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            voteAnsTask = null;

            if (DataError[0] == null && result == null) {
                Toast.makeText(mContext, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(mContext, DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
        }
    }

    private String[] DataError = {null, null};

}

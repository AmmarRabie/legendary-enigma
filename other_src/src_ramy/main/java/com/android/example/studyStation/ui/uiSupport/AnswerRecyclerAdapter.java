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

import java.util.List;

/**
 * Created by ramym on 12/15/2017.
 */

public class AnswerRecyclerAdapter extends RecyclerView.Adapter<AnswerRecyclerAdapter.AnswerViewHolder> {


    private List<Answer> mAnswerList;
    private Context mContext;

    private AnswerRecyclerAdapter.OnItemClickListener mOnItemClickListener = null;

    VoteAnswerTask voteAnsTask=null;

    public interface OnItemClickListener {

        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }


    public AnswerRecyclerAdapter(List<Answer> list, Context context) {
        // this.layout=LayoutInflater.from(context);
        this.mContext = context;
        this.mAnswerList = list;
    }

    @Override
    public AnswerRecyclerAdapter.AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);

        return new AnswerRecyclerAdapter.AnswerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AnswerRecyclerAdapter.AnswerViewHolder holder, int position) {

        Answer item = mAnswerList.get(position);

        holder.answerContent.setText(item.getContent());
        holder.ReplyierEmail.setText(item.getReplyierEmail());
        holder.ReplyingDate.setText(item.getReplyingdate());

        holder.askerEmail=item.getAskerEmail();
        holder.qCreationDate=item.getCreationdate();

        if (!holder.voteButton.callOnClick())
            holder.voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Enter some words to search about it ", Toast.LENGTH_LONG).show();


                }
            });

    }


    @Override
    public int getItemCount() {
        return mAnswerList.size();
    }

    public void setOnItemClickListener(AnswerRecyclerAdapter.OnItemClickListener listener) {
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

    private class VoteAnswerTask extends AsyncTask<String, Void,Void> {
        private Context mContext;

        public VoteAnswerTask(Context mContext) {
            this.mContext = mContext;
        }

        protected Void doInBackground(String... params)
        {
            String replierEmail= params[0];
            String replingDate= params[1];
            String VoterEmail=params[2];


           Logic.votesAnswer(replierEmail,replingDate,VoterEmail);
            return null;
        }


        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            voteAnsTask = null;

        }
    }

}

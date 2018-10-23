package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.Question;
import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.R;

import java.util.List;

/**
 * Created by ramym on 12/9/2017.
 */

public class QuestionRecyclerAdapter extends RecyclerView.Adapter<QuestionRecyclerAdapter.QuestionViewHolder>{


    private List<Question> mQuestionList;
    private Context mContext;

    private QuestionRecyclerAdapter.OnItemClickListener mOnItemClickListener = null;
    // private LayoutInflater layout;

    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }

    public QuestionRecyclerAdapter(List<Question> list, Context context)
    {
        // this.layout=LayoutInflater.from(context);
        this.mContext=context;
        this.mQuestionList=list;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {

        holder.bind(position);


    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public void setOnItemClickListener(QuestionRecyclerAdapter.OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
    public class QuestionViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        public TextView questionHeader;
        public TextView questionCreationDate ;
        public TextView questionAskerEmail;

        private View container;


        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public QuestionViewHolder(View itemView) {
            super(itemView);

            questionHeader = itemView.findViewById(R.id.questionLayout_QuestionHeader);
            questionCreationDate = itemView.findViewById(R.id.questionLayout_CreationDate);
            questionAskerEmail = itemView.findViewById(R.id.questionLayout_AskerEmail);
        }

        void bind(final int position) {

            Question item =mQuestionList.get(position);

            questionAskerEmail.setText(item.getAskerEmail());
            questionCreationDate.setText(item.getcreationDate());
            questionHeader.setText(item.getHeader());

            if (mOnItemClickListener != null && !itemView.callOnClick()) {

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(position,QuestionRecyclerAdapter.QuestionViewHolder.this);
                    }
                });
            }
        }

    }
}

package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.R;

import java.util.List;

/**
 * Created by ramym on 12/9/2017.
 */

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder>{


    private List<Student> mStudentList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;
   // private LayoutInflater layout;

    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }

    public StudentRecyclerAdapter(List<Student> list, Context context)
    {
       // this.layout=LayoutInflater.from(context);
        this.mContext=context;
        this.mStudentList=list;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);

        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {

        Student item =mStudentList.get(position);

        holder.studentName.setText(item.getName());
        holder.studentUni.setText(item.getUni());
        holder.studentDep.setText(item.getDep());
        holder.studentFac.setText(item.getFac());
        holder.studentEmail.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
   public class StudentViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        public TextView studentName;
        public TextView studentDep ;
        public TextView studentFac;
        public TextView studentUni;
        public TextView studentEmail;

        private View container;


        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public StudentViewHolder(View itemView) {
            super(itemView);

            studentName = itemView.findViewById(R.id.studentLayout_name);
            studentDep = itemView.findViewById(R.id.studentLayout_depName);
            studentFac = itemView.findViewById(R.id.studentLayout_facName);
            studentUni = itemView.findViewById(R.id.studentLayout_uniName);
            studentEmail = itemView.findViewById(R.id.studenteLayout_email);
        }


     /*   void bind(final int position) {

            Student std = mStudentList.get(position);

            studentName.setText(std.getName());
            studentDep.setText(std.getDep());
            studentFac.setText(std.getFac());
            studentUni.setText(std.getUni());
            studentEmail.setText(std.getEmail());

            if (mOnItemClickListener != null && !itemView.callOnClick()) {

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(position,FollowersRecyclerAdapter.FolloweeViewHolder.this);
                    }
                });
            }
        }
     */

    }
}

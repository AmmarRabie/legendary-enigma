package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.CourseLabel;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.R;

import java.util.List;

/**
 * Created by Loai Ali on 12/16/2017.
 */

public class courseListRecyclerAdapter extends RecyclerView.Adapter<courseListRecyclerAdapter.courseListViewHolder> {

    private List<CourseLabel> mCourseList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;
    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }
    public courseListRecyclerAdapter(Context mContext, List<CourseLabel> courseList) {
        this.mContext = mContext;
        this.mCourseList = courseList;
    }

    public courseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: inflate you own layout of each item
        // Inflate the followee_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.courselist_layout, parent, false);

        return new courseListViewHolder(view);
    }

    public void onBindViewHolder(courseListViewHolder holder, int position) {



        CourseLabel ramy=mCourseList.get(position);

        holder.description.setText(ramy.getDescription());

        holder.label.setText(ramy.getLabel());
        holder.code.setText(ramy.getCode());

    }

    public int getItemCount() {
        return mCourseList.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    class courseListViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        TextView  code;
        TextView description;
        TextView  label;
        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public courseListViewHolder(View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.code);
            description = itemView.findViewById(R.id.desription);
            label = itemView.findViewById(R.id.CourseName);
        }


        void bind(final int position) {

            CourseLabel course = mCourseList.get(position);

            code.setText(course.getCode());
            description.setText(course.getDescription());
            label.setText(course.getLabel());

            if (mOnItemClickListener != null && !itemView.callOnClick()) {

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(position,courseListViewHolder.this);
                    }
                });
            }
        }


    }
}

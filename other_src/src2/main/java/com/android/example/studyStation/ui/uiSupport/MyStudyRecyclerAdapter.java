package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Course;
import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;

import java.util.List;

/**
 * Created by Loai Ali on 12/9/2017.
 */

public class MyStudyRecyclerAdapter extends RecyclerView.Adapter<MyStudyRecyclerAdapter.CourseViewHolder> {

    private List<Course> CourseList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }


    /*
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     * @param followeeList the data to inflate to View
     */
    public MyStudyRecyclerAdapter(Context mContext, List<Course> courselist) {
        this.mContext = mContext;
        this.CourseList = courselist;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FolloweeViewHolder that holds the view for each item
     */
    @Override
    public MyStudyRecyclerAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: inflate you own layout of each item
        // Inflate the followee_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.mystudy_layout, parent, false);

        return new MyStudyRecyclerAdapter.CourseViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(MyStudyRecyclerAdapter.CourseViewHolder holder, int position) {

       /* // TODO: here is the logic how you want to display your data


        return root;*/


        final Course newcourse = CourseList.get(position);

        holder.depName.setText(newcourse.getDepartmentName());

        holder.description.setText(newcourse.getDescription());
        holder.uniName.setText(newcourse.getUnivesityName());
        holder.facName.setText(newcourse.getFacultyName());
        holder.label.setText(newcourse.getLabel());

        holder.itemView.setTag(1, newcourse.getDepartmentName());
        holder.itemView.setTag(2, newcourse.getUnivesityName());
        holder.itemView.setTag(3, newcourse.getDescription());
        holder.itemView.setTag(4, newcourse.getFacultyName());
        holder.itemView.setTag(5, newcourse.getLabel());
        holder.itemView.setTag(6, newcourse.getType());

        if (!holder.deleteButton.callOnClick())
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... voids) {
                            if(newcourse.getType()=="Self Study")
                            return Logic.deleteSelfStudyCourse(UserInfo.token,newcourse);
                            else  return Logic.deleteCourseNotes(UserInfo.token,newcourse);
                        }
                    };
                }
            });

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return CourseList.size();
    }


    public void setOnItemClickListener(MyStudyRecyclerAdapter.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
    class CourseViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        TextView depName;
        TextView uniName;
        TextView facName;
        TextView description;
        TextView label;
        TextView type;

        ImageView deleteButton;

        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public CourseViewHolder(View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.Courselabel);
            depName = itemView.findViewById(R.id.Coursedep);
            uniName = itemView.findViewById(R.id.CourseUni);
            facName = itemView.findViewById(R.id.Coursefac);
            description = itemView.findViewById(R.id.Coursedescription);

            deleteButton = itemView.findViewById(R.id.mystudy_layout_imageDelete);
        }


        void bind(final int position) {

            Course course = CourseList.get(position);

            label.setText(course.getLabel());
            depName.setText(course.getDepartmentName());
            uniName.setText(course.getUnivesityName());
            facName.setText(course.getFacultyName());
            description.setText(course.getDescription());
            type.setText(course.getType());

            if (mOnItemClickListener != null && !itemView.callOnClick()) {

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(position, MyStudyRecyclerAdapter.CourseViewHolder.this);
                    }
                });
            }
        }


    }
}

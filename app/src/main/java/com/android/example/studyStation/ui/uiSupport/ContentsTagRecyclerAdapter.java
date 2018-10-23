package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.CourseNotes;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.PlayCourseActivity;
import com.android.example.studyStation.ui.activities.QuestionActivity;

import java.util.List;

/**
 * Created by Loai Ali on 12/9/2017.
 */

public class ContentsTagRecyclerAdapter extends RecyclerView.Adapter<ContentsTagRecyclerAdapter.CourseViewHolder> {

    private List<CourseNotes> CourseList;
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
    public ContentsTagRecyclerAdapter(Context mContext, List<CourseNotes> courselist) {
        this.mContext = mContext;
        this.CourseList = courselist;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FolloweeViewHolder that holds the view for each item
     */
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: inflate you own layout of each item
        // Inflate the followee_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.contents_tag_layout, parent, false);

        return new CourseViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(final CourseViewHolder holder, final int position) {

        holder.bind(position);
        final CourseNotes newcourse = CourseList.get(position);

        holder.itemView.setTag(newcourse);


        if (mOnItemClickListener != null && !holder.itemView.callOnClick()) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mOnItemClickListener.onItemClick(position, holder);
                    Intent playcoursenoteActivity = new Intent(mContext, PlayCourseActivity.class);
                    mContext.startActivity(playcoursenoteActivity);
                }
            });
        }
    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return CourseList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
    class CourseViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        TextView uniName;
        TextView facName;
        TextView label;
        TextView tag;
        TextView menuMore;
        TextView email;

        AsyncTask<Void, Void, String> deleteTask = null;

        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public CourseViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.contentsTag_Courselabel);
            uniName = itemView.findViewById(R.id.contentsTag_CourseUni);
            facName = itemView.findViewById(R.id.contentsTag_Coursefac);
            tag = itemView.findViewById(R.id.contentsTag_tagName);
            menuMore = itemView.findViewById(R.id.contentsTag__menu);
            email = itemView.findViewById(R.id.contentsTag_email);
        }


        void bind(final int position) {

            final CourseNotes course = CourseList.get(position);

            label.setText(course.getCourseLabel());
            uniName.setText(course.getUniName());
            facName.setText(course.getFacName());
            tag.setText(course.getTagName());
            email.setText(course.getCreatorEmail());

            if (!menuMore.callOnClick()) {
                menuMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (deleteTask == null)
                            deleteTask = new AsyncTask<Void, Void, String>() {
                                @Override
                                protected String doInBackground(Void... voids) {
                                    return Logic.deleteCourseNoteFromModerator(DataError, UserInfo.token, course);
                                }

                                @Override
                                protected void onPostExecute(String s) {
                                    super.onPostExecute(s);
                                    deleteTask = null;

                                    if (DataError[0] == null && s == null) {
                                        Toast.makeText(mContext, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (DataError[0] != null) {
                                        Toast.makeText(mContext, DataError[0], Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }
                            }.execute();

                    }
                });
            }


        }


    }

    private String[] DataError = {null,null};
}

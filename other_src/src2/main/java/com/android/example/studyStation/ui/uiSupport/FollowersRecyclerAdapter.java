package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.example.studyStation.DefinedData.Followee;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by AmmarRabie on 22/11/2017.
 */

public class FollowersRecyclerAdapter extends RecyclerView.Adapter<FollowersRecyclerAdapter.FolloweeViewHolder> {

    // Class variables for the list (that holds data) and the Context
    private List<Followee> mFolloweeList;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }


    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     * @param followeeList the data to inflate to View
     */
    public FollowersRecyclerAdapter(Context mContext, List<Followee> followeeList) {
        this.mContext = mContext;
        this.mFolloweeList = followeeList;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FolloweeViewHolder that holds the view for each item
     */
    @Override
    public FolloweeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: inflate you own layout of each item
        // Inflate the followee_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.followee_layout, parent, false);

        return new FolloweeViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FolloweeViewHolder holder, int position) {

   /*     // TODO: here is the logic how you want to display your data
        holder.bind(position);

        View root = inflater.inflate(R.layout.fragment_followers, container, false);


        mRecyclerView = root.findViewById(R.id.fragmentFollowers_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        if (mTask == null) {
            mTask = new GetFolloweeTask(root.getContext());
            mTask.execute(UserInfo.email);
        }
        return root;*/
        ;

        Followee ramy=mFolloweeList.get(position);

        holder.followeeDep.setText(ramy.getmDepName());

        holder.followeeUni.setText(ramy.getmUniName());
        holder.followeeName.setText(ramy.getmName());
        holder.followeeFac.setText(ramy.getmFacName());
        holder.followeeEmail.setText(ramy.getmEmail());

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return mFolloweeList.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
    class FolloweeViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        TextView followeeName;
        TextView followeeDep ;
        TextView followeeFac;
        TextView followeeUni;
        TextView followeeEmail;

        /**
         * Constructor for the FolloweeViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public FolloweeViewHolder(View itemView) {
            super(itemView);

            followeeName = itemView.findViewById(R.id.followeeLayout_name);
            followeeDep = itemView.findViewById(R.id.followeeLayout_depName);
            followeeFac = itemView.findViewById(R.id.followeeLayout_facName);
            followeeUni = itemView.findViewById(R.id.followeeLayout_uniName);
            followeeEmail = itemView.findViewById(R.id.followeeLayout_email);
        }


        void bind(final int position) {

            Followee followee = mFolloweeList.get(position);

            followeeName.setText(followee.getmName());
            followeeDep.setText(followee.getmDepName());
            followeeFac.setText(followee.getmFacName());
            followeeUni.setText(followee.getmUniName());
            followeeEmail.setText(followee.getmEmail());

            if (mOnItemClickListener != null && !itemView.callOnClick()) {

                this.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(position,FolloweeViewHolder.this);
                    }
                });
            }
        }


    }
}

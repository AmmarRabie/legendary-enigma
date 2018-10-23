package com.android.example.studyStation.ui.uiSupport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.NewFeed;
import com.android.example.studyStation.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by AmmarRabie on 22/11/2017.
 */

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.NewViewHolder> {

    private static final String TAG = NewsFeedRecyclerAdapter.class.getSimpleName();
    // Class variables for the list (that holds data) and the Context
    private List<NewFeed> mNewsFeeds;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener = null;

    public interface OnItemClickListener {
        void onItemClick(int positionInTheList, RecyclerView.ViewHolder clickedView);
    }


    public NewsFeedRecyclerAdapter(Context mContext, List<NewFeed> newsFeedsList) {
        this.mContext = mContext;
        this.mNewsFeeds = newsFeedsList;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new FolloweeViewHolder that holds the view for each item
     */
    @Override
    public NewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO: inflate you own layout of each item
        // Inflate the followee_layout to a view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_feed_layout, parent, false);

        return new NewViewHolder(view);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(NewViewHolder holder, int position) {

        // TODO: here is the logic how you want to display your data
        holder.bind(position);

        holder.itemView.setTag(mNewsFeeds.get(position).getContentLink());
    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return mNewsFeeds.size();
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // TODO: implement you own ViewHolder
    // Inner class for creating ViewHolders
    class NewViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the followee narm, department, ...
        TextView creatorName;
        TextView creationDate;
        TextView cntLabel;


        YouTubeThumbnailView contentView;

        /**
         * Constructor for the NewsViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public NewViewHolder(View itemView) {
            super(itemView);
            creatorName = itemView.findViewById(R.id.newFeedLayout_name);
            creationDate = itemView.findViewById(R.id.newFeedLayout_date);
            cntLabel = itemView.findViewById(R.id.newFeedLayout_cntLabel);

            contentView = itemView.findViewById(R.id.newFeedLayout_YouTubeThumbnailView);

        }


        /**
         * bind this object(holder) with data from the list in this position
         * also put the listeners
         * @param position
         */
        void bind(final int position) {

            final NewFeed newfeed = mNewsFeeds.get(position);

//            creatorDep.setText(newfeed.getCreatorDep());
//            creatorFac.setText(newfeed.getCreatorFac());
//            creatorUni.setText(newfeed.getCreatorUni());
            creatorName.setText(newfeed.getCreatorName());
            creationDate.setText(newfeed.getCreationDate());
            cntLabel.setText(newfeed.getContentLabel());

            final String vLink = Uri.parse(mNewsFeeds.get(position).getContentLink()).getQueryParameter("v");
            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                    youTubeThumbnailView.setVisibility(View.GONE);
                    Toast.makeText(mContext, errorReason.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                }
            };

            contentView.initialize(mContext.getString(R.string.Youtube_key), new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(vLink);
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);

                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });

            if (!contentView.callOnClick()) {
                contentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext,
                                mContext.getString(R.string.Youtube_key), vLink,100,true,false); // 100 = delay before start
                        mContext.startActivity(intent);
                    }
                });

            }
        }

    }
}

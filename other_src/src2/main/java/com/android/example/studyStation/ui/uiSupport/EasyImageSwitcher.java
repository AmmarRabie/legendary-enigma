package com.android.example.studyStation.ui.uiSupport;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * Created by AmmarRabie on 23/09/2017.
 */

/**
 * this class to easy use imageSwitcher with also construct with the imageSwitcher, then setup with the images you want
 * and the class wil handle the clickListeners for you
 * <p>
 * for example in an activity:
 * EasyImageSwitcher overview = new EasyImageSwitcher(this,findViewById(R.id.image_overview),
 * findViewById(R.id.overview_prev),findViewById(R.id.overview_next));
 * overview.setupImageSwitcherWith(new int[] { R.drawable.football_ground_in_pakistan ,R.drawable.pic_1,R.drawable.pic_2,R.drawable.zombatar_1});
 * <p>
 * EasyImageSwitcher balls = new EasyImageSwitcher(this,findViewById(R.id.image_balls),null,null);
 * balls.setupImageSwitcherWith(new int[] { R.drawable.ball,R.drawable.ball_2,R.drawable.ball_3,R.drawable.ball_4});
 * <p>
 * EasyImageSwitcher grass = new EasyImageSwitcher(this,findViewById(R.id.image_grass),null,null);
 * grass.setupImageSwitcherWith(new int[] { R.drawable.untitledd,R.drawable.images_2,R.drawable.images_5});
 */
public class EasyImageSwitcher {


    private Context mContext;
    private ImageSwitcher mImageSwitcher;
    private View[] mNavigationButtons = null;

    private int mCurrImage = -1;
    private int[] mDrawableResourcesIds;
    private Bitmap[] mBitmapImages;
    int length;

    private boolean mSetNavigationButtons = false;

    public EasyImageSwitcher(Context context, View imageSwitcher, @Nullable View previousButton, @Nullable View nextButton) {
        mContext = context;
        if (!(imageSwitcher instanceof ImageSwitcher)) {
            Log.e("EasyImageSwitcher", "EasyImageSwitcherConstructor: view is not an imageSwitcher");
            throw new IllegalArgumentException("view is not an image switcher");
        }

        mImageSwitcher = ((ImageSwitcher) imageSwitcher);
        initializeImageSwitcher();

        if (previousButton != null && nextButton != null) {
            mSetNavigationButtons = true;
            mNavigationButtons = new View[2];
            mNavigationButtons[0] = previousButton;
            mNavigationButtons[1] = nextButton;
        }

    }

    public void setupWith(@DrawableRes int[] drawableResourcesIds) {
        // reset
        mBitmapImages = null;
        mCurrImage = -1;
        length = drawableResourcesIds.length;

        mDrawableResourcesIds = drawableResourcesIds;


        setInitialImage();
        setImageRotateListener();


        if (mSetNavigationButtons)
            setNavigationButtons();

    }

    public void setupWith(Bitmap[] images) {
        // reset
        mDrawableResourcesIds = null;
        mCurrImage = -1;
        length = images.length;

        mBitmapImages = images;
        setInitialImage();
        setImageRotateListener();

        

        if (mSetNavigationButtons)
            setNavigationButtons();

    }

    private void setNavigationButtons() {
        mNavigationButtons[1].setOnClickListener(mClickListener);

        mNavigationButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrImage--;
                if (mCurrImage == -1) {
                    mCurrImage = length - 1;
                }
                mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
                mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right));
                setCurrentImage();
            }
        });
    }


    private void initializeImageSwitcher() {
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(mContext);
                return imageView;
            }
        });

        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left));
        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right));
    }

    private void setImageRotateListener() {

        mImageSwitcher.setOnClickListener(mClickListener);
    }

    private void setInitialImage() {
        mCurrImage = 0;
        setCurrentImage();
    }

    private void setCurrentImage() {

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images[mCurrImage]);
        bitmap = Bitmap.createScaledBitmap(bitmap,500,500,false);
        Drawable d = new BitmapDrawable(getResources(),bitmap);
        imageSwitcher.setImageDrawable(d);*/

        // old
        if (mCurrImage == -1) {
            Log.e("EasyImageSwitcher", "setCurrentImage: currImage = -1");
            return;
        }
        if (mDrawableResourcesIds != null)
            mImageSwitcher.setImageResource(mDrawableResourcesIds[mCurrImage]);
        else
            mImageSwitcher.setImageDrawable(new BitmapDrawable(mContext.getResources(), mBitmapImages[mCurrImage]));

    }


    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mCurrImage++;
            if (mCurrImage == length) {
                mCurrImage = 0;
            }
            mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left));
            mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.slide_out_right));
            setCurrentImage();
        }
    };
}
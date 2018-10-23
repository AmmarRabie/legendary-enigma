/*
package com.android.example.easymatch_sidenav.ui.uiSupport;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.easymatch_sidenav.R;
import com.android.example.easymatch_sidenav.ui.fragments.HistoryFragment;
import com.android.example.easymatch_sidenav.ui.fragments.NearGroundsFragment;
import com.android.example.easymatch_sidenav.ui.fragments.UpcomingMatchesFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

*/
/**
 * Created by AmmarRabie on 14/10/2017.
 *//*


public class EasyMatchSideNav {
    private Context mContext;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "upcoming";
    private static final String TAG_NEAR_GROUNDS = "nearGrounds";
    private static final String TAG_HISTORY = "history";
    private static final String TAG_TEAM = "team";
    private static final String TAG_ABOUT = "aboutUs";
    public static String CURRENT_TAG = TAG_HOME;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://pogmogoal.com/wp-content/uploads/Cover1.jpg";
    //    private static final String urlNavHeaderBg = "http://www.coolfbcovers.com/covers-images/download/Football68.jpg";
//    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    private static final String urlProfileImg = "https://yt3.ggpht.com/-i9HRIJGssKw/AAAAAAAAAAI/AAAAAAAAAAA/3DiCRDnvyUU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg";


    public EasyMatchSideNav(Context context, NavigationView navigationView, DrawerLayout drawer)
    {
        mContext = context;
        this.navigationView = navigationView;
        this.drawer = drawer;

        this.navHeader = navigationView.getHeaderView(0);
        this.txtName = navHeader.findViewById(R.id.name);
        this.txtWebsite = navHeader.findViewById(R.id.website);
        this.imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        this.imgProfile = navHeader.findViewById(R.id.img_profile);



    }
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    */
/***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     *//*

    private void loadNavHeader() {
        // name, website
        txtName.setText("Ammar Alsayed");
        txtWebsite.setText("ammaralsayed55@gmail.com");

        // loading header background image
        Glide.with(mContext).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(mContext).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    */
/***
     * Returns respected fragment that user
     * selected from navigation menu
     *//*

    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (mContext.getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers();
            // i see that i shouldn't close the drawer
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                if (fragment == null) return;
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frameFragmentContainer, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }


        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                UpcomingMatchesFragment upcomingMatchesFragment = new UpcomingMatchesFragment();
                return upcomingMatchesFragment;
            case 1:
                // photos
                NearGroundsFragment nearGroundsFragment = new NearGroundsFragment();
                return nearGroundsFragment;
            case 2:
                // movies fragment
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            case 3:


            case 4:

            case 5:

            default:
                return new UpcomingMatchesFragment();
        }
    }

    private void setToolbarTitle() {
        mContext.getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }



}
*/

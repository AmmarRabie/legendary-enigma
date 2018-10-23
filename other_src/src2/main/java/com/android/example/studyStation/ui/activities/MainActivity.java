package com.android.example.studyStation.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.appLogic.LocationUtility;
import com.android.example.studyStation.ui.fragments.CourseListFragment;
import com.android.example.studyStation.ui.fragments.EditContentFragment;
import com.android.example.studyStation.ui.fragments.FolloweesFragment;
import com.android.example.studyStation.ui.fragments.FormFragment;
import com.android.example.studyStation.ui.fragments.MyStudyFragment;
import com.android.example.studyStation.ui.fragments.HomeFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import com.android.example.studyStation.ui.uiSupport.CircleTransform;


import com.android.example.studyStation.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "news_feed";
    private static final String TAG_MY_STUDY = "my study";
    private static final String TAG_FORM = "form_questions";
    private static final String TAG_FOLLOWERS = "followers";
    private static final String TAG_EDIT_CONTENT = "edit_content";
    private static final String TAG_COURSE_LIST = "course_list";

    public static String CURRENT_TAG = TAG_HOME;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://pogmogoal.com/wp-content/uploads/Cover1.jpg";
    //    private static final String urlNavHeaderBg = "http://www.coolfbcovers.com/covers-images/download/Football68.jpg";
//    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    private static final String urlProfileImg = "https://yt3.ggpht.com/-i9HRIJGssKw/AAAAAAAAAAI/AAAAAAAAAAA/3DiCRDnvyUU/s88-c-k-no-mo-rj-c0xffffff/photo.jpg";


    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        ///////////////////// test the location utility ///////////////////////////////////////
        LocationUtility.getUserLocation(this, new LocationUtility.onGetLastLocationListener() {
            @Override
            public void onGetLastLocation(double latitude, double longitude) {
                Toast.makeText(MainActivity.this, "your latitude = " + latitude, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "your longitude = " + longitude, Toast.LENGTH_SHORT).show();
                long distance_me_mosque = LocationUtility.distanceBetween_mm(latitude,longitude,29.958474,31.146703);
                Toast.makeText(MainActivity.this, "distance between you and belal mosque is\n\t" + (distance_me_mosque/1000.0f) + " meter(321.869)", Toast.LENGTH_SHORT).show();

                long distance_me_pyramids = LocationUtility.distanceBetween_mm(latitude,longitude,29.9627586,31.1500178);
                Toast.makeText(MainActivity.this, "distance between you and pyramids ground is\n\t" + (distance_me_pyramids/1000.0f) + " meter(643.738)", Toast.LENGTH_SHORT).show();

                long distance_me_uni = LocationUtility.distanceBetween_mm(latitude,longitude,30.035134,31.430687);
                Toast.makeText(MainActivity.this, "distance between you and university is\n\t" + (distance_me_uni/1000.0f) + " meter(28968.2)", Toast.LENGTH_SHORT).show();
            }
        });
        //End///////////////////// test the location utility ///////////////////////////////////////End

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
//        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        // if the rotation is happened just load the correct title
        else
        {
            setToolbarTitle();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Check to see which item was being clicked and perform appropriate action
        switch (item.getItemId()) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case R.id.nav_newsFeed:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;

            case R.id.nav_myStudy:
                navItemIndex = 1;
                CURRENT_TAG = TAG_MY_STUDY;
                break;

            case R.id.nav_form:
                navItemIndex = 2;
                CURRENT_TAG = TAG_FORM;
                break;

            case R.id.nav_courseList:
                navItemIndex = 3;
                CURRENT_TAG = TAG_COURSE_LIST;
                break;

            case R.id.nav_editContent:
                navItemIndex = 4;
                CURRENT_TAG = TAG_EDIT_CONTENT;
                break;

            case R.id.nav_followers:
                navItemIndex = 5;
                CURRENT_TAG = TAG_FOLLOWERS;
                break;



            // TODO: complete these features
            case R.id.nav_feedback:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_about:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_help:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;

            default:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
        }

        //Checking if the item is in checked state or not, if not make it in checked state
        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }

        loadHomeFragment();
        return true;

    }


    //////////////////////////////////////////////

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // name, website
        txtName.setText("Ammar Alsayed");
        txtWebsite.setText("ammaralsayed55@gmail.com");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
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
                // Home (news feed)
                return new HomeFragment();
            case 1:
                // My study
                return new MyStudyFragment();
            case 2:
                // Form
                return new FormFragment();
            case 3:
                // Course list
                return new CourseListFragment();

            case 4:
                // Edit Content
                return new EditContentFragment();

            case 5:
                // followers
                return new FolloweesFragment();

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // checking if user is on other navigation menu
        // rather than home
        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if we are in the near grounds fragment
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.option_near_grounds, menu);
        }
        if (navItemIndex == 2) {
            getMenuInflater().inflate(R.menu.option_history, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuMain_action_findBy) {
            Toast.makeText(getApplicationContext(), "find by..", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.menuMain_action_mapView) {
            Toast.makeText(getApplicationContext(), "map view clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

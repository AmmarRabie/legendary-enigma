package com.android.example.studyStation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.PreferenceUtility;
import com.android.example.studyStation.server.ServerUtility;
import com.android.example.studyStation.ui.fragments.CourseListFragment;
import com.android.example.studyStation.ui.fragments.EditContentFragment;
import com.android.example.studyStation.ui.fragments.EditCourseListFragment;
import com.android.example.studyStation.ui.fragments.FolloweesFragment;
import com.android.example.studyStation.ui.fragments.FormFragment;
import com.android.example.studyStation.ui.fragments.HomeFragment;
import com.android.example.studyStation.ui.fragments.MyStudyFragment;
import com.android.example.studyStation.ui.uiSupport.CircleTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;


    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //YouTubePlayerFragment myYouTubePlayerFragment;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "news_feed";
    private static final String TAG_MY_STUDY = "my study";
    private static final String TAG_FORM = "form_questions";
    private static final String TAG_FOLLOWERS = "followers";
    private static final String TAG_EDIT_CONTENT = "edit_content";
    private static final String TAG_COURSE_LIST = "course_list";
    private static final String TAG_EDIT_COURSE_LIST_FRAGMENT = "EditCourseListFragment_tag";


    public static String CURRENT_TAG = TAG_HOME;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://holytrinitycathedral.ca/wp-content/uploads/2013/12/Man-Holding-Bible-Background1.png";
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
        ServerUtility.setIP(PreferenceUtility.getServerIp(this));

        Toast.makeText(this, "expecting change in the server ip", Toast.LENGTH_SHORT).show();
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
        txtName = navHeader.findViewById(R.id.name);
        txtWebsite = navHeader.findViewById(R.id.website);
        imgNavHeaderBg = navHeader.findViewById(R.id.img_header_bg);
        imgProfile = navHeader.findViewById(R.id.img_profile);

        // load nav menu header data and put listners
        loadNavHeader();

        // initializing navigation menu
//        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        // if the rotation is happened just load the correct title
        else {
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
                if (!UserInfo.isModerator) {
                    Toast.makeText(this, "sorry this func is for moderator only", Toast.LENGTH_SHORT).show();
                    break;
                }
                navItemIndex = 4;
                CURRENT_TAG = TAG_EDIT_CONTENT;
                break;

            case R.id.nav_followers:
                navItemIndex = 5;
                CURRENT_TAG = TAG_FOLLOWERS;
                break;

            case R.id.nav_editCourseList:
                if (!UserInfo.isModerator) {
                    Toast.makeText(this, "sorry this func is for moderator only", Toast.LENGTH_SHORT).show();
                    break;
                }
                navItemIndex = 6;
                CURRENT_TAG = TAG_EDIT_COURSE_LIST_FRAGMENT;
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
        txtName.setText(UserInfo.name);
        txtWebsite.setText(UserInfo.email);

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

        if (!imgProfile.callOnClick()) {
            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), StudentProfileActivity.class));
                }
            });
        }
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
            drawer.closeDrawers();
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

            case 6:
                // Edit coures list
                return new EditCourseListFragment();

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
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private static final int SETTINGS_ACTIVITY_REQUEST_CODE = 31;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_ACTIVITY_REQUEST_CODE);
            return true;
        }
        if (id == R.id.action_profile) {
            startActivity(new Intent(getApplicationContext(), StudentProfileActivity.class));
            return true;
        }
        if (id == R.id.action_signout) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            UserInfo.token = null;
            finish();
            return true;
        }

        if (id == R.id.action_search) {
            Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(searchActivity);
            return true;
        }

        if (id == R.id.action_savev) {
            Intent searchActivity = new Intent(MainActivity.this, SaveVideoActivity.class);
            startActivity(searchActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SETTINGS_ACTIVITY_REQUEST_CODE) {
            ServerUtility.setIP(PreferenceUtility.getServerIp(getApplicationContext()));
            return;
        }
    }


}

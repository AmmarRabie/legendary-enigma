package com.android.example.studyStation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.android.example.studyStation.R;
import com.android.example.studyStation.ui.fragments.DoSearchFragment;
import com.android.example.studyStation.ui.fragments.SearchResultFragment;

public class SearchActivity extends FragmentActivity {

    TextView NoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        NoResult = (TextView) findViewById(R.id.search_NoResult);
        NoResult.setVisibility(View.GONE);
        DoSearchFragment firstFragment = new DoSearchFragment();


        //  SearchResultFragment firstFragment=new SearchResultFragment();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment).commit();
    }

    public void setNoResult() {
        NoResult.setVisibility(View.VISIBLE);
    }
}

package com.android.example.studyStation.ui.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Tag;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by AmmarRabie on 30/09/2017.
 */

public class FormFragment extends Fragment implements AdapterView.OnItemSelectedListener {

     getTagTask tagTask=null;

    @InjectView(R.id.fragment_form_Tag)
    Spinner search_tag;

    String SearchTagSelected;

    private String[] DataError = {null, null};

    public FormFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form, container, false);


      // fr=(FrameLayout)view.findViewById(R.id.fragmentform_container);

        //DoSearchFragment firstFragment = new DoSearchFragment();

        search_tag=(Spinner)view.findViewById(R.id.fragment_form_Tag);
        search_tag.setOnItemSelectedListener(this);

        if (tagTask == null) {
            tagTask = new getTagTask(view.getContext());
            tagTask.execute();
        }

        //  SearchResultFragment firstFragment=new SearchResultFragment();
        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        //firstFragment.setArguments(getActivity().getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
       //getActivity().getSupportFragmentManager().beginTransaction()
         //       .add(R.id.fragmentform_container, firstFragment).commit();

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId()==R.id.fragment_form_Tag)
        {

            String SearchChoiceSelected = (String) parent.getItemAtPosition(position);

            SearchResultFragment nf=new SearchResultFragment();

            Bundle args = new Bundle();

            args.putString("SearchInput","");

            args.putString("SearchType",SearchChoiceSelected);

            nf.setArguments(args);

            // FolloweesFragment nf=new FolloweesFragment();
            FragmentManager fg= getFragmentManager();
            fg.beginTransaction().replace(R.id.fragmentform_container,nf).commit();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class getTagTask extends AsyncTask<String, Void, List<Tag>> {
        private Context mContext;

        public getTagTask(Context mContext) {
            this.mContext = mContext;
        }

        protected List<Tag> doInBackground(String... params) {
            return Logic.getTags(DataError);
        }


        protected void onPostExecute(List<Tag> result) {
            super.onPostExecute(result);
            tagTask = null;

            if (DataError[0] == null && result == null) {
                Toast.makeText(getContext(), DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(getContext(), DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }

            if (result.size() == 0) {
                Toast.makeText(mContext, "no tags found", Toast.LENGTH_SHORT).show();
                return;
            }
            List<String> newStringList=new ArrayList<String>(result.size());

            for (int i=0;i<result.size();i++)
            {
                newStringList.add(result.get(i).getName());
            }
          //  newStringList.add("ALL");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, newStringList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            search_tag.setAdapter(dataAdapter);
            search_tag.setClickable(true);

            search_tag.setSelection(0,true);

            SearchTagSelected=search_tag.getItemAtPosition(0).toString();
        }


    }

}

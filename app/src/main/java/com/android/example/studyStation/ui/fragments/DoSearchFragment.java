package com.android.example.studyStation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.example.studyStation.DefinedData.Tag;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.activities.SignupActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

import static com.android.example.studyStation.DefinedData.UserInfo.token;

/**
 * Created by AmmarRabie on 25/10/2017.
 */

public class DoSearchFragment extends Fragment implements View.OnClickListener ,AdapterView.OnItemSelectedListener{

    @InjectView(R.id.do_search_choice)
    Spinner search_choice;

    @InjectView(R.id.do_search_tags)
    Spinner search_tag;

    @InjectView(R.id.do_search_string)
    EditText search_input;

    @InjectView(R.id.do_search_button)
    Button search_button;

    ArrayAdapter<CharSequence> choices_Adapter = null;

    ArrayAdapter<CharSequence> tags_Adapter = null;

    getTagTask tagTask=null;

    String SearchChoiceSelected="Student";  // to store our choice


    private String[] DataError = {null, null};

    String SearchTagSelected=null;
    public DoSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_do_search, container, false);

        choices_Adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.search_choices_strings, android.R.layout.simple_spinner_item);
        choices_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        search_choice=(Spinner)view.findViewById(R.id.do_search_choice);

        search_tag=(Spinner)view.findViewById(R.id.do_search_tags);

        search_choice.setAdapter(choices_Adapter);

        search_choice.setOnItemSelectedListener(this);
        search_tag.setOnItemSelectedListener(this);

        search_input=(EditText) view.findViewById(R.id.do_search_string);

        search_button=(Button) view.findViewById(R.id.do_search_button);

        search_button.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {

        Bundle args = new Bundle();

        String searchIN=search_input.getText().toString();

        args.putString("SearchInput",searchIN);

        if (SearchChoiceSelected .equals( "Student"))
        {
            if (searchIN.equals(""))
            {
                Toast.makeText(this.getContext(), "Enter some words to search about it ", Toast.LENGTH_LONG).show();
                return;
            }
            args.putString("SearchType","StudentSearch");
        }
        else if (SearchChoiceSelected.equals("Question"))
        {
            if (SearchTagSelected.equals("ALL"))
            {
                if (searchIN.equals(""))
                {
                    Toast.makeText(this.getContext(), "Enter some words to search about it ", Toast.LENGTH_LONG).show();
                    return;
                }
                args.putString("SearchType","QuestionByHeader");
            }
            else
            {
                    args.putString("SearchType", SearchTagSelected);
            }
        }

        SearchResultFragment nf=new SearchResultFragment();

        nf.setArguments(args);

       // FolloweesFragment nf=new FolloweesFragment();
        FragmentManager fg= getFragmentManager();
        fg.beginTransaction().replace(R.id.fragment_container,nf).commit();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId()==R.id.do_search_choice)
        {
            SearchChoiceSelected=(String)parent.getItemAtPosition(position);
            if (SearchChoiceSelected.equals("Question"))
            {

                if (tagTask == null) {
                    tagTask = new getTagTask(view.getContext());
                }
                tagTask.execute();
            }
            else
            {
                search_tag.setClickable(false);
                search_tag.setAdapter(null);
            }

        }
        else
        {
            SearchTagSelected= (String)parent.getItemAtPosition(position);
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

            List<String> newStringList=new ArrayList<String>(result.size());

            for (int i=0;i<result.size();i++)
            {
                newStringList.add(result.get(i).getName());
            }
            newStringList.add("ALL");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, newStringList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            search_tag.setAdapter(dataAdapter);
            search_tag.setClickable(true);

            search_tag.setSelection(0,true);

            SearchTagSelected=search_tag.getItemAtPosition(0).toString();
        }
    }
}


package com.android.example.studyStation.ui.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText _nameText;
    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.input_youtupeLink)
    EditText _youtupeLink;
    @InjectView(R.id.btn_signup)
    Button _signupButton;

    @InjectView(R.id.activitySignup_dep)
    Spinner mDepSpinner;
    @InjectView(R.id.activitySignup_fac)
    Spinner mFacSpinner;
    @InjectView(R.id.activitySignup_uni)
    Spinner mUniSpinner;


    private String uniCurrSelection = null;
    private String facCurrSelection = null;
    private String depCurrSelection = null;
    ArrayAdapter<String> uniArrayAdapter = null;
    ArrayAdapter<String> facArrayAdapter = null;
    ArrayAdapter<String> depArrayAdapter = null;

    GetAvailableDomainTask mGetAvailableDomainTask = null;
    SignUpTask mSignUpTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);


        // setup adapters
        uniArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item); //selected item will look like a spinner set from XML
        facArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item);
        depArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item);
        uniArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        facArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        depArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);


        if (mGetAvailableDomainTask == null)
            mGetAvailableDomainTask = new GetAvailableDomainTask();
        mGetAvailableDomainTask.execute_all();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }


    private ProgressDialog progressDialog = null;

    // call when the user click on sign up button
    public void signup() {
        Log.d(TAG, "Signup");
        if (mSignUpTask != null)
            return;
        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);


        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        // TODO: Implement your own signup logic here. --> i will send a request to insert this student
        mSignUpTask = new SignUpTask(name, email, password, depCurrSelection, facCurrSelection, uniCurrSelection);
        mSignUpTask.execute((Void) null);

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);

    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        progressDialog.dismiss();
        setResult(RESULT_OK);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "signup failed", Toast.LENGTH_LONG).show();
        if (progressDialog != null)
            progressDialog.dismiss();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String youtupeLink = _youtupeLink.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (youtupeLink.length() < 4 || youtupeLink.length() > 10) {
            _youtupeLink.setError("not a channel youtupe link");
            valid = false;
        } else {
            _youtupeLink.setError(null);
        }


        return valid;
    }


    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void updateDepSpinner(ArrayList<String> spinnerArray) {

        if (spinnerArray == null || spinnerArray.size() == 0 || TextUtils.isEmpty(spinnerArray.get(0))) {
            Log.e(TAG, "updateDepSpinner: null list or empty");
            return;
        }
//        spinnerArray.add(0, "Select your department");

        depCurrSelection = spinnerArray.get(0);
        depArrayAdapter.clear();
        depArrayAdapter.addAll(spinnerArray);

        mDepSpinner.setAdapter(depArrayAdapter);

        // Set the integer mSelected to the constant values
        mDepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    depCurrSelection = selection;
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserInfo.dep = null;
            }
        });
    }

    private void updateFacSpinner(ArrayList<String> spinnerArray) {

        if (spinnerArray == null || spinnerArray.size() == 0 || TextUtils.isEmpty(spinnerArray.get(0))) {
            Log.d(TAG, "updateFacSpinner: null list or empty");
            return;
        }
//        spinnerArray.add(0, "Select your department");
        facCurrSelection = spinnerArray.get(0);

        facArrayAdapter.clear();
        facArrayAdapter.addAll(spinnerArray);

        mFacSpinner.setAdapter(facArrayAdapter);

        // Set the integer mSelected to the constant values
        mFacSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    facCurrSelection = selection;
                    mGetAvailableDomainTask.execute_Dep(uniCurrSelection, facCurrSelection);
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserInfo.dep = null;
            }
        });
    }

    private void updateUniSpinner(ArrayList<String> spinnerArray) {

        if (spinnerArray == null || spinnerArray.size() == 0 || TextUtils.isEmpty(spinnerArray.get(0))) {
            Log.d(TAG, "updateUniSpinner: null list of universities or empty");
            return;
        }
//        spinnerArray.add(0, "Select your department");
        uniCurrSelection = spinnerArray.get(0);

        uniArrayAdapter.clear();
        uniArrayAdapter.addAll(spinnerArray);
        mUniSpinner.setAdapter(uniArrayAdapter);

        // Set the integer mSelected to the constant values
        mUniSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    uniCurrSelection = selection;
                    mGetAvailableDomainTask.execute_Fac(uniCurrSelection);
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                UserInfo.dep = null;
            }
        });
    }

    /**
     * organize the 3 individual tasks of getting universities, faculties and departments
     */
    private class GetAvailableDomainTask {

        private String[] DataError2 = {null,null};

        /**
         * init all spinners with first value find
         */
        void execute_all() {
            if (mGetUniTask != null || mGetFacTask != null || mGetDepTask != null) return;
            mGetUniTask = new GetUniTask();
            mGetUniTask.execute(); // execute all inner*inner (on post execute)
        }

        private void execute_Dep(String uni, String fac) {
            if (mGetDepTask != null) return;
            mGetDepTask = new GetDepTask();
            mGetDepTask.execute(uni, fac);
        }

        private void execute_Fac(String uni) {
            if (mGetFacTask != null) return;
            mGetFacTask = new GetFacTask();
            mGetFacTask.execute(uni);
        }

        private GetUniTask mGetUniTask = null;
        private GetFacTask mGetFacTask = null;
        private GetDepTask mGetDepTask = null;

        private GetAvailableDomainTask() {
        }

        private class GetUniTask extends AsyncTask<Void, Void, ArrayList<String>> {
            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                return Logic.getUniversityList(DataError2);
            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                super.onPostExecute(strings);
                if (DataError2[0] == null && strings == null) {
                    Toast.makeText(SignupActivity.this, DataError2[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DataError2[0] != null) {
                    Toast.makeText(SignupActivity.this, DataError2[0], Toast.LENGTH_SHORT).show();
                    return;
                }
                mGetUniTask = null;
                updateUniSpinner(strings);
                if (mGetFacTask != null) return;
                mGetFacTask = new GetFacTask();
                mGetFacTask.execute(uniCurrSelection);
            }
        }


        private class GetFacTask extends AsyncTask<String, Void, ArrayList<String>> {


            @Override
            protected ArrayList<String> doInBackground(String... strings) {
                if (strings[0] == null || TextUtils.isEmpty(strings[0])) return null;
                return Logic.getFacultyList(DataError2, strings[0]);
            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                super.onPostExecute(strings);
                mGetFacTask = null;
                if (DataError2[0] == null && strings == null) {
                    Toast.makeText(SignupActivity.this, DataError2[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (DataError2[0] != null) {
                    Toast.makeText(SignupActivity.this, DataError2[0], Toast.LENGTH_SHORT).show();
                    return;
                }
                updateFacSpinner(strings);
                if (mGetDepTask != null) return;
                mGetDepTask = new GetDepTask();
                mGetDepTask.execute(uniCurrSelection, facCurrSelection);
            }
        }


        private class GetDepTask extends AsyncTask<String, Void, ArrayList<String>> {
            @Override
            protected ArrayList<String> doInBackground(String... strings) {
                String uni = strings[0];
                String fac = strings[1];
                if (TextUtils.isEmpty(uni) || TextUtils.isEmpty(fac))
                    return null;
                return Logic.getDepartmentList(DataError2, uni, fac);
            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                super.onPostExecute(strings);
                if (DataError2[0] == null && strings == null) {
                    Toast.makeText(SignupActivity.this, DataError2[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DataError2[0] != null) {
                    Toast.makeText(SignupActivity.this, DataError2[0], Toast.LENGTH_SHORT).show();
                    return;
                }
                mGetDepTask = null;
                updateDepSpinner(strings);
            }
        }
    }


    /**
     * find if this email exist, if not insert him in the database
     * result is the message to show to the user
     */
    private class SignUpTask extends AsyncTask<Void, Void, String> {
        private Student mStudent;
        private String mes;
        private String[] DataError1 ={null,null};

        private SignUpTask(String name, String email, String pass, String dep, String fac, String uni) {
            mStudent = new Student(name, email, pass, dep, fac, uni);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return Logic.signUp(DataError1, mStudent);
        }

        @Override
        protected void onPostExecute(String token) {
            super.onPostExecute(token);
            mSignUpTask = null;
            if (DataError1[0] == null && token == null) {
                Toast.makeText(SignupActivity.this, DataError1[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                onSignupFailed();
                return;
            }

            if (DataError1[0] != null) {
                Toast.makeText(SignupActivity.this, DataError1[0], Toast.LENGTH_SHORT).show();
                onSignupFailed();
                return;
            }

            setResult(RESULT_OK);
            UserInfo.token = token;
            Log.e("Ammar_TAG_debug", "token = " + token);
            onSignupSuccess();
        }

    }

    private String[] DataError = {null,null};
}
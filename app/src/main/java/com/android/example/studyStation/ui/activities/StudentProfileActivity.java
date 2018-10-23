package com.android.example.studyStation.ui.activities;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.studyStation.DefinedData.Student;
import com.android.example.studyStation.DefinedData.UserInfo;
import com.android.example.studyStation.R;
import com.android.example.studyStation.appLogic.Logic;
import com.android.example.studyStation.ui.fragments.MyStudyFragment;

import java.util.ArrayList;

import static com.android.example.studyStation.DefinedData.UserInfo.email;
import static com.android.example.studyStation.DefinedData.UserInfo.token;

public class StudentProfileActivity extends AppCompatActivity {

    private String frag_tag = "fragment-";
    private String mEmail;
    private Button btn_changePass;
    private Button btn_changeName;
    private TextView tv_uniName;
    private TextView tv_facName;
    private TextView tv_role;
    private TextView tv_email;
    private TextView tv_depName;
    private boolean isChanged_dep = false;
    private boolean isChanged_pas = false;
    private boolean isChanged_name = false;
    private boolean isCurrUser = false;
    private String newPassword = null;
    private String newName = null;

    //    private GetContentsTask mTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        tv_uniName = ((TextView) findViewById(R.id.activityStudentProfile_uni));
        tv_facName = ((TextView) findViewById(R.id.activityStudentProfile_fac));
        tv_depName = ((TextView) findViewById(R.id.activityStudentProfile_dep));
        tv_role = ((TextView) findViewById(R.id.activityStudentProfile_role));
        tv_email = ((TextView) findViewById(R.id.activityStudentProfile_email));
        btn_changePass = ((Button) findViewById(R.id.activityStudentProfile_changePass));
        btn_changeName = ((Button) findViewById(R.id.activityStudentProfile_changeName));
        setTitle("");
        mEmail = getIntent().getExtras() == null ? email : getIntent().getExtras().getString("email", email);
        if (email.equals(mEmail)) {
            isCurrUser = true;
            btn_changePass.setVisibility(View.VISIBLE);
            btn_changeName.setVisibility(View.VISIBLE);
            setListeners();
        }
        setStudentData();


        // Debug
        Toast.makeText(this, "hello" + mEmail, Toast.LENGTH_LONG).show();
    }


    public void onViewCourses(View view) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(frag_tag);
        if (fragmentByTag != null) {
            View root = fragmentByTag.getView();
            if (root.getVisibility() == View.VISIBLE)
                root.setVisibility(View.GONE);
            else
                root.setVisibility(View.VISIBLE);
            return;
        }
        Fragment fragment = new MyStudyFragment();

        if (!isCurrUser) {
            Bundle b = new Bundle();
            b.putString("email", email);
            fragment.setArguments(b);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.activityStudentProfile_fragmentContainer, fragment, frag_tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setListeners() {
        if (mGetDepTask == null) {
            mGetDepTask = new GetDepTask();
            mGetDepTask.execute(UserInfo.uni, UserInfo.fac);
        }
        dialogSpinner = new Spinner(this);
        tv_depName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfileActivity.this);
                builder.setTitle("changing Department");

                // Set up the input

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(dialogSpinner);

                // Set up the buttons
                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (depArrayAdapter.isEmpty()) return;
                        String dep = depArrayAdapter.getItem(dialogSpinner.getSelectedItemPosition());
                        tv_depName.setText(dep);
                        if (dep.equals(dep)) return;
                        setChangeOk();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        btn_changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfileActivity.this);
                builder.setTitle("Password change");


                final EditText input = new EditText(StudentProfileActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newPassword = input.getText().toString();
                        if (newPassword.isEmpty()) {
                            Toast.makeText(StudentProfileActivity.this, "empty password!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        isChanged_pas = true;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        btn_changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentProfileActivity.this);
                builder.setTitle("Name change");


                final EditText input = new EditText(StudentProfileActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newName = input.getText().toString();
                        if (newName.isEmpty() || newName.length() < 5) {
                            Toast.makeText(StudentProfileActivity.this, "name is invalid, lenght less than 5", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            return;
                        }
                        isChanged_name = true;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }

    private GetDepTask mGetDepTask = null;
    private Spinner dialogSpinner = null;
    private ArrayAdapter<String> depArrayAdapter;


    private void setChangeOk() {
        isChanged_dep = true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isCurrUser)
            getMenuInflater().inflate(R.menu.option_student_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (isChanged_dep || isChanged_pas || isChanged_name) {
                Toast.makeText(this, "updating...", Toast.LENGTH_SHORT).show();
                new UpdateTask().execute(tv_depName.getText().toString(), newPassword);
            } else
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class UpdateTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... info) {
            String dep = info[0];
            String pas = info[1];
            return Logic.updateStudentData(token, DataError, newPassword, dep, newName);
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(StudentProfileActivity.this, s, Toast.LENGTH_SHORT).show();

            if (DataError[0] == null && s == null) {
                Toast.makeText(StudentProfileActivity.this, DataError[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError[0] != null) {
                Toast.makeText(StudentProfileActivity.this, DataError[0], Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(StudentProfileActivity.this, s, Toast.LENGTH_SHORT).show();
            UserInfo.name = getTitle().toString();
            UserInfo.dep = tv_depName.getText().toString();
            setResult(RESULT_OK);
            finish();
            super.onPostExecute(s);
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
            mGetDepTask = null;
            if (DataError2[0] == null && strings == null) {
                Toast.makeText(StudentProfileActivity.this, DataError2[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                return;
            }

            if (DataError2[0] != null) {
                Toast.makeText(StudentProfileActivity.this, DataError2[0], Toast.LENGTH_SHORT).show();
                return;
            }
            depArrayAdapter = new ArrayAdapter<>(StudentProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, strings);
//            depArrayAdapter.setDropDownViewResource(R.layout.spinner_dialog_item);
            dialogSpinner.setAdapter(depArrayAdapter);
        }
    }


    void setStudentData() {
        if (isCurrUser) {
            setTitle(UserInfo.name);
            tv_depName.setText(UserInfo.dep);
            tv_uniName.setText(UserInfo.uni);
            tv_facName.setText(UserInfo.fac);
            tv_email.setText(email);
            if (UserInfo.isModerator)
                tv_role.setVisibility(View.VISIBLE);
            return;
        }

        // is othrer student, send to api and get info
        new AsyncTask<Void, Void, Student>() {

            @Override
            protected Student doInBackground(Void... voids) {
                return Logic.getProfile(DataError3, UserInfo.token, mEmail);
            }

            @Override
            protected void onPostExecute(Student student) {
                super.onPostExecute(student);
                if (DataError3[0] == null && student == null) {
                    Toast.makeText(StudentProfileActivity.this, DataError3[1] + "\ncan't reach server", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DataError3[0] != null) {
                    Toast.makeText(StudentProfileActivity.this, DataError3[0], Toast.LENGTH_SHORT).show();
                    return;
                }
                setTitle(student.getName());
                tv_depName.append(student.getDep());
                tv_uniName.append(student.getUni());
                tv_facName.append(student.getFac());
                tv_email.append(student.getEmail());
                if (student.isModerator())
                    tv_role.setVisibility(View.VISIBLE);

            }
        }.execute();

    }

    private String[] DataError = {null, null};
    private String[] DataError2 = {null, null};
    private String[] DataError3 = {null, null};
}

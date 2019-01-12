package ir.applinkfinder.hw6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.applinkfinder.hw6.database.TaskBaseHelper;
import ir.applinkfinder.hw6.database.TaskDbSchema;
import ir.applinkfinder.hw6.model.WorksRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginSignupFragment extends Fragment {

    private Button mButtonLogin;
    private Button mButtonSignupHere;
    private Button mButtonGuestLogin;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;

    public LoginSignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_signup, container, false);
        mButtonLogin = view.findViewById(R.id.button_login);
        mButtonSignupHere = view.findViewById(R.id.button_signup);
        mEditTextPassword = view.findViewById(R.id.edittext_password);
        mEditTextUsername = view.findViewById(R.id.edittext_username);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = WorksRepository.getInstance(getActivity()).searchContactPassword(mEditTextUsername.getText().toString());
                int contactId;
                if (mEditTextPassword.getText().toString().equals(password)){
                    Log.d("mytag", "login");

                    SQLiteDatabase db;
                    db = new TaskBaseHelper(getActivity()).getReadableDatabase();
                    String user = mEditTextUsername.getText().toString();
                    String query = "select * from " + TaskDbSchema.ContactsTable.NAME + " where " +
                            TaskDbSchema.ContactsTable.NAME + "." +TaskDbSchema.ContactsTable.Cols.USERNAME + " = " + "'" + user + "'";
                    Cursor cursor = db.rawQuery(query, null);
                    try {
                        // cursor ruye db peymayesh mikone
                        cursor.moveToFirst();
                        contactId = cursor.getInt(cursor.getColumnIndex(TaskDbSchema.ContactsTable.Cols.CONTACT_ID));
                    }// try
                    finally {
                        cursor.close();
                    }

//                    ContactIdFinder contactIdFinder = new ContactIdFinder();
//                    contactId = contactIdFinder.getContactId(mEditTextUsername.getText().toString());

                    Intent intent = MainActivity.newIntent(getActivity(), contactId);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getActivity(), R.string.toast_username_passwords_mismatch, Toast.LENGTH_SHORT).show();
                }

            }
        });

        mButtonSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Begin the transaction
                FragmentManager fragmentManager = getFragmentManager();
                Fragment signupFragment = new SignupFragment();
                if (fragmentManager.findFragmentById(R.id.container_login_signup) == null) {
                    fragmentManager.beginTransaction()
                            .add(R.id.container_login_signup, signupFragment)
                            .commit();
                }
                else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container_login_signup, signupFragment)
                            .commit();
                }
            }
        });

        return view;
    }
}
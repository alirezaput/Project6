package ir.applinkfinder.hw6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.applinkfinder.hw6.contacts.Contact;
import ir.applinkfinder.hw6.model.WorksRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    private Button mButtonSignUp;
    private EditText mEditTextSignupName;
    private EditText mEditTextSignupEmail;
    private EditText mEditTextSignupUsername;
    private EditText mEditTextSignupPassword;
    private EditText mEditTextSignupPasswordConfirm;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        mEditTextSignupName = view.findViewById(R.id.edittext_signup_name);
        mEditTextSignupEmail = view.findViewById(R.id.edittext_signup_email);
        mEditTextSignupUsername = view.findViewById(R.id.edittext_signup_username);
        mEditTextSignupPassword = view.findViewById(R.id.edittext_signup_password);
        mEditTextSignupPasswordConfirm = view.findViewById(R.id.edittext_signup_confirm_password);

        mButtonSignUp = view.findViewById(R.id.button_signup);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = mEditTextSignupName.getText().toString();
                String strEmail = mEditTextSignupEmail.getText().toString();
                String strUsername = mEditTextSignupUsername.getText().toString();
                String strPassword = mEditTextSignupPassword.getText().toString();
                String strPasswordConfirm = mEditTextSignupPasswordConfirm.getText().toString();

                if (!strPassword.equals(strPasswordConfirm)){
                    Toast.makeText(getActivity(), R.string.toast_passwords_mismatch , Toast.LENGTH_SHORT).show();
                }
                else {
                    //insert details in DB
                    Contact contact = new Contact();
                    contact.setName(strName);
                    contact.setEmail(strEmail);
                    contact.setUsername(strUsername);
                    contact.setPassword(strPassword);

                    WorksRepository.getInstance(getActivity()).insertContact(contact);

                    // bad az registration, be safheye qabli (LoginSignUpFragment) bargardad:
                    // Begin the transaction
                    FragmentManager fragmentManager = getFragmentManager();
                    LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
                    if (fragmentManager.findFragmentById(R.id.container_login_signup) == null) {
                        fragmentManager.beginTransaction()
                                .add(R.id.container_login_signup, loginSignupFragment)
                                .commit();
                    }
                    else {
                        fragmentManager.beginTransaction()
                                .replace(R.id.container_login_signup, loginSignupFragment)
                                .commit();
                    }
                }
            }
        });

        return view;
    }
}
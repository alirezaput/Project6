package ir.applinkfinder.hw6;

import android.content.Intent;
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

import ir.applinkfinder.hw6.model.WorksRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginSignupFragment extends Fragment {

    private Button mButtonLogin;
    private Button mButtonSignupHere;
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

                if (mEditTextPassword.getText().toString().equals(password)){
                    Log.d("mytag", "login");
                    Intent intent = MainActivity.newIntent(getActivity(), mEditTextUsername.getText().toString());
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
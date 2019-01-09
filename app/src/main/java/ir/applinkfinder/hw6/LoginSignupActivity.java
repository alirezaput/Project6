package ir.applinkfinder.hw6;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginSignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        // Begin the transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment loginSignupFragment = new LoginSignupFragment();
        if (fragmentManager.findFragmentById(R.id.container_login_signup) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.container_login_signup, loginSignupFragment)
                    .commit();
        }
    }//onCreate

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, LoginSignupActivity.class);
//        intent.putExtra();
        return intent;
    }//newIntent
}

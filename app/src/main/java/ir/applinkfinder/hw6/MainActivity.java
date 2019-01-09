package ir.applinkfinder.hw6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private String username;

    private static final String EXTRA_USERNAME = "ir.applinkfinder.hw6.extra_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra(EXTRA_USERNAME);

        mViewPager = findViewById(R.id.viewPager);

        ViewPagerClass adapter = new ViewPagerClass(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOffscreenPageLimit(10);

        // Give the TabLayout the ViewPager
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
    }//onCreate

    public static Intent newIntent(Context context, String username){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }//newIntent
}
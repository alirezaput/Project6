package ir.applinkfinder.hw6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ir.applinkfinder.hw6.model.WorksRepository;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private int contactId;

    private static final String EXTRA_CONTACT_ID = "ir.applinkfinder.hw6.extra_contact_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactId = getIntent().getIntExtra(EXTRA_CONTACT_ID, -1);
        Toast.makeText(this, "mainActivity Welcome User: " + contactId, Toast.LENGTH_SHORT).show();

        WorksRepository.getInstance(this).setContactID(contactId);

        getContactIDinMainActivity();

        mViewPager = findViewById(R.id.viewPager);

//        ViewPagerClass adapter = new ViewPagerClass(this, getSupportFragmentManager());
        ViewPagerClass adapter = new ViewPagerClass(this, getSupportFragmentManager(), contactId);

        // Set the adapter onto the view pager
        mViewPager.setAdapter(adapter);
//        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOffscreenPageLimit(10);

        // Give the TabLayout the ViewPager
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(mViewPager);
    }//onCreate

    @Override
    protected void onResume() {
        super.onResume();

    }

    public int getContactIDinMainActivity(){
        return contactId;
    }

    public static Intent newIntent(Context context, int contactId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contactId);
        return intent;
    }//newIntent
}
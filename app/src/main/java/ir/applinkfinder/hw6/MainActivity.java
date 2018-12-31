package ir.applinkfinder.hw6;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}

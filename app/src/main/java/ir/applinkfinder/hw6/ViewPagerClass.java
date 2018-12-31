package ir.applinkfinder.hw6;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Adapter of ViewPager
public class ViewPagerClass extends FragmentPagerAdapter{ //FragmentStatePagerAdapter {
    private Context mContext;

    public ViewPagerClass(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 2; //number of fragments
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_done);
            case 1:
                return mContext.getString(R.string.tab_undone);
//            case 2:
//                return mContext.getString(R.string.tab_all);
            default:
                return null;
        }
    }
}
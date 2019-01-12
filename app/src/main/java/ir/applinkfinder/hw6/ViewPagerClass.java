package ir.applinkfinder.hw6;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// Adapter of ViewPager
public class ViewPagerClass extends FragmentPagerAdapter{// implements MainActivity.OnAboutDataRecievedListener { //FragmentStatePagerAdapter {
    private Context mContext;
    private int contactId;

    public ViewPagerClass(Context context, FragmentManager fragmentManager, int contactID) {
        super(fragmentManager);
        mContext = context;
        contactId = contactID;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) { //0
//            return new DoneFragment();
            return new ListFragment().newInstance(0, contactId); //0
        }
        else if (position == 1){ //1
//            return new UndoneFragment();
            return new ListFragment().newInstance(1, contactId); //1
        }
//        else if (position == 2){
////            return new AllworksFragment();
//            return new ListFragment().newInstance(2);
//        }
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
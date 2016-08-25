package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.FeedFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.ProfileFragment;

/**
 * Created by kuwali on 8/21/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs = 4;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FeedFragment feedFragment = new FeedFragment();
                return feedFragment;
//            case 2:
//                TopicsFragment topicsFragment= new TopicsFragment();
//                return topicsFragment;
//            case 1:
//                NewQuestionFragment newQuestionFragment = new NewQuestionFragment();
//                return newQuestionFragment;
            case 3:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            default:
                FeedFragment feedFragments = new FeedFragment();
                return feedFragments;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

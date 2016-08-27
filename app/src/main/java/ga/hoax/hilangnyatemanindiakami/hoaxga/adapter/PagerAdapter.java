package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.roughike.bottombar.BottomBar;

import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.DiscoverFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.FeedFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.NotificationFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.ProfileFragment;

/**
 * Created by kuwali on 8/21/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private BottomBar bottomBar;
    private List<Fragment> fragmentList;

    public PagerAdapter(FragmentManager fm, BottomBar bottomBar, List<Fragment> fragmentList) {
        super(fm);
        this.bottomBar = bottomBar;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

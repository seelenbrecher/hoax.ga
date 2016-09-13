package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;
import java.util.Vector;

import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.PagerAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.DiscoverFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.FeedFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.NotificationFragment;
import ga.hoax.hilangnyatemanindiakami.hoaxga.fragment.ProfileFragment;

/**
 * Main Activity that consist of BottomBar, ActionBar and Fragment
 */
public class MainActivity extends BaseActivity {

    private static String TAG = "MainActivity";

    private User mActiveUser;
    private ActionBar mActionBar;
    private BottomBar mBottomBar;
    private ViewPager mViewPager;
    private ImageView mNewPostImage;
    private RelativeLayout mParentViewLayout;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();

        mActiveUser = UserService.getInstance(this).getCurrentUser();

        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mParentViewLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);
        mNewPostImage = (ImageView) findViewById(R.id.newPost);

        List<Fragment> mFragmentList = new Vector<>();
        mFragmentList.add(Fragment.instantiate(this, FeedFragment.class.getName()));
        mFragmentList.add(Fragment.instantiate(this, DiscoverFragment.class.getName()));
        mFragmentList.add(Fragment.instantiate(this, NotificationFragment.class.getName()));
        mFragmentList.add(Fragment.instantiate(this, ProfileFragment.class.getName()));

        mPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), mBottomBar, mFragmentList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mActiveUser == null) {
            finish();
            return;
        }

        try {
            assert mNewPostImage != null;
            mNewPostImage.bringToFront();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

        ViewCompat.setTranslationZ(mNewPostImage, 100);
        mParentViewLayout.invalidate();

        mNewPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position >=2)
                    mBottomBar.selectTabAtPosition(position+1);
                else
                    mBottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBottomBar.setOnTabSelectListener(bottomBarOnTabListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            Log.d(TAG, "Logout pressed");
            logout();
            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.menu_account_setting){
            Log.d(TAG, "Account Setting pressed");
            Intent intent = new Intent(getApplicationContext(), AccountSettingActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private void logout() {
        UserService.getInstance(getApplicationContext()).logout();
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("pass");
        editor.apply();
    }

    private OnTabSelectListener bottomBarOnTabListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(int tabId) {
            if (tabId == R.id.feedBB) {
                if (mViewPager != null) mViewPager.setCurrentItem(0);
                mActionBar.setTitle("Feeds");
            } else if (tabId == R.id.discoverBB) {
                if (mViewPager != null) mViewPager.setCurrentItem(1);
                mActionBar.setTitle("Discover");
            } else if (tabId == R.id.notificationBB) {
                if (mViewPager != null) mViewPager.setCurrentItem(2);
                mActionBar.setTitle("Notification");
            } else if (tabId == R.id.profileBB) {
                if (mViewPager != null) mViewPager.setCurrentItem(3);
                mActionBar.setTitle("Profile");
            }
        }
    };
}

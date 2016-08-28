package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
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
 * Created by kuwali on 8/21/16.
 */
public class MainActivity extends AppCompatActivity {

    private User activeUser;
    private ActionBar actionBar;
    private BottomBar bottomBar;
    private ViewPager pager;
    private ImageView newPost;
    private RelativeLayout parentView;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        activeUser = UserService.getInstance(this).getCurrentUser();

        if (activeUser == null) {
            finish();
            return;
        }

        fragmentList = new Vector<>();
        fragmentList.add(Fragment.instantiate(this, FeedFragment.class.getName()));
        fragmentList.add(Fragment.instantiate(this, DiscoverFragment.class.getName()));
        fragmentList.add(Fragment.instantiate(this, NotificationFragment.class.getName()));
        fragmentList.add(Fragment.instantiate(this, ProfileFragment.class.getName()));

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        pager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), bottomBar, fragmentList);

        parentView = (RelativeLayout) findViewById(R.id.activity_main_layout);

        newPost = (ImageView) findViewById(R.id.newPost);
        newPost.bringToFront();
        ViewCompat.setTranslationZ(newPost, 100);
        parentView.invalidate();

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });

        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position >=2)
                    bottomBar.selectTabAtPosition(position+1);
                else
                    bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomBar.setOnTabSelectListener(bottomBarOnTabListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_logout) {
            UserService.getInstance(getApplicationContext()).logout();
            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("username");
            editor.remove("pass");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.menu_account_setting){
            Intent intent = new Intent(getApplicationContext(), AccountSettingActivity.class);
            startActivity(intent);
        }
        return true;
    }

    private OnTabSelectListener bottomBarOnTabListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            if (tabId == R.id.feedBB) {
                if (pager != null) pager.setCurrentItem(0);
                actionBar.setTitle("Feeds");
            } else if (tabId == R.id.discoverBB) {
                if (pager != null) pager.setCurrentItem(1);
                actionBar.setTitle("Discover");
            } else if (tabId == R.id.profileBB) {
                if (pager != null) pager.setCurrentItem(3);
                actionBar.setTitle("Profile");
            } else if (tabId == R.id.notificationBB) {
                if (pager != null) pager.setCurrentItem(2);
                actionBar.setTitle("Notification");
            }
        }
    };
}

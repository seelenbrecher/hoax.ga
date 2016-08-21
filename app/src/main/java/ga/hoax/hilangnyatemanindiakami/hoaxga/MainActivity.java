package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.PagerAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/21/16.
 */
public class MainActivity extends AppCompatActivity {

    private User activeUser;
    private ActionBar actionBar;
    private BottomBar bottomBar;
    private ViewPager pager;

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

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(bottomBarOnTabListener);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        return true;
    }

    private OnTabSelectListener bottomBarOnTabListener = new OnTabSelectListener() {
        @Override
        public void onTabSelected(@IdRes int tabId) {
            if (tabId == R.id.feed) {
                actionBar.setTitle("Feeds");
            } else if (tabId == R.id.discover) {
                actionBar.setTitle("Discover");
            } else if (tabId == R.id.profile) {
                actionBar.setTitle("Profile");
            } else if (tabId == R.id.notification) {
                actionBar.setTitle("Notification");
            }
        }
    };
}

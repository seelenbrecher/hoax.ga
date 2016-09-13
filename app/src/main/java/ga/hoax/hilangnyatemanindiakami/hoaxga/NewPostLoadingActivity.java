package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;


/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostLoadingActivity extends Activity {
    private final int POST_DELAY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_loading);

        CircularProgressBar mProgressBar = (CircularProgressBar) findViewById(R.id.newPostProgress);
        mProgressBar.setProgressWithAnimation(100, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), NewPostResultActivity.class);
                intent.putExtra("postTitle", getIntent().getStringExtra("postTitle"));
                NewPostLoadingActivity.this.finish();
                NewPostLoadingActivity.this.startActivity(intent);
            }
        }, POST_DELAY);
    }
}

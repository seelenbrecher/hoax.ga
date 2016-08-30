package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostLoadingActivity extends Activity {
    private CircularProgressBar progressBar;
    private ImageView progressLogo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_loading);

        progressBar = (CircularProgressBar) findViewById(R.id.newPostProgress);
        progressBar.setProgressWithAnimation(100, 5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), NewPostResultActivity.class);
                intent.putExtra("postTitle", getIntent().getStringExtra("postTitle"));
                NewPostLoadingActivity.this.finish();
                NewPostLoadingActivity.this.startActivity(intent);
            }
        }, 6000);
    }
}

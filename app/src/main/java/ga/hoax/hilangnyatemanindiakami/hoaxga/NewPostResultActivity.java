package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.dd.CircularProgressButton;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostResultActivity extends BaseActivity {
    private ActionBar mActionBar;
    private CircularProgressButton mFeedButton;
    private CircularProgressButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_result);

        mActionBar = getSupportActionBar();

        mFeedButton = (CircularProgressButton) findViewById(R.id.feedButton);
        mShareButton = (CircularProgressButton) findViewById(R.id.sharedButton);

        mFeedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, UserService.getInstance(getApplicationContext()).getCurrentUser().getName() + " has post " + getIntent().getStringExtra("postTitle") + " at Hoax.ga.. find out more");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mActionBar.setTitle("Result");
    }
}

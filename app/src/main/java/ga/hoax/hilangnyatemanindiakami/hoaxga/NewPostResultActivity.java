package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostResultActivity extends AppCompatActivity{
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_result);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Result");
    }
}

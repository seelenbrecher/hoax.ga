package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dd.CircularProgressButton;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login.LoginActivity;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostResultActivity extends AppCompatActivity{
    private ActionBar actionBar;
    private CircularProgressButton feedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_result);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Result");

        feedButton = (CircularProgressButton) findViewById(R.id.feedButton);

        feedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}

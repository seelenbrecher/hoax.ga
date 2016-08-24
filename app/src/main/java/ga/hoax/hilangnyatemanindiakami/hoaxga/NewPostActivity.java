package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostActivity extends AppCompatActivity{

    private ActionBar actionBar;
    private EditText newPostTitleEditText;
    private EditText newPostUrlEditText;
    private EditText newPostContentEditText;
    private EditText newPostTagEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Detect It!");
        actionBar.setDisplayHomeAsUpEnabled(true);

        newPostTitleEditText = (EditText) findViewById(R.id.newPostTitle);
        newPostUrlEditText = (EditText) findViewById(R.id.newPostURL);
        newPostContentEditText = (EditText) findViewById(R.id.newPostContent);
        newPostTagEditText = (EditText) findViewById(R.id.newPostTags);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_submit) {
            User currUser = UserService.getInstance(getApplicationContext()).getCurrentUser();
            Post post = new Post(0,newPostTitleEditText.getText().toString(), currUser, new Date(), newPostContentEditText.getText().toString(), false);
            DataService.getInstance(getApplicationContext()).addPost(post, currUser, addPostListener);
            super.onBackPressed();
        } else if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    private DataService.AddPostListener addPostListener = new DataService.AddPostListener() {
        @Override
        public void onResponse(boolean added, String message, Post post) {
            if (added) {
                
            }
        }
    };

}

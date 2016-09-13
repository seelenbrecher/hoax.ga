package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;
import mabbas007.tagsedittext.TagsEditText;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostActivity extends BaseActivity {
    public static final int SELECT_PICTURE = 1;
    private static final String TAG = "New Post Activity";

    private ActionBar mActionBar;
    private EditText mNewPostTitleEditText;
    private EditText mNewPostUrlEditText;
    private EditText mNewPostContentEditText;
    private TagsEditText mNewPostTagEditText;
    private EditText mNewPostAddImageName;
    private CircularProgressButton mNewPostAddImageButton;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mActionBar = getSupportActionBar();

        mNewPostTitleEditText = (EditText) findViewById(R.id.newPostTitle);
        mNewPostUrlEditText = (EditText) findViewById(R.id.newPostURL);
        mNewPostContentEditText = (EditText) findViewById(R.id.newPostContent);
        mNewPostTagEditText = (TagsEditText) findViewById(R.id.newPostTags);
        mNewPostAddImageName = (EditText) findViewById(R.id.newPostAddImageName);
        mNewPostAddImageButton = (CircularProgressButton) findViewById(R.id.newPostAddImageButton);

        mNewPostAddImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            assert mActionBar != null;
            mActionBar.setTitle("Detect It!");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }

        mNewPostAddImageName.setClickable(false);
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
            submit();
            Intent intent = new Intent(getApplicationContext(), NewPostLoadingActivity.class);
            intent.putExtra("postTitle", mNewPostTitleEditText.getText().toString());
            finish();
            startActivity(intent);
        } else if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }

    private void submit() {
        User currUser = UserService.getInstance(getApplicationContext()).getCurrentUser();
        Post post = new Post(0, mNewPostTitleEditText.getText().toString(), currUser.getUsername(), new Date(), mNewPostContentEditText.getText().toString(), false);
        DataService.getInstance(getApplicationContext()).addPost(post, currUser, addPostListener, mBitmap);
    }

    private DataService.AddPostListener addPostListener = new DataService.AddPostListener() {
        @Override
        public void onResponse(boolean added, String message, Post post) {
            if (added) {
                Toast.makeText(getApplicationContext(),"Posted", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                if (mBitmap != null) {
                    mBitmap.recycle();
                }
                // This is so fucking unreadable :v
                InputStream stream = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                mBitmap = BitmapFactory.decodeStream(stream, null, options);
                int width = mBitmap.getWidth();
                int height = mBitmap.getHeight();
                double scale = width >= 1280 || height >= 1280 ? 0.4 : 1;
                height = (int)(height*scale);
                width = (int)(width*scale);
                mBitmap = Bitmap.createScaledBitmap(mBitmap, width, height, false);
                if (stream != null) {
                    stream.close();
                }
                mNewPostAddImageName.setText(mBitmap.getConfig().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

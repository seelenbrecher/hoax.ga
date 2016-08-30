package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;
import mabbas007.tagsedittext.TagsEditText;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostActivity extends AppCompatActivity{
    public static final int SELECT_PICTURE = 1;

    private ActionBar actionBar;
    private EditText newPostTitleEditText;
    private EditText newPostUrlEditText;
    private EditText newPostContentEditText;
    private TagsEditText newPostTagEditText;
    private EditText newPostAddImageName;
    private ProgressDialog progressDialog;
    private CircularProgressButton newPostAddImageButton;
    private Bitmap bitmap;

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
        newPostTagEditText = (TagsEditText) findViewById(R.id.newPostTags);
        newPostAddImageName = (EditText) findViewById(R.id.newPostAddImageName);

        newPostAddImageButton = (CircularProgressButton) findViewById(R.id.newPostAddImageButton);

        newPostAddImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        newPostAddImageName.setClickable(false);
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
            Post post = new Post(0, newPostTitleEditText.getText().toString(), currUser.getUsername(), new Date(), newPostContentEditText.getText().toString(), false);
            DataService.getInstance(getApplicationContext()).addPost(post, currUser, addPostListener, bitmap);
            Intent intent = new Intent(getApplicationContext(), NewPostLoadingActivity.class);
            intent.putExtra("postTitle", newPostTitleEditText.getText().toString());
            finish();
            startActivity(intent);
        } else if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
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
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(data.getData());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                bitmap = BitmapFactory.decodeStream(stream, null, options);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double scale = width >= 1280 || height >= 1280 ? 0.4 : 1;
                height = (int)(height*scale);
                width = (int)(width*scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                stream.close();
                newPostAddImageName.setText(bitmap.getConfig().toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/25/16.
 */
public class AccountSettingActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 1;

    private ActionBar actionBar;
    private CircularProgressButton saveButton;
    private CircularImageView changeProfilePicture;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;

    private EditText firstName;
    private EditText lastName;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText job;
    private EditText quotes;

    private User currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        progressDialog = new ProgressDialog(getApplicationContext());

        currentUser = UserService.getInstance(getApplicationContext()).getCurrentUser();

        actionBar = getSupportActionBar();

        changeProfilePicture = (CircularImageView) findViewById(R.id.changeProfilePicture);
        if (currentUser.getProfileImage() != null) {
            changeProfilePicture.setImageBitmap(UserService.getInstance(getApplicationContext()).getProfileImage(currentUser));
        }

        firstName = (EditText) findViewById(R.id.changeFirstNameEditText);
        lastName = (EditText) findViewById(R.id.changeLastNameEditText);
        oldPassword = (EditText) findViewById(R.id.oldPasswordEditText);
        newPassword = (EditText) findViewById(R.id.newPasswordEditText);
        job = (EditText) findViewById(R.id.changeJobEditText);
        quotes = (EditText) findViewById(R.id.changeQuoteEditText);

        saveButton = (CircularProgressButton) findViewById(R.id.saveChangesButton);

        progressDialog.setIndeterminate(true);

        actionBar.setTitle("Account Setting");

        changeProfilePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        //set forms' placeholder
        if(currentUser.getFirstName() != null){
            firstName.setText(currentUser.getFirstName());
        }
        else{
            firstName.setText("");
        }

        if(currentUser.getLastName() != null){
            lastName.setText(currentUser.getLastName());
        }
        else{
            lastName.setText("");
        }

        if(currentUser.getJob() != null){
            job.setText(currentUser.getJob());
        }
        else{
            job.setText("");
        }

        if(currentUser.getQuote() != null){
            quotes.setText(currentUser.getQuote());
        }
        else{
            quotes.setText("");
        }

        saveButton.setOnClickListener(saveOnClickListener);
        quotes.setOnEditorActionListener(finishUpdateListener);
    }

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
                double scale = 100.0/height;
                height = (int)(height*scale);
                width = (int)(width*scale);
                bitmap = Bitmap.createScaledBitmap(bitmap, width,height, false);
                stream.close();
                Toast.makeText(this,bitmap.getConfig().toString(),Toast.LENGTH_SHORT).show();
                changeProfilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveChanges(){
        String firstNameText = firstName.getText().toString();
        String lastNameText = lastName.getText().toString();
        String newPasswordText = newPassword.getText().toString();
        String jobText = job.getText().toString();
        String quotesText = quotes.getText().toString();


        Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

        UserService.getInstance(this).updateProfile(firstNameText, lastNameText, newPasswordText, jobText, quotesText, updateListener, bitmap);
    }

    private View.OnClickListener saveOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(!oldPassword.getText().toString().equals(newPassword.getText().toString())){
                Toast.makeText(getApplicationContext(), "Password Missmatch", Toast.LENGTH_SHORT).show();
            }
            else {
                saveChanges();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private UserService.UpdateListener updateListener = new UserService.UpdateListener() {
        @Override
        public void onResponse(boolean updated, String message, User user) {
            progressDialog.dismiss();
            if (updated) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private TextView.OnEditorActionListener finishUpdateListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveChanges();
            }
            return false;
        }
    };
}

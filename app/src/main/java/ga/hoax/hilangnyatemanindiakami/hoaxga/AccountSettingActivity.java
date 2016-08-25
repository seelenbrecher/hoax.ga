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
import android.view.View;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.dd.CircularProgressButton;

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
    private ImageView changeProfilePicture;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Account Setting");

        changeProfilePicture = (ImageView) findViewById(R.id.changeProfilePicture);
        changeProfilePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
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

    private UserService.RegisterListener registerListener = new UserService.RegisterListener() {
        @Override
        public void onResponse(boolean registered, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            if (registered) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", user.getUsername());
                editor.putString("pass", user.getPassword());
                editor.commit();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.firebase.auth.FirebaseAuth;

import ga.hoax.hilangnyatemanindiakami.hoaxga.MainActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.LandingPageActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/20/16.
 */
public class LoginActivity extends Activity {

    private ProgressDialog progressDialog;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CircularProgressButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            goToMainActivity();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        usernameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (CircularProgressButton) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(loginOnClickListener);
        passwordEditText.setOnEditorActionListener(passwordDoneListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        progressDialog.show();
        UserService.getInstance(getApplicationContext()).login(username, password, loginListener);
        progressDialog.dismiss();
    }

    private View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    };

    private UserService.LoginListener loginListener = new UserService.LoginListener() {
        @Override
        public void onResponse(boolean loggedIn, String message, User user) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            if(loggedIn) {
//                SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("username", user.getUsername());
//                editor.putString("pass", user.getPassword());
//                editor.commit();
                goToMainActivity();
            }
        }
    };

    private TextView.OnEditorActionListener passwordDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
            }
            return false;
        }
    };
}

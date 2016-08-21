package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.register;

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

import ga.hoax.hilangnyatemanindiakami.hoaxga.LandingPageActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login.LoginActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;

/**
 * Created by kuwali on 8/21/16.
 */
public class RegisterActivity extends Activity {

    private ProgressDialog progressDialog;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private CircularProgressButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            goToMainActivity();
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        usernameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        registerButton = (CircularProgressButton) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(registerOnClickListener);
        emailEditText.setOnEditorActionListener(emailDoneListener);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LandingPageActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void register() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String email = emailEditText.getText().toString();
        progressDialog.show();
        UserService.getInstance(getApplicationContext()).registerUser(username,password,email,registerListener);
    }

    private View.OnClickListener registerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };

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
                goToMainActivity();
            }
        }
    };

    private TextView.OnEditorActionListener emailDoneListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register();
            }
            return false;
        }
    };
}

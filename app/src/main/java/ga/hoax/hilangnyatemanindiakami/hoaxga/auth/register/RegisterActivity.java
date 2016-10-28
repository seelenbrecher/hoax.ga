package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ga.hoax.hilangnyatemanindiakami.hoaxga.LandingPageActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login.LoginActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;

/**
 * Created by kuwali on 8/21/16.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private CircularProgressButton registerButton;

    //firebase related begin
    private final String TAG = "RegisterActivity";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //firebase related end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        usernameEditText = (EditText) findViewById(R.id.userNameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        registerButton = (CircularProgressButton) findViewById(R.id.registerButton);
        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        registerButton.setOnClickListener(registerOnClickListener);
        lastNameEditText.setOnEditorActionListener(finishRegisterListener);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                register();
                break;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
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
        final String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String firstName = firstNameEditText.getText().toString();
        final String lastName = lastNameEditText.getText().toString();

        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Failed to register",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            addUserDatabase(firstName, lastName, username, email);
                            goToMainActivity();
                        }
                    }
                });
    }

    private void addUserDatabase(String firstname, String lastname, String username, String email) {
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mFirebaseAuth;
        FirebaseUser mFirebaseUser;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        System.out.println(mFirebaseUser.getUid());
        User user = new User(firstname, lastname, username, email, mFirebaseUser.getUid(), "", "", "", "");
        mFirebaseDatabaseReference.child("users").push().setValue(user);
    }

    private View.OnClickListener registerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            register();
        }
    };


    private TextView.OnEditorActionListener finishRegisterListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                register();
            }
            return false;
        }
    };
}

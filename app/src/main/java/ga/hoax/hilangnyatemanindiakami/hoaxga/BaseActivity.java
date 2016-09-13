package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Base Activity class that extends AppCompatActivity
 * contain almost all basic function that include in every Activity
 */
public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

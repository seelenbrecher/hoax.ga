package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kuwali on 8/25/16.
 */
public class AccountSettingActivity extends AppCompatActivity {
    public ActionBar actionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Account Setting");
    }
}

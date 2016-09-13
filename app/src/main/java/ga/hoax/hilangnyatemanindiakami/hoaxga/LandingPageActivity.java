package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login.LoginActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.register.RegisterActivity;
import it.chengdazhi.decentbanner.DecentBanner;

/**
 * Created by kuwali on 8/13/16.
 */
public class LandingPageActivity extends Activity {
    private DecentBanner mDecentBanner;
    private CircularProgressButton mButtonLogin;
    private CircularProgressButton mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        if (UserService.getInstance(this).getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        mDecentBanner = (DecentBanner) findViewById(R.id.decentBanner);
        mButtonLogin = (CircularProgressButton) findViewById(R.id.loginButton);
        mButtonRegister = (CircularProgressButton) findViewById(R.id.registerButton);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDecentBanner.setBackgroundResource(R.color.transparent);
        View view1 = getLayoutInflater().inflate(R.layout.decent_banner_view1, null);
        View view2 = getLayoutInflater().inflate(R.layout.decent_banner_view2, null);

        List<View> mViewList = new ArrayList<>();
        mViewList.add(view1);
        mViewList.add(view2);

        List<String> mTitleList = new ArrayList<>();
        mTitleList.add("");
        mTitleList.add("");

        mDecentBanner.start(mViewList, mTitleList, 2, 500);
    }
}

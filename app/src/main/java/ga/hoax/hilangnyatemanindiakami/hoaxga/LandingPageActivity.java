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
    private DecentBanner decentBanner;
    private List<View> views;
    private List<String> titles;
    private CircularProgressButton buttonLogin;
    private CircularProgressButton buttonRegister;

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

        decentBanner = (DecentBanner) findViewById(R.id.decentBanner);
        decentBanner.setBackgroundResource(R.color.transparent);
        View view1 = getLayoutInflater().inflate(R.layout.decent_banner_view1, null);
        View view2 = getLayoutInflater().inflate(R.layout.decent_banner_view2, null);

        views = new ArrayList<>();
        views.add(view1);
        views.add(view2);

        titles = new ArrayList<>();
        titles.add("");
        titles.add("");

        decentBanner.start(views, titles, 2, 500);

        buttonLogin = (CircularProgressButton) findViewById(R.id.loginButton);
        buttonRegister = (CircularProgressButton) findViewById(R.id.registerButton);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}

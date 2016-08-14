package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import it.chengdazhi.decentbanner.DecentBanner;

/**
 * Created by kuwali on 8/13/16.
 */
public class LandingPage extends Activity {
    private DecentBanner decentBanner;
    private List<View> views;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

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
    }
}

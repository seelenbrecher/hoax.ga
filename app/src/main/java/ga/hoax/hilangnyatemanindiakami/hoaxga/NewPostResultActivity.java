package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.CircularProgressButton;

import org.json.JSONObject;
import org.w3c.dom.Text;

import at.grabner.circleprogress.CircleProgressView;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.login.LoginActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;

/**
 * Created by kuwali on 8/24/16.
 */
public class NewPostResultActivity extends AppCompatActivity{

    private final String API_URL = "http://kawung.mhs.cs.ui.ac.id/~fahmi51/index.php";

    private ActionBar actionBar;
    private CircularProgressButton feedButton;
    private CircularProgressButton shareButton;

    private CircleProgressView progressView;
    private RoundCornerProgressBar MLProgressBar;
    private TextView MLProgressValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post_result);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Result");

        feedButton = (CircularProgressButton) findViewById(R.id.feedButton);
        shareButton = (CircularProgressButton) findViewById(R.id.sharedButton);
        progressView = (CircleProgressView) findViewById(R.id.newPostResultBar);
        MLProgressValue = (TextView) findViewById(R.id.newPostMLResultValue);
        MLProgressBar = (RoundCornerProgressBar) findViewById(R.id.newPostMLResultBar);

        RequestQueue mRequestQueue;

        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, API_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });


        mRequestQueue.add(jsObjRequest);

        feedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, UserService.getInstance(getApplicationContext()).getCurrentUser().getName() + " has post " + getIntent().getStringExtra("postTitle") + " at Hoax.ga.. find out more");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via"));
            }
        });
    }
}

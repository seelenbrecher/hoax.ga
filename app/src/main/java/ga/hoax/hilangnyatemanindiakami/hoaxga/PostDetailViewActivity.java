package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by fahmi on 25/08/2016.
 */
public class PostDetailViewActivity extends AppCompatActivity {
    //parent View related
    private ActionBar actionBar;

    //postCreator related
    private ImageView imagePostStarter;
    private TextView titlePost;
    private TextView postStarter;
    private TextView datePosted;
    private ImageView postImage;
    private TextView postContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Post Detail");

        imagePostStarter = (ImageView) findViewById(R.id.imagePostStarter);
        titlePost = (TextView) findViewById(R.id.titlePost);
        postStarter = (TextView) findViewById(R.id.postStarter);
        datePosted = (TextView) findViewById(R.id.datePosted);
        postImage = (ImageView) findViewById(R.id.postImage);
        postContent = (TextView) findViewById(R.id.postContent);

        postStarter.setText("gay");
        postContent.setText("<3<3");




    }


}

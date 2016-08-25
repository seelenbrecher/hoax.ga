package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by fahmi on 25/08/2016.
 */
public class PostDetailViewActivity extends AppCompatActivity {
    //parent View related
    private ActionBar actionBar;

    //View related
    private ImageView imagePostStarter;
    private TextView titlePost;
    private TextView postStarter;
    private TextView datePosted;
    private ImageView postImage;
    private TextView postContent;
    private RecyclerView commentRecyclerView;

    //Intent data related
    private Post post;

    //@TODO: use some library like momentjs or smth
    private String getMoment(Date postDate){
        Date date = new Date();
        String moment = "";
        long diff = date.getTime() - postDate.getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;
        if (diff >= 86400) {
            moment = Math.round(diff/86400) + " days";
        } else if (diff >= 3600) {
            moment = Math.round(diff/3600) + " hours";
        } else if (diff >= 60) {
            moment = Math.round(diff/60) + " minutes";
        } else {
            moment = Math.round(diff) + " seconds";
        }
        return moment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        post = (Post)bundle.get("post");

        actionBar = getSupportActionBar();
        actionBar.setTitle("Post Detail");

        imagePostStarter = (ImageView) findViewById(R.id.imagePostStarter);
        titlePost = (TextView) findViewById(R.id.titlePost);
        postStarter = (TextView) findViewById(R.id.postStarter);
        datePosted = (TextView) findViewById(R.id.datePosted);
        postImage = (ImageView) findViewById(R.id.postImage);
        postContent = (TextView) findViewById(R.id.postContent);
        commentRecyclerView = (RecyclerView) findViewById(R.id.commentRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        String datePostMoment = getMoment(post.getDate());

        postStarter.setText(post.getUser().getName());
        titlePost.setText(post.getTitle());
        datePosted.setText(datePostMoment);
        postContent.setText(post.getContent());

        commentRecyclerView.setHasFixedSize(true);
        commentRecyclerView.setLayoutManager(layoutManager);




    }


}

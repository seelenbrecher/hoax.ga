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

import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.CommentAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Comments;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by fahmi on 25/08/2016.
 */
public class PostDetailViewActivity extends AppCompatActivity {
    //parent View related
    private ActionBar actionBar;

    //View related
    private RecyclerView recyclerView;

    //Intent data related
    private Post post;
    private List<Comments> comments = new ArrayList<>();

    //adapter
    private CommentAdapter adapter;

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


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
//        commentRecyclerView.setHasFixedSize(true);
        buildData();
        adapter = new CommentAdapter(getApplicationContext(), post, comments);
        System.out.println(comments);
        recyclerView.setAdapter(adapter);

    }

    private void buildData(){
        comments = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("name1");
        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("name2");
        User user3 = new User();
        user3.setId(3);
        user3.setFirstName("name3");
        Comments c1 = new Comments(1, 1, user1, new Date(100), "anjing lo");
        Comments c2 = new Comments(2, 2, user2, new Date(102), "lo yg anjing bangst");
        Comments c3 = new Comments(3, 3, user3, new Date(106), "sama-sama anjing gausah ribut");
        Comments c4 = new Comments(4, 4, user1, new Date(116), "bacot lo babi china");
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        comments.add(c4);
    }


}

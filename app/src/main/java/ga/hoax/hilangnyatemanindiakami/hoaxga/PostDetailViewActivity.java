package ga.hoax.hilangnyatemanindiakami.hoaxga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Comments;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;

/**
 * Created by fahmi on 25/08/2016.
 */
public class  PostDetailViewActivity extends BaseActivity {
    //parent View related
    private ActionBar actionBar;

    //View related
    private RecyclerView recyclerView;

    //Intent data related
    private List<Comments> comments = new ArrayList<>();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private Query mFirebaseQuery;

    //adapter

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Post Detail");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

//        commentRecyclerView.setHasFixedSize(true);
//        buildData();
//        User user = UserService.getInstance(getApplicationContext()).getUserByUsername(post.getUser());

//        adapter = new CommentAdapter(getApplicationContext(), user, post, comments);
//        recyclerView.setAdapter(adapter);

//        DataService.getInstance(getApplicationContext()).getCommentList(getCommentListener);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String postId = (String) bundle.get("post");
        System.out.println(postId);
        System.out.println((String) bundle.get("user"));

        mFirebaseQuery = mFirebaseDatabaseReference.orderByChild("posts");
        mFirebaseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                System.out.println(String.valueOf(value.get("title")));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        System.out.println("pp" + post.getTitle() + post.getId());
////        DataService.getInstance(getApplicationContext()).setPostChosen(post.getId(), true);
//        System.out.println(post.isSelected());
    }

    @Override
    protected void onDestroy(){
       super.onDestroy();
//        DataService.getInstance(getApplicationContext()).setPostChosen(post.getId(), false);
    }


//    private void buildData(){
//        comments = new ArrayList<>();
//        User user1 = new User();
//        user1.setId(1);
//        user1.setFirstName("name1");
//        User user2 = new User();
//        user2.setId(2);
//        user2.setFirstName("name2");
//        User user3 = new User();
//        user3.setId(3);
//        user3.setFirstName("name3");
//        Comments c1 = new Comments(1, 1, user1, new Date(100), "anjing lo");
//        Comments c2 = new Comments(2, 2, user2, new Date(102), "lo yg anjing bangst");
//        Comments c3 = new Comments(3, 3, user3, new Date(106), "sama-sama anjing gausah ribut");
//        Comments c4 = new Comments(4, 4, user1, new Date(116), "bacot lo babi china");
//        comments.add(c1);
//        comments.add(c2);
//        comments.add(c3);
//        comments.add(c4);
//    }

    DataService.GetCommentListListener getCommentListener = new DataService.GetCommentListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Comments> commentList) {
            if (success) {
                comments.clear();
//                adapter.notifyDataSetChanged();
                System.out.println(commentList);
                comments.addAll(commentList);
//                adapter.notifyDataSetChanged();
            }
        }
    };

}

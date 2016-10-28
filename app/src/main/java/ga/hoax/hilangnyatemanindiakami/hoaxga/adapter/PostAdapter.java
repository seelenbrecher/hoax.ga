package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ga.hoax.hilangnyatemanindiakami.hoaxga.PostDetailViewActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by fahmi on 27/10/16.
 */
public class PostAdapter extends ArrayAdapter<Post> {

    private Context context;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    DatabaseReference ref;

    public PostAdapter(Context context, int resourceId, List<Post> items) {
        super(context, resourceId, items);
        this.context = context;
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();

    }

    private class ViewHolder {
        TextView titlePost;
        TextView postStarter;
        TextView datePosted;
        TextView postContent;
        TextView postDetail;
        TextView postResult;
        Button voteUpButton;
        Button voteDownButton;
    }

    private String momentJs(Date date){
        Date curDate = new Date();
        long diff = curDate.getTime() - date.getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;

        if (diff >= 86400) {
            return Math.round(diff/86400) + " days ago";

        }
        if (diff >= 3600) {
            return Math.round(diff/3600) + " hours ago";

        }
        if (diff >= 60) {
            return Math.round(diff / 60) + " minutes ago";
        }
        return Math.round(diff) + " seconds ago";
    }

    private String analyze(int up, int down){
        int tmp = (int)(Math.random() * 100) % 75;
        tmp += up - down;
        if( tmp >= 50) return "FACT";
        return "HOAX";

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        Post post = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_list_feed, null);
            holder = new ViewHolder();
            holder.titlePost = (TextView) convertView.findViewById(R.id.titlePost);
            holder.postStarter = (TextView) convertView.findViewById(R.id.postStarter);
            holder.datePosted = (TextView) convertView.findViewById(R.id.datePosted);
            holder.postContent = (TextView) convertView.findViewById(R.id.postContent);
            holder.postResult = (TextView) convertView.findViewById(R.id.postResult);
            holder.voteDownButton = (Button) convertView.findViewById(R.id.downVoteButton);
            holder.voteUpButton = (Button) convertView.findViewById(R.id.upVoteButton);
            holder.postDetail = (TextView) convertView.findViewById(R.id.postDetail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titlePost.setText(post.getTitle());
        holder.postStarter.setText(post.getUser());
        holder.datePosted.setText(momentJs(post.getDate()));
        holder.postContent.setText(post.getContent());

        String res = analyze(post.getVoteUp(), post.getVoteDown());
        holder.postResult.setText(res);

        if(res.equals("FACT")) {
            holder.postResult.setTextColor(Color.parseColor("#ff9800"));
        } else {
            holder.postResult.setTextColor(Color.parseColor("#ca0608"));
        }
        holder.voteDownButton.setText(post.getVoteDown() + "");
        holder.voteUpButton.setText(post.getVoteUp() + "");

        holder.voteDownButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Post post = getItem(position);
                String user = mFirebaseUser.getUid();
                if(!post.isVoteUpContain(user)){
                    if(!post.isVoteDownContain(user)){
                        post.addVotedDownUser(user);
                        post.setVoteDown(post.getVoteDown() + 1);
                    } else {
                        post.removeVotedDownUser(user);
                        post.setVoteDown(post.getVoteDown() - 1);
                    }
                }

                final Post postCopy = post;

                ref.child("posts").orderByChild("title").equalTo(post.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                            ref.child("posts/"+key).setValue(postCopy);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.voteUpButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Post post = getItem(position);
                String user = mFirebaseUser.getUid();
                if(!post.isVoteDownContain(user)){
                    if(!post.isVoteUpContain(user)){
                        post.addVotedUpUser(user);
                        post.setVoteUp(post.getVoteUp() + 1);
                    } else {
                        post.removeVotedUpUser(user);
                        post.setVoteUp(post.getVoteUp() - 1);
                    }
                }

                final Post postCopy = post;

                ref.child("posts").orderByChild("title").equalTo(post.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child : dataSnapshot.getChildren()) {
                            String key = child.getKey();
                            ref.child("posts/"+key).setValue(postCopy);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.postDetail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(context, PostDetailViewActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;

    }

}

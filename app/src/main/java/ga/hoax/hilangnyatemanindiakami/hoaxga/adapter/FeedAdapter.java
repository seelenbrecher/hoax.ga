package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.PostDetailViewActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/21/16.
 */
public class FeedAdapter extends BaseAdapter {
    private Context mContext;
    private List<Post> posts;

    private CircularImageView imagePostStarter;
    private TextView titlePost;
    private TextView postStarter;
    private TextView datePosted;
    private ImageView postImage;
    private TextView postContent;
    private TextView postDetail;

    private CircularProgressButton voteUp;
    private CircularProgressButton voteDown;

    private UserService userService;
    private DataService dataService;

    public FeedAdapter(Context mContext, List<Post> posts) {
        this.mContext = mContext;
        this.posts = posts;
        this.userService = new UserService(mContext);
        this.dataService = new DataService(mContext);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_feed, null);
        }

        Post post = (Post) getItem(position);
        User user = userService.getUserByUsername(post.getUser());

        imagePostStarter = (CircularImageView) convertView.findViewById(R.id.imagePostStarter);
        titlePost = (TextView) convertView.findViewById(R.id.titlePost);
        postStarter = (TextView) convertView.findViewById(R.id.postStarter);
        datePosted = (TextView) convertView.findViewById(R.id.datePosted);
        postImage = (ImageView) convertView.findViewById(R.id.postImage);
        postContent = (TextView) convertView.findViewById(R.id.postContent);
        postDetail = (TextView) convertView.findViewById(R.id.postDetail);
        voteUp = (CircularProgressButton) convertView.findViewById(R.id.upVoteButton);
        voteDown = (CircularProgressButton) convertView.findViewById(R.id.downVoteButton);

        postDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = (Post) getItem(position);
                Toast.makeText(mContext, "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, PostDetailViewActivity.class);
                intent.putExtra("post", post);
                mContext.startActivity(intent);
            }
        });

        if (user.getProfileImage() != null)
            imagePostStarter.setImageBitmap(UserService.getInstance(mContext).getProfileImage(user));

        postStarter.setText(user.getName());

        Date date = new Date();
        long diff = date.getTime() - post.getDate().getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;
        if (diff >= 86400) {
            datePosted.setText(Math.round(diff/86400) + " days ago");
        } else if (diff >= 3600) {
            datePosted.setText(Math.round(diff/3600) + " hours ago");
        } else if (diff >= 60) {
            datePosted.setText(Math.round(diff/60) + " minutes ago");
        } else {
            datePosted.setText(Math.round(diff) + " seconds ago");
        }

        titlePost.setText(post.getTitle());
        postContent.setText(post.getContent());

        voteUp.setText(Integer.toString(post.getVoteUp()));
        if(post.isVoteUpContain(user)) voteUp.setProgress(100);
        voteUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Post post = (Post) getItem(position);
                User user = UserService.getInstance(mContext).getCurrentUser();
                System.out.println(post.getUser() + post.getTitle() + user.getUsername() + post.isPermissedToVote(user) + " " + !post.isVoteDownContain(user));
                if(post.isPermissedToVote(user) && !post.isVoteDownContain(user)){
                    if(!post.isVoteUpContain(user)){
                        post.addVotedUpUser(user);
                        post.setVoteUp(post.getVoteUp() + 1);
                    } else {
                        post.removeVotedUpUser(user);
                        post.setVoteUp(post.getVoteUp() - 1);
                    }
                }
                if(post.isVoteUpContain(user))
                    voteUp.setProgress(100);
                else
                    voteUp.setProgress(0);

                voteUp.setText(Integer.toString(post.getVoteUp()));
            }
        });

        voteDown.setText(Integer.toString(post.getVoteDown()));
        if(post.isVoteDownContain(user)) voteDown.setProgress(100);
        voteDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Post post = (Post) getItem(position);
                User user = UserService.getInstance(mContext).getCurrentUser();
                if(post.isPermissedToVote(user) && !post.isVoteUpContain(user)){
                    if(!post.isVoteDownContain(user)){
                        post.addVotedDownUser(user);
                        post.setVoteDown(post.getVoteDown() + 1);
                    } else {
                        post.removeVotedDownUser(user);
                        post.setVoteDown(post.getVoteDown() - 1);
                    }
                }

                if(post.isVoteDownContain(user))
                    voteDown.setProgress(100);
                else
                    voteDown.setProgress(0);
                voteDown.setText(Integer.toString(post.getVoteDown()));
            }
        });

        if (post.getPicture() != null && dataService.getPostImage(post) != null) {
            postImage.setImageBitmap(dataService.getPostImage(post));
        } else
            postImage.setImageBitmap(null);

        return convertView;
    }
}

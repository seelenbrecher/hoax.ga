package ga.hoax.hilangnyatemanindiakami.hoaxga.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 9/15/16.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    public CircularImageView imagePostStarter;
    public TextView titlePost;
    public TextView postStarter;
    public TextView datePosted;
    public ImageView postImage;
    public TextView postContent;
    public TextView postDetail;

    public Button voteUp;
    public Button voteDown;

    public PostViewHolder(View itemView) {
        super(itemView);

        imagePostStarter = (CircularImageView) itemView.findViewById(R.id.imagePostStarter);
        titlePost = (TextView) itemView.findViewById(R.id.titlePost);
        postStarter = (TextView) itemView.findViewById(R.id.postStarter);
        datePosted = (TextView) itemView.findViewById(R.id.datePosted);
        postImage = (ImageView) itemView.findViewById(R.id.postImage);
        postContent = (TextView) itemView.findViewById(R.id.postContent);
        postDetail = (TextView) itemView.findViewById(R.id.postDetail);
        voteUp = (Button) itemView.findViewById(R.id.upVoteButton);
        voteDown = (Button) itemView.findViewById(R.id.downVoteButton);
    }

    public void bindToPost(Post post, View.OnClickListener showDetailListener) {
//        if (user.getProfileImage() != null)
//            imagePostStarter.setImageBitmap(UserService.getInstance(mContext).getProfileImage(user));

        postStarter.setText("name");

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
        voteDown.setText(Integer.toString(post.getVoteDown()));

        if (post.getPicture() != null ) {
//            postImage.setImageBitmap(dataService.getPostImage(post));
        } else
            postImage.setImageBitmap(null);
    }
}

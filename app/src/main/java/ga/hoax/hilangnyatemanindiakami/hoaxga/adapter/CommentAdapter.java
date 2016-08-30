package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;
import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Comments;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by fahmi on 25/08/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private List<Comments> comments;
    private User user;
    private User loggedUser;
    private Post post;
    private Context context;
    private DataService dataService;

    public CommentAdapter(Context context, User user, Post post, List<Comments> comments){
        this.comments = comments;
        this.post = post;
        this.user = user;
        this.context = context;
        this.dataService = new DataService(this.context);
        this.loggedUser = UserService.getInstance(this.context).getCurrentUser();
    }

    @Override
    public int getItemCount() {
        return comments.size()+1;
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        CommentViewHolder commentViewHolder;
        if(viewType == 1) {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_card_view, viewGroup, false);
            commentViewHolder = new CommentViewHolder(v, 1);
        } else {
            v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_detail_header_view, viewGroup, false);
            commentViewHolder = new CommentViewHolder(v);
        }

        return commentViewHolder;
    }

    public String getMoment(Date date){
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;

        String moment = "";

        if (diff >= 86400) {
            moment = Math.round(diff/86400) + " days ago";
        } else if (diff >= 3600) {
            moment = Math.round(diff/3600) + " hours ago";
        } else if (diff >= 60) {
            moment = Math.round(diff/60) + " minutes ago";
        } else {
            moment = Math.round(diff) + " seconds ago";
        }
        return moment;
    }

    @Override
    public void onBindViewHolder(final CommentViewHolder commentViewHolder, int i) {
        if(i == 0) {
            commentViewHolder.postStarter.setText(user.getName());
            commentViewHolder.titlePost.setText(post.getTitle());
            commentViewHolder.datePosted.setText(getMoment(post.getDate()));
            commentViewHolder.postContent.setText(post.getContent());

            if (user.getProfileImage() != null)
                commentViewHolder.imagePostStarter.setImageBitmap(UserService.getInstance(context).getProfileImage(user));


            if (post.getPicture() != null && dataService.getPostImage(post) != null) {
                commentViewHolder.postImage.setImageBitmap(dataService.getPostImage(post));
            } else
                commentViewHolder.postImage.setImageBitmap(null);

            commentViewHolder.voteUp.setText(Integer.toString(post.getVoteUp()));
            commentViewHolder.voteUp.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    System.out.println(post.getUser() + loggedUser.getUsername() + post.isPermissedToVote(loggedUser) + " " + !post.isVoteDownContain(loggedUser));
                    if(post.isPermissedToVote(loggedUser) && !post.isVoteDownContain(loggedUser)){
                        if(!post.isVoteUpContain(loggedUser)){
                            post.addVotedUpUser(loggedUser);
                            post.setVoteUp(post.getVoteUp() + 1);
                        } else {
                            post.removeVotedUpUser(loggedUser);
                            post.setVoteUp(post.getVoteUp() - 1);
                        }
                    }


                    commentViewHolder.voteUp.setText(Integer.toString(post.getVoteUp()));
                }
            });

            commentViewHolder.voteDown.setText(Integer.toString(post.getVoteDown()));
            if(post.isVoteDownContain(loggedUser)) commentViewHolder.voteDown.setProgress(100);
            commentViewHolder.voteDown.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(post.isPermissedToVote(loggedUser) && !post.isVoteUpContain(loggedUser)){
                        if(!post.isVoteDownContain(loggedUser)){
                            post.addVotedDownUser(loggedUser);
                            post.setVoteDown(post.getVoteDown() + 1);
                        } else {
                            post.removeVotedDownUser(loggedUser);
                            post.setVoteDown(post.getVoteDown() - 1);
                        }
                    }

                    if(post.isVoteDownContain(loggedUser))
                        commentViewHolder.voteDown.setProgress(100);
                    else
                        commentViewHolder.voteDown.setProgress(0);
                    commentViewHolder.voteDown.setText(Integer.toString(post.getVoteDown()));
                }
            });

            commentViewHolder.commentBox.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if(event.getAction() == MotionEvent.ACTION_UP) {
                        if(event.getRawX() >= (commentViewHolder.commentBox.getRight()
                                - commentViewHolder.commentBox.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                            String comment = (String) commentViewHolder.commentBox.getText().toString();
                            commentViewHolder.commentBox.setText("");
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                            dataService.addComment(comment, user, post.getId(), addCommentListener);

                            return true;
                        }
                    }
                    return false;
                }
            });

        } else {
            i--;
            System.out.println(comments.get(i).getContent());
            commentViewHolder.postedUser.setText(comments.get(i).getUser().getName());
            commentViewHolder.commentContent.setText(comments.get(i).getContent());
            commentViewHolder.commentDate.setText(getMoment(comments.get(i).getDate()));
        }
    }

    DataService.AddCommentListener addCommentListener = new DataService.AddCommentListener() {

        @Override
        public void onResponse(boolean added, String message, Comments comment) {
            if (added) {
                notifyDataSetChanged();
                comments.add(comment);
                notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        //userData
        private CircularImageView imagePostStarter;
        private TextView titlePost;
        private TextView postStarter;
        private TextView datePosted;
        private EditText commentBox;
        private TextView postContent;
        private ImageView postImage;
        private Button voteUp;
        private CircularProgressButton voteDown;

        // comments
        private CardView commentCard;
        private TextView postedUser;
        private TextView commentDate;
        private TextView commentContent;



        CommentViewHolder(View itemView) {
            super(itemView);

            imagePostStarter = (CircularImageView) itemView.findViewById(R.id.imagePostStarter);
            postStarter = (TextView) itemView.findViewById(R.id.postStarter);
            titlePost = (TextView) itemView.findViewById(R.id.titlePost);
            datePosted = (TextView) itemView.findViewById(R.id.datePosted);
            postContent = (TextView) itemView.findViewById(R.id.postContent);
            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            commentBox = (EditText) itemView.findViewById(R.id.commentBox);
            voteUp = (Button) itemView.findViewById(R.id.upVoteButton);
            voteDown = (CircularProgressButton) itemView.findViewById(R.id.downVoteButton);
        }

        CommentViewHolder(View itemView, int status) {
            super(itemView);
            commentCard = (CardView) itemView.findViewById(R.id.commentCard);
            postedUser = (TextView) itemView.findViewById(R.id.postedUser);
            commentDate = (TextView) itemView.findViewById(R.id.datePosted);
            commentContent = (TextView) itemView.findViewById(R.id.postContent);
        }
    }

}

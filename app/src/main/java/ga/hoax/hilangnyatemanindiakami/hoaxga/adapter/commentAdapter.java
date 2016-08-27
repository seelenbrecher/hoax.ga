package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Comments;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by fahmi on 25/08/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private List<Comments> comments;
    private Post post;
    private Context context;

    public CommentAdapter(Context context, Post post, List<Comments> comments){
        this.comments = comments;
        this.post = post;
        this.context = context;
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

    @Override
    public void onBindViewHolder(CommentViewHolder commentViewHolder, int i) {
        if(i == 0) {
            commentViewHolder.postStarter.setText(post.getUser());
            commentViewHolder.titlePost.setText(post.getTitle());
        } else {
            i--;
            System.out.println(comments.get(i).getContent());
            commentViewHolder.postedUser.setText(comments.get(i).getUser().getName());
            commentViewHolder.postContent.setText(comments.get(i).getContent());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        //userData
        private TextView titlePost;
        private TextView postStarter;
        private TextView datePosted;

        // comments
        private CardView commentCard;
        private TextView postedUser;
        private TextView date;
        private TextView postContent;



        CommentViewHolder(View itemView) {
            super(itemView);

            postStarter = (TextView) itemView.findViewById(R.id.postStarter);
            titlePost = (TextView) itemView.findViewById(R.id.titlePost);
        }

        CommentViewHolder(View itemView, int status) {
            super(itemView);
            commentCard = (CardView) itemView.findViewById(R.id.commentCard);
            postedUser = (TextView) itemView.findViewById(R.id.postedUser);
            postContent = (TextView) itemView.findViewById(R.id.postContent);
        }
    }

}

package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Comments;

/**
 * Created by fahmi on 25/08/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private  List<Comments> comments;

    public CommentAdapter(List<Comments> comments){
        this.comments = comments;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_card_view, viewGroup, false);
        CommentViewHolder commentViewHolder = new CommentViewHolder(v);
        return commentViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.postedUser.setText(comments.get(i).getUser().getName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        private CardView commentCard;
        private TextView postedUser;
        private TextView date;
        private TextView postContent;

        CommentViewHolder(View itemView) {
            super(itemView);
            commentCard = (CardView) itemView.findViewById(R.id.commentCard);
            postedUser = (TextView) itemView.findViewById(R.id.postedUser);
        }
    }

}

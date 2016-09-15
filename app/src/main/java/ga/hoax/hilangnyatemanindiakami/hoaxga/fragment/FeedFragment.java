package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.PostDetailViewActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;
import ga.hoax.hilangnyatemanindiakami.hoaxga.viewholder.PostViewHolder;

/**
 * Created by kuwali on 8/21/16.
 */
public class FeedFragment extends Fragment {
    private final String TAG = "Feed Fragment";

    private List<Post> mPostList = new ArrayList<Post>();
    private FeedAdapter mFeedAdapter;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mFeedRecyclerView;

    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mFeedRecyclerView = (RecyclerView) view.findViewById(R.id.feedsRecyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        mFeedAdapter = new FeedAdapter(getContext(), mPostList);
//        DataService.getInstance(getContext()).getPostList(getPostListListener);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Query postsQuery = getQuery(mDatabase);
        Log.d(TAG, postsQuery.toString());
        mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.layout_list_feed,
                PostViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, final Post model, int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity

                        Intent intent = new Intent(getActivity(), PostDetailViewActivity.class);
                        intent.putExtra("post", model);
                        intent.putExtra("user", new User());
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
//                if (model.stars.containsKey(getUid())) {
//                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
//                } else {
//                    viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
//                }

                // Bind Post to ViewHolder, setting OnClickListener for the star button
//                viewHolder.bindToPost(model, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View starView) {
//                        // Need to write to both places the post is stored
//                        DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
//                        DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());
//
//                        // Run two transactions
//                        onStarClicked(globalPostRef);
//                        onStarClicked(userPostRef);
//                    }
//                });
            }
        };



        mFeedRecyclerView.setAdapter(mAdapter);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
//        mSwipeRefreshLayout.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        for(Post post: mPostList){
//                            if(post.getPicture() != null) Log.i(post.getTitle(), post.getPicture());
//                            else
//                                Log.i(post.getTitle(), "null");
//                        }
//                        DataService.getInstance(getContext()).getPostList(getPostListListener);
//                    }
//                }
//        );
    }

    private Query getQuery(DatabaseReference mDatabase) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = mDatabase.child("posts")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }


//    DataService.GetPostListListener getPostListListener = new DataService.GetPostListListener() {
//        @Override
//        public void onResponse(boolean success, String message, List<Post> postList) {
//            if (success) {
//                FeedFragment.this.mPostList.clear();
//                mFeedAdapter.notifyDataSetChanged();
//                FeedFragment.this.mPostList.addAll(postList);
//                mFeedAdapter.notifyDataSetChanged();
//                if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);
//            }
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
}

package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ga.hoax.hilangnyatemanindiakami.hoaxga.PostDetailViewActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.PostAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;
import ga.hoax.hilangnyatemanindiakami.hoaxga.viewholder.PostViewHolder;

/**
 * Created by kuwali on 8/21/16.
 */
public class FeedFragment extends Fragment {
    private final String TAG = "Feed Fragment";

    private PostAdapter mPostAdapter;
    private ListView mPostListView;

    public FeedFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        mPostAdapter = new PostAdapter(getActivity(), R.layout.layout_list_feed, new ArrayList<Post>());
        mPostListView = (ListView) rootView.findViewById(R.id.feedListView);
        mPostListView.setAdapter(mPostAdapter);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    updateListView(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

        return rootView;
    }

    private void updateListView(Post post){
        mPostAdapter.insert(post, 0);
    }
}

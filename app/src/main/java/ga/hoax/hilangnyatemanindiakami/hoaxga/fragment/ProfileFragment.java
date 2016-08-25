package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pools;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/21/16.
 */
public class ProfileFragment extends Fragment {
    //constants
    private final boolean CHECKED_POST = true;
    private final boolean CONTRIBUTED_POST = false;

    //user related
    private User user;

    //posts related
    private boolean selectedFeed = true; //default to your checked post
    private List<Post> checkedPosts = new ArrayList<Post>();
    private List<Post> contributedPosts = new ArrayList<Post>();
    private FeedAdapter adapter;

    //user related
    private User myUser;

    //view related
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //user data
        user = UserService.getInstance(getContext()).getCurrentUser();
        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePicture);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView job = (TextView) view.findViewById(R.id.job);
        TextView quote = (TextView) view.findViewById(R.id.quote);

        name.setText(user.getUsername());
        job.setText(user.getJob());

        //button for selecting feed type
        Button checkedPostButton = (Button) view.findViewById(R.id.checkedPostButton);
        Button contributedPostButton = (Button) view.findViewById(R.id.contributedPostButton);

        checkedPostButton.setOnClickListener(checkedPostClickListener);
        contributedPostButton.setOnClickListener(conributedPostClickListener);


        //post list view data
        ListView selectedPostCategoryListView = (ListView) view.findViewById(R.id.selectedPostCategoryListView);
        adapter = new FeedAdapter(getContext(), checkedPosts);
        DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, user);
        selectedPostCategoryListView.setAdapter(adapter);

        return view;
    }

    private View.OnClickListener  checkedPostClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedFeed == CHECKED_POST) {
                //status don't change, do nothing
            } else {
                selectedFeed = CHECKED_POST;
                adapter = new FeedAdapter(getContext(), checkedPosts);
                DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, user);
            }
        }
    };

    private View.OnClickListener  conributedPostClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(selectedFeed == CONTRIBUTED_POST) {
                //status don't change, do nothing
            } else {
                selectedFeed = CONTRIBUTED_POST;
                adapter = new FeedAdapter(getContext(), contributedPosts);
                DataService.getInstance(getContext()).getPostRelatedtoContributedUser(getPostListListener, user);
            }
        }
    };


    DataService.GetPostListListener getPostListListener = new DataService.GetPostListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Post> postList) {
            if (success) {
                List<Post> posts = selectedFeed == CHECKED_POST ? checkedPosts : contributedPosts;
                posts.clear();
                adapter.notifyDataSetChanged();
                posts.addAll(postList);

                adapter.notifyDataSetChanged();
            }
        }
    };
}

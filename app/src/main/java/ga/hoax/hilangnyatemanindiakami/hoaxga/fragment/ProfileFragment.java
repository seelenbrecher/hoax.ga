package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
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

    //mCurrentUser related
    private User mCurrentUser;

    //posts related
    private boolean mSelectedFeedIndicator = true; //default to your checked post
    private List<Post> mCheckedPostsList = new ArrayList<Post>();
    private List<Post> mContributedPostsList = new ArrayList<Post>();
    private int mCheckedPostsCount;
    private int mContributedPostsCount;
    private FeedAdapter mFeedAdapter;

    //view related
    private View view;
    private LinearLayout mCheckedPostLayout;
    private LinearLayout mContributedPostLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        //mCurrentUser data
        mCurrentUser = UserService.getInstance(getContext()).getCurrentUser();
        CircularImageView profilePicture = (CircularImageView) view.findViewById(R.id.profilePicture);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView job = (TextView) view.findViewById(R.id.job);
        TextView quote = (TextView) view.findViewById(R.id.quote);
        CircularProgressButton followersButton = (CircularProgressButton) view.findViewById(R.id.followersButton);
        CircularProgressButton followingsButton = (CircularProgressButton) view.findViewById(R.id.followingsButton);

        TextView checkedPostButton = (TextView) view.findViewById(R.id.checkedPostButton);
        TextView contributedPostButton = (TextView) view.findViewById(R.id.contributedPostButton);

        name.setText(mCurrentUser.getName());
        job.setText(mCurrentUser.getJob());
        quote.setText(mCurrentUser.getQuote());

        mCheckedPostsCount = DataService.getInstance(getContext()).countCheckedPosts(mCurrentUser);
        mContributedPostsCount = DataService.getInstance(getContext()).countContributedPosts(mCurrentUser);
        checkedPostButton.setText(Integer.toString(mCheckedPostsCount));
        contributedPostButton.setText(Integer.toString(mContributedPostsCount));

        if(mCurrentUser.getProfileImage() != null){
            profilePicture.setImageBitmap(UserService.getInstance(getContext()).getProfileImage(mCurrentUser));
        }
        else{
            profilePicture.setBackground(getResources().getDrawable(R.drawable.default_profpic));
        }
        followersButton.setText(mCurrentUser.getFollowersNumber()+" followers");
        followingsButton.setText(mCurrentUser.getFollowingsNumber()+" followings");


        //layout for selecting feed type
        mCheckedPostLayout = (LinearLayout) view.findViewById(R.id.checkedPostLayout);
        mContributedPostLayout = (LinearLayout) view.findViewById(R.id.contributedPostLayout);

        mCheckedPostLayout.setOnClickListener(checkedPostClickListener);
        mContributedPostLayout.setOnClickListener(conributedPostClickListener);


        //post list view data
        ListView selectedPostCategoryListView = (ListView) view.findViewById(R.id.selectedPostCategoryListView);
        mFeedAdapter = new FeedAdapter(getContext(), mCheckedPostsList);
        DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, mCurrentUser);
        selectedPostCategoryListView.setAdapter(mFeedAdapter);

        return view;
    }

    private View.OnClickListener  checkedPostClickListener = new View.OnClickListener() {
        @SuppressWarnings("ResourceAsColor")
        @Override
        public void onClick(View v) {
            mCheckedPostLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mContributedPostLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            if (mSelectedFeedIndicator == CHECKED_POST) {
                //status don't change, do nothing
            } else {
                mSelectedFeedIndicator = CHECKED_POST;
                mFeedAdapter = new FeedAdapter(getContext(), mCheckedPostsList);
                DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, mCurrentUser);
            }
        }
    };

    private View.OnClickListener  conributedPostClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mContributedPostLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mCheckedPostLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            if (mSelectedFeedIndicator == CONTRIBUTED_POST) {
                //status don't change, do nothing
            } else {
                mSelectedFeedIndicator = CONTRIBUTED_POST;
                mFeedAdapter = new FeedAdapter(getContext(), mContributedPostsList);
                DataService.getInstance(getContext()).getPostRelatedToContributedUser(getPostListListener, mCurrentUser);
            }
        }
    };


    DataService.GetPostListListener getPostListListener = new DataService.GetPostListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Post> postList) {
            if (success) {
                List<Post> posts = mSelectedFeedIndicator == CHECKED_POST ? mCheckedPostsList : mContributedPostsList;
                posts.clear();
                mFeedAdapter.notifyDataSetChanged();
                posts.addAll(postList);
                mFeedAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
}

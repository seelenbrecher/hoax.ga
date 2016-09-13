package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/21/16.
 */
public class FeedFragment extends Fragment {
    private List<Post> mPostList = new ArrayList<Post>();
    private FeedAdapter mFeedAdapter;
    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mTimelineListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        mTimelineListView = (ListView) view.findViewById(R.id.feedsListView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFeedAdapter = new FeedAdapter(getContext(), mPostList);
        DataService.getInstance(getContext()).getPostList(getPostListListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        mTimelineListView.setAdapter(mFeedAdapter);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        for(Post post: mPostList){
                            if(post.getPicture() != null) Log.i(post.getTitle(), post.getPicture());
                            else
                                Log.i(post.getTitle(), "null");
                        }
                        DataService.getInstance(getContext()).getPostList(getPostListListener);
                    }
                }
        );
    }

    DataService.GetPostListListener getPostListListener = new DataService.GetPostListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Post> postList) {
            if (success) {
                FeedFragment.this.mPostList.clear();
                mFeedAdapter.notifyDataSetChanged();
                FeedFragment.this.mPostList.addAll(postList);
                mFeedAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
}

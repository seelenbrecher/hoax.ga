package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.PostDetailViewActivity;
import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/21/16.
 */
public class FeedFragment extends Fragment {
    private List<Post> posts = new ArrayList<Post>();
    private FeedAdapter adapter;
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView timelineListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        timelineListView = (ListView) view.findViewById(R.id.feedsListView);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new FeedAdapter(getContext(),posts);
        DataService.getInstance(getContext()).getPostList(getPostListListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        timelineListView.setAdapter(adapter);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        for(Post post: posts){
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
                posts.clear();
                adapter.notifyDataSetChanged();
                posts.addAll(postList);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }
}

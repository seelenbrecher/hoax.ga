package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        ListView timelineListView = (ListView) view.findViewById(R.id.feedsListView);
        timelineListView.setAdapter(adapter);
        timelineListView.setOnItemClickListener(onItemClickListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        DataService.getInstance(getContext()).getPostList(getPostListListener);
                    }
                }
        );


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new FeedAdapter(getContext(),posts);
        DataService.getInstance(getContext()).getPostList(getPostListListener);
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


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), PostDetailViewActivity.class);
            startActivity(intent);
        }
    };
}

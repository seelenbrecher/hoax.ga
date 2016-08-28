package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;
import mabbas007.tagsedittext.TagsEditText;

/**
 * Created by kuwali on 8/27/16.
 */
public class DiscoverFragment extends Fragment {
    private View view;

    private TagsEditText tagsEdit;

    private List<Post> searchResult = new ArrayList<Post>();
    private FeedAdapter adapter;

    private User user;

    private boolean selectedFeed = true; //default to your checked post

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_discover, container, false);

        tagsEdit = (TagsEditText) view.findViewById(R.id.tagsText);

        adapter = new FeedAdapter(getContext(), searchResult);

        user = UserService.getInstance(getContext()).getCurrentUser();

        //post list view data
        ListView selectedPostCategoryListView = (ListView) view.findViewById(R.id.selectedPostCategoryListView);
        adapter = new FeedAdapter(getContext(), searchResult);
        DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, user);
        selectedPostCategoryListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discover, menu);
    }

    DataService.GetPostListListener getPostListListener = new DataService.GetPostListListener() {
        @Override
        public void onResponse(boolean success, String message, List<Post> postList) {
            if (success) {
                List<Post> posts = searchResult;
                posts.clear();
                adapter.notifyDataSetChanged ();
                posts.addAll(postList);
                adapter.notifyDataSetChanged();
            }
        }
    };
}

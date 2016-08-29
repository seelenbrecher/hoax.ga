package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

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
    private boolean currentStatus = false;

    private View view;

    //tags
    private TagsEditText tagsEdit;
    private ImageView tagsButton;

    //search in action bar
    private MenuItem searchItem;
    private SearchView searchView;

    private List<Post> searchResult = new ArrayList<Post>();
    private FeedAdapter adapter;

    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_discover, container, false);

        tagsEdit = (TagsEditText) view.findViewById(R.id.tagsText);

        tagsButton = (ImageView) view.findViewById(R.id.tagsButton);

        adapter = new FeedAdapter(getContext(), searchResult);

        user = UserService.getInstance(getContext()).getCurrentUser();

        //post list view data
        ListView selectedPostCategoryListView = (ListView) view.findViewById(R.id.selectedPostCategoryListView);
        adapter = new FeedAdapter(getContext(), searchResult);
        DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, user);
        selectedPostCategoryListView.setAdapter(adapter);

        //initial tagsEdit
        tagsEdit.setEnabled(!currentStatus);
        tagsEdit.setInputType(InputType.TYPE_NULL);
        tagsEdit.setFocusableInTouchMode(!currentStatus);

        //tagsButton listener
        tagsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //add tags
                if(!currentStatus){
                    Log.i("ASD", "ASD");
                    tagsEdit.setEnabled(!currentStatus);
                    tagsEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    tagsEdit.setFocusableInTouchMode(!currentStatus);
                    tagsButton.setImageResource(R.drawable.check_circle_color_accent);
                }
                //search tags
                else{
                    tagsEdit.setEnabled(!currentStatus);
                    tagsEdit.setInputType(InputType.TYPE_NULL);
                    tagsEdit.setFocusableInTouchMode(!currentStatus);
                    tagsButton.setImageResource(R.drawable.add_circle_color_accent);

                    //TODO refresh adapter
                }
                currentStatus = !currentStatus;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_discover, menu);

        //TODO search action diapain gitu gangerti
        //https://developer.android.com/training/appbar/action-views.html

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

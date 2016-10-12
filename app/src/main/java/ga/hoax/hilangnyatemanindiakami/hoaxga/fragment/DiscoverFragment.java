package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private boolean mYourInterestEditableIndicator = false;

    private View view;

    //tags
    private TagsEditText mTagsEdit;
    private ImageView mTagsButton;

    //search in action bar
    private MenuItem mSearchItem;
    private SearchView mSearchView;

    private List<Post> mSearchResultList = new ArrayList<Post>();
    private ListView mSelectedPostCategoryListView;
    private FeedAdapter mFeedAdapter;

    private User mCurrentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_discover, container, false);

        mTagsEdit = (TagsEditText) view.findViewById(R.id.tagsText);
        mTagsButton = (ImageView) view.findViewById(R.id.tagsButton);

        //post list view data
        mSelectedPostCategoryListView = (ListView) view.findViewById(R.id.selectedPostCategoryListView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        mFeedAdapter = new FeedAdapter(getContext(), mSearchResultList);
//        mCurrentUser = UserService.getInstance(getContext()).getCurrentUser();

//        DataService.getInstance(getContext()).getPostRelatedToCheckedUser(getPostListListener, mCurrentUser);
    }

    @Override
    public void onStart() {
        super.onStart();

        mSelectedPostCategoryListView.setAdapter(mFeedAdapter);

        //initial mTagsEdit
        mTagsEdit.setEnabled(!mYourInterestEditableIndicator);
        mTagsEdit.setInputType(InputType.TYPE_NULL);
        mTagsEdit.setFocusableInTouchMode(!mYourInterestEditableIndicator);

        //mTagsButton listener
        mTagsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //add tags
                if(!mYourInterestEditableIndicator){
                    mTagsEdit.setEnabled(!mYourInterestEditableIndicator);
                    mTagsEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                    mTagsEdit.setFocusableInTouchMode(!mYourInterestEditableIndicator);
                    mTagsButton.setImageResource(R.drawable.check_circle_color_accent);
                }
                //search tags
                else{
                    mTagsEdit.setEnabled(!mYourInterestEditableIndicator);
                    mTagsEdit.setInputType(InputType.TYPE_NULL);
                    mTagsEdit.setFocusableInTouchMode(!mYourInterestEditableIndicator);
                    mTagsButton.setImageResource(R.drawable.add_circle_color_accent);

                    //TODO refresh mFeedAdapter
                }
                mYourInterestEditableIndicator = !mYourInterestEditableIndicator;
            }
        });
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
                List<Post> posts = mSearchResultList;
                posts.clear();
                mFeedAdapter.notifyDataSetChanged ();
                posts.addAll(postList);
                mFeedAdapter.notifyDataSetChanged();
            }
        }
    };
}

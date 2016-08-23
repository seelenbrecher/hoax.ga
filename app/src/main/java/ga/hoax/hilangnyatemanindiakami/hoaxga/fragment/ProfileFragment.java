package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.FeedAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/21/16.
 */
/*public class ProfileFragment extends Fragment {
    //constants
    private final boolean CHECKED_POST = true;
    private final boolean CONTRIBUTED_POST = false;

    //user related
    private User user;

    //posts related
    private boolean selectedFeed = true;
    private List<Post> checkedPosts = new ArrayList<Post>();
    private List<Post> contributedPosts = new ArrayList<Post>();
    private FeedAdapter adapter;

    //view related
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        buildData();

        ListView timelineListView = (ListView) view.findViewById(R.id.feedsListView);
        timelineListView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new FeedAdapter(getContext(),posts);
    }

    private void buildData(List<Post> posts) {
        User user1 = new User();
        user1.setId(1);
        user1.setName("name1");
        User user2 = new User();
        user2.setId(2);
        user2.setName("name2");
        User user3 = new User();
        user3.setId(3);
        user3.setName("name3");
        User user4 = new User();
        user4.setId(4);
        user4.setName("name4");
        User user5 = new User();
        user5.setId(5);
        user5.setName("name5");
        Post post1 = new Post(1, "Bom ketiga dalam teror sarinah", user1, new Date(100), "Setelah ledakan pertama di dekat stasiun cawang, teror bom terus berkelanjutan, kali ini di dekat si bodoh yang sedang membodohkan kebodohannya");
        Post post2 = new Post(2, "15 Orang Meninggal Dunia Karena Terjebak Macet di Brebes", user2, new Date(200), "Hari raya sapi tidak lepas dari yang namanya kebodohan. Sebagian besar sapi pun menjadi bodoh.");
        Post post3 = new Post(3, "Title 3", user3, new Date(300), "Content will be long enough for the content ya it is long enough for now but I dont know in the future");
        Post post4 = new Post(4, "Title 4", user4, new Date(400), "Content will be long enough for the content ya it is long enough for now but I dont know in the future");
        Post post5 = new Post(5, "Title 5", user5, new Date(500), "Content will be long enough for the content ya it is long enough for now but I dont know in the future");
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
    }
}
*/
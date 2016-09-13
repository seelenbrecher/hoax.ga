package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.NotificationAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Notification;

/**
 * Created by kuwali on 8/27/16.
 */
public class NotificationFragment extends Fragment {
    private View view;
    private List<Notification> mNotificationList = new ArrayList<>();
    private ListView mNotificationListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NotificationAdapter mNotifictionAdapter;

    protected String mCurrentUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        mNotificationListView = (ListView) view.findViewById(R.id.notificationListView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mNotifictionAdapter = new NotificationAdapter(context, mNotificationList);
        mCurrentUsername = UserService.getInstance(context).getCurrentUser().getUsername();
        DataService.getInstance(context).getNotificationList(mCurrentUsername, getNotificationListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        mNotificationListView.setAdapter(mNotifictionAdapter);

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        DataService.getInstance(getContext()).getNotificationList(mCurrentUsername, getNotificationListener);
                    }
                }
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    DataService.GetNotificationListener getNotificationListener = new DataService.GetNotificationListener() {
        @Override
        public void onResponse(boolean success, String message, List<Notification> notifications) {
            if (success) {
                mNotificationList.clear();
                mNotifictionAdapter.notifyDataSetChanged();
                if (notifications != null) mNotificationList.addAll(notifications);
                mNotifictionAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(false);
            }
        }
    };
}

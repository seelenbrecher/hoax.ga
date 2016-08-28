package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.adapter.NotificationAdapter;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Notification;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/27/16.
 */
public class NotificationFragment extends Fragment {
    private View view;
    private List<Notification> notificationList = new ArrayList<>();
    private ListView notificationListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NotificationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListView = (ListView) view.findViewById(R.id.notificationListView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter  = new NotificationAdapter(context, notificationList);
        DataService.getInstance(context).getNotificationList(getNotificationListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        notificationListView.setAdapter(adapter);
        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        DataService.getInstance(getContext()).getNotificationList(getNotificationListener);
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
                notificationList.clear();
                adapter.notifyDataSetChanged();
                if (notifications != null) notificationList.addAll(notifications);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
            }
        }
    };
}

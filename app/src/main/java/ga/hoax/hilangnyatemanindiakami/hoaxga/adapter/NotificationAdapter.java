package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;
import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.UserService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.DataService;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Notification;
import ga.hoax.hilangnyatemanindiakami.hoaxga.data.Post;

/**
 * Created by kuwali on 8/28/16.
 */
public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private List<Notification> notificationList;
    private ImageView notificationImageView;
    private TextView userTextView;
    private TextView typeTextView;
    private TextView postTextView;
    private TextView voteTextView;
    private TextView dateTextView;

    private DataService dataService;
    private UserService userService;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.dataService = new DataService(context);
        this.userService = new UserService(context);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_list_notification, null);
        }

        notificationImageView = (ImageView) convertView.findViewById(R.id.notificationImage);
        userTextView = (TextView) convertView.findViewById(R.id.otherUserNotificationText);
        typeTextView = (TextView) convertView.findViewById(R.id.typeNotificationText);
        postTextView = (TextView) convertView.findViewById(R.id.postNotificationText);
        voteTextView = (TextView) convertView.findViewById(R.id.voteUserNotificationText);
        dateTextView = (TextView) convertView.findViewById(R.id.dateNotificationText);

        Notification notification = (Notification) getItem(position);
        User otherUser = userService.getUserByUsername(notification.getOtherUser());
        Post post = dataService.getSinglePost(notification.getPostId());

        notificationImageView.setImageBitmap(userService.getProfileImage(otherUser));
        userTextView.setText(otherUser.getFirstName());

        switch (notification.getNotificationType()) {
            case COMMENT:
                typeTextView.setText("commented on your post");
                break;
        }

        postTextView.setText("\"" + post.getTitle() + "\"");

        Date date = new Date();
        long diff = date.getTime() - post.getDate().getTime();
        diff = diff / DateUtils.SECOND_IN_MILLIS;
        if (diff >= 86400) {
            dateTextView.setText(Math.round(diff/86400) + " days ago");
        } else if (diff >= 3600) {
            dateTextView.setText(Math.round(diff/3600) + " hours ago");
        } else if (diff >= 60) {
            dateTextView.setText(Math.round(diff/60) + " minutes ago");
        } else {
            dateTextView.setText(Math.round(diff) + " seconds ago");
        }



        return convertView;
    }
}

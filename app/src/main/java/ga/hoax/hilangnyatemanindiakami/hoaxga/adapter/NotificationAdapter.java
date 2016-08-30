package ga.hoax.hilangnyatemanindiakami.hoaxga.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
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
    private TextView userContentView;
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

        //notificationImageView = (ImageView) convertView.findViewById(R.id.notificationImage);
        userContentView = (TextView) convertView.findViewById(R.id.notificationContent);
        dateTextView = (TextView) convertView.findViewById(R.id.dateNotificationText);

        Notification notification = (Notification) getItem(position);
        User otherUser = userService.getUserByUsername(notification.getOtherUser());
        Post post = dataService.getSinglePost(notification.getPostId());

        //set notifier image
        //if(userService.getProfileImage(otherUser) != null) notificationImageView.setImageBitmap(userService.getProfileImage(otherUser));


        //notification content
        String userName;
        String commentType = "";
        String commentContent = "";
        String votePost = "";

        userName = otherUser.getFirstName();

        switch (notification.getNotificationType()) {
            case COMMENT:
                commentType = "commented on your post";
                commentContent  = "\"" + post.getTitle() + "\"";
                break;
        }

        //contentView
        int pos = 0;
        Spannable contentString = new SpannableString(userName + " " + commentType + " " + commentContent);
        contentString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), pos, pos + userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pos += userName.length()+1;
        pos += commentType.length()+1;
        contentString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), pos, pos + commentContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        pos += commentContent.length()+1;
        Spannable voteString = new SpannableString(votePost);
        voteString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorAccent)), 0, votePost.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        userContentView.setText(contentString);

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

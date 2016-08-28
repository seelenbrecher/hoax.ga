package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kuwali on 8/28/16.
 */
public class Notification implements Serializable, Comparable<Notification> {

    public enum NotificationType implements Serializable { COMMENT, FOLLOW, UPVOTE, DOWNVOTE}

    private int id;
    private NotificationType notificationType;
    private String currentUser;
    private String otherUser;
    private int postId;
    private Date date;

    public Notification() {
        this.id = 0;
        this.notificationType = null;
        this.currentUser = null;
        this.otherUser = null;
        this.postId = 0;
        this.date = null;
    }

    public Notification(int id, NotificationType notificationType, String currentUser, String otherUser, int postId, Date date) {
        this.id = id;
        this.notificationType = notificationType;
        this.currentUser = currentUser;
        this.otherUser = otherUser;
        this.postId = postId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Notification another) {
        return 0;
    }
}

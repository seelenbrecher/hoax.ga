package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import java.io.Serializable;
import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;

/**
 * Created by kuwali on 8/22/16.
 */
public class Comments implements Serializable, Comparable<Comments> {
    private int id;
    private int postId;
    private User user;
    private Date date;
    private String content;

    public Comments() {
        this.id = 0;
        this.postId = 0;
        this.user = null;
        this.date = null;
        this.content = "";
    }

    public Comments(int id, int postId, User user, Date date, String content) {
        this.id = id;
        this.postId = postId;
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(Comments another) {
        return this.id <= another.getId() ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
    }
}

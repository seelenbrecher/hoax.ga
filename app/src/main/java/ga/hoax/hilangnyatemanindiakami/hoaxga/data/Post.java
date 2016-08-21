package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;

/**
 * Created by kuwali on 8/21/16.
 */
public class Post implements Serializable, Comparable<Post>{
    private int id;
    private String title;
    private User user;
    private Date date;
    private String content;

    public Post() {
        this.id = 0;
        this.title = "";
        this.user = null;
        this.date = null;
        this.content = "";
    }

    public Post(int id, String title, User user, Date date, String content) {
        this.id = id;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public int compareTo(Post another) {
        return this.id <= another.getId() ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
    }
}

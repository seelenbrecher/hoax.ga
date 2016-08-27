package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import android.graphics.Bitmap;

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
    private String user;
    private Date date;
    private String content;
    private boolean selected;
    private String picture;

    public Post() {
        this.id = 0;
        this.title = "";
        this.user = null;
        this.date = null;
        this.content = "";
        this.selected = false;
        this.picture = null;
    }

    public Post(int id, String title, String user, Date date, String content, boolean selected) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.date = date;
        this.content = content;
        this.selected = selected;
        this.picture = null;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public int compareTo(Post another) {
        return this.id <= another.getId() ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
    }
}

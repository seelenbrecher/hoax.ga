package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;

/**
 * Created by kuwali on 8/21/16.
 */
@IgnoreExtraProperties
public class Post implements Serializable, Comparable<Post>{

    private int id;
    private String title;
    private String user;
    private Date date;
    private String content;
    private boolean selected;
    private String picture;

    private int voteUp;
    private int voteDown;

    private List<String> votedUpUser;
    private  List<String> votedDownUser;

    public Post() {
        this.id = 0;
        this.title = "";
        this.user = null;
        this.date = null;
        this.content = "";
        this.selected = false;
        this.picture = null;

        this.voteUp = 0;
        this.voteDown = 0;

        this.votedDownUser = new ArrayList<>();
        this.votedUpUser = new ArrayList<>();
    }

    public Post(int id, String title, String user, Date date, String content, boolean selected) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.date = date;
        this.content = content;
        this.selected = selected;
        this.picture = null;

        this.voteUp = 0;
        this.voteDown = 0;

        this.votedDownUser = new ArrayList<>();
        this.votedUpUser = new ArrayList<>();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("content", content);
        result.put("user", user);
        result.put("picture", picture);
        result.put("voteup", voteUp);
        result.put("votedown", voteDown);

        return result;
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

    public int getVoteDown() {
        return voteDown;
    }

    public void setVoteDown(int voteDown) {
        this.voteDown = voteDown;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public void setVoteUp(int voteUp) {
        this.voteUp = voteUp;
    }

    public boolean isPermissedToVote(User user){
        String username = user.getUsername();
        return (!this.user.equals(username));
    }

    public void addVotedUpUser(User user){
        this.votedUpUser.add(user.getUsername());
    }

    public boolean isVoteUpContain(User user){
        return votedUpUser.contains(user.getUsername());
    }

    public void removeVotedUpUser(User user){
        this.votedUpUser.remove(user.getUsername());
    }


    public void addVotedDownUser(User user){
        this.votedDownUser.add(user.getUsername());
    }

    public boolean isVoteDownContain(User user){
        return votedDownUser.contains(user.getUsername());
    }


    public void removeVotedDownUser(User user){
        this.votedDownUser.remove(user.getUsername());
    }
}

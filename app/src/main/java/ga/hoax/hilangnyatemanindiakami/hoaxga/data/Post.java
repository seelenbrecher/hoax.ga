package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import android.util.Pair;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private String id;
    private String title;
    private String user;
    private Date date;
    private String content;
    private boolean selected;
    private String picture;

    private int voteUp = 0;
    private int voteDown = 0;

    private List<String> votedUpUser = new ArrayList<>();
    private  List<String> votedDownUser = new ArrayList<>();

    public Post() {}

    public Post(String title, String user, String content, String voteUp, String voteDown) {
        this.title = title;
        this.user = user;
        this.date = new Date();
        this.content = content;
        this.selected = false;
        this.picture = null;

        this.voteUp = Integer.parseInt(voteUp);
        this.voteDown = Integer.parseInt(voteDown);
        this.votedUpUser.add("lol");
        this.votedDownUser.add("lol");
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return this.id.compareTo(another.getId()) < 0 ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
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

    public boolean isPermissedToVote(String user){
        return (!this.user.equals(user));
    }

    public List<String> getVotedUpUser(){return votedUpUser; }

    public List<String>  getVotedDownUser(){ return votedDownUser; };

    public void setVotedUpUser(List< String>  votedUp){
        this.votedUpUser = votedUp;
    }

    public void setVotedDownUser(List<String>  votedDown){
        this.votedDownUser = votedDown;
    }

    public void addVotedUpUser(String user){
        this.votedUpUser.add(user);
    }

    public boolean isVoteUpContain(String user){
        return votedUpUser.contains(user);
    }

    public void removeVotedUpUser(String user){
        this.votedUpUser.remove(user);
    }


    public void addVotedDownUser(String user){this.votedDownUser.add(user); }

    public boolean isVoteDownContain(String user){return votedDownUser.contains(user);}

    public void removeVotedDownUser(String user){
        this.votedDownUser.remove(user);
    }
}

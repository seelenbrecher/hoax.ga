package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuwali on 08/01/16.
 */
public class User implements Serializable, Comparable<User>{
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String profileImage;
    private String job;
    private String quote;
    private List<User> followings;
    private List<User> followers;
    private int userRanking;

    public User() {
        id = Integer.MIN_VALUE;
        firstName = null;
        lastName = null;
        username = null;
        password = null;
        email = null;
        profileImage = null;
        job = null;
        quote = null;
        userRanking = 0;
        followings = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() { return profileImage; }

    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getJob() { return job; }

    public void setJob(String job) { this.job = job; }

    public String getQuote() { return quote; }

    public void setQuote(String quote) { this.quote = quote; }

    public void addFollowing(User user){ this.followings.add(user); }

    public int getFollowingsNumber(){ return this.followings.size(); }

    public void removeUserFromFollowing(User user){ this.followings.remove(user); }

    public void addFollowers(User user){ this.followers.add(user); }

    public int getFollowersNumber(){return this.followers.size(); }

    public void removeUserFromFollowers(User user){this.followers.remove(user); }

    //@TODO: implement me!
    public int getUserRanking() {
        return 0;
    }

    @Override
    public int compareTo(User another) {
        return this.username.compareTo(another.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        return this.username.equals(((User)o).getUsername());
    }
}

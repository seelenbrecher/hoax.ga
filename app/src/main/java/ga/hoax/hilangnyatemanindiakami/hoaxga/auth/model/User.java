package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model;

import java.io.Serializable;

/**
 * Created by kuwali on 08/01/16.
 */
public class User implements Serializable, Comparable<User>{
    private int id;
    private String name;
    private String username;
    private String password;
    private String email;
    private String profileImage;

    public User() {
        id = Integer.MIN_VALUE;
        name = null;
        username = null;
        password = null;
        email = null;
        profileImage = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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
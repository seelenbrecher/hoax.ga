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
    private String school;
    private String kelas;
    private String university;
    private String major;
    private int poin;
    private String level;
    private int jumlahTanya;
    private int jumlahJawab;
    private int jumlahPC;
    private String aboutMe;
    private UserType userType;

    public enum UserType implements Serializable {ADIK, KAKAK}

    public User() {
        id = Integer.MIN_VALUE;
        name = null;
        username = null;
        password = null;
        email = null;
        profileImage = null;
        school = null;
        kelas = null;
        university = null;
        major = null;
        poin = 0;
        level = "Kroco";
        jumlahTanya = 0;
        jumlahJawab = 0;
        jumlahPC = 0;
        aboutMe = null;
        userType = null;
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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getPoin() {
        return poin;
    }

    public void setPoin(int poin) {
        this.poin = poin;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getJumlahTanya() {
        return jumlahTanya;
    }

    public void setJumlahTanya(int jumlahTanya) {
        this.jumlahTanya = jumlahTanya;
    }

    public int getJumlahJawab() {
        return jumlahJawab;
    }

    public void setJumlahJawab(int jumlahJawab) {
        this.jumlahJawab = jumlahJawab;
    }

    public int getJumlahPC() {
        return jumlahPC;
    }

    public void setJumlahPC(int jumlahPC) {
        this.jumlahPC = jumlahPC;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

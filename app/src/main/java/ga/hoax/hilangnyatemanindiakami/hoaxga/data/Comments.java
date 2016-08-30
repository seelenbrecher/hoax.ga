package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private int voteUp;
    private int voteDown;

    private List<User> votedUpUser;
    private  List<User> votedDownUser;

    public Comments() {
        this.id = 0;
        this.postId = 0;
        this.user = null;
        this.date = null;
        this.content = "";

        this.votedDownUser = new ArrayList<>();
        this.votedUpUser = new ArrayList<>();

        this.voteUp = 0;
        this.voteDown = 0;
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

    public int getVoteUp() { return voteUp; }

    public void setVoteUp(int voteUp) { this.voteUp = voteUp; }

    public int getVoteDown() { return voteDown; }

    public void setVoteDown(int voteDown) { this.voteDown = voteDown; }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //if not comment maker and did't vote before
    public boolean isPermissedToVote(User user){
        return ( !user.getUsername().equals(this.user.getUsername()) );
    }

    public void addVotedUpUser(User user){
        this.votedUpUser.add(user);
    }

    public boolean isVoteUpContain(User user){
        return votedUpUser.contains(user);
    }

    public void removeVotedUpUser(User user){
        this.votedUpUser.remove(user);
    }


    public void addVotedDownUser(User user){
        this.votedDownUser.add(user);
    }

    public boolean isVoteDownContain(User user){
        return votedDownUser.contains(user.getUsername());
    }


    public void removeVotedDownUser(User user){
        this.votedDownUser.remove(user);
    }

    private int rankingDiffCalculator(int cur, int other){
        if(cur > other) return 1;
        if(cur == other) return 0;
        return -1;
    }

    public int calculateVoteDiff(Comments other){
        int thisVoteFactor = voteUp - voteDown;
        int otherVoteFactor = other.getVoteUp() - other.getVoteDown();
        return rankingDiffCalculator(thisVoteFactor, otherVoteFactor);
    }

    private int calculateDateDiff(Comments other){
        return rankingDiffCalculator((int) (this.date.getTime()/1000L), (int) (other.getDate().getTime()/1000L));
    }

    private int calculateUserRankingDiff(Comments other) {
        return rankingDiffCalculator(this.user.getUserRanking(), other.getUser().getUserRanking());
    }

    @Override
    public int compareTo(Comments another) {
        //ranking algo use this following formula
        // dateDiff * 3/10 + voteFactorDIff* 5/10 + userRankingDiff * 2/10

        int voteFactorDiff = calculateVoteDiff(another);
        int dateFactorDiff = calculateDateDiff(another);
        int userRankingFactorDiff = calculateUserRankingDiff(another);

        return this.id <= another.getId() ? (this.id == another.getId() ? 0 : Integer.MAX_VALUE) : Integer.MIN_VALUE;
    }
}

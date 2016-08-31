package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model.User;

/**
 * Created by kuwali on 8/22/16.
 */
public class DataService {
    private static String FILE_NAME = "data_qa.dat";

    private static DataService instance;

    public static DataService getInstance(Context context) {
        if (instance == null) {
            instance = new DataService(context);
        }
        return instance;
    }

    private Context context;
    private List<Post> postList;
    private List<Comments> commentsList;
    private List<Notification> notificationList;
    private HashMap<String, List<Notification>> notificationHashMap;
    private int lastNotifId;

    public DataService(Context context) {
        this.context = context;
        deserializeData();
    }

    public void getNotificationList (String currentUser, GetNotificationListener getNotificationListener) {
        notificationList = notificationHashMap.get(currentUser);
        getNotificationListener.onResponse(true, "", notificationList);
    }

    public void setPostChosen(int position, boolean checked) {
        postList.get(position).setSelected(checked);
        serializeData();
    }

    public Post getSinglePost(int position) {
        return postList.get(position);
    }

    public void setPostVote(Post post){
        postList.get(post.getId()).setVoteUp(post.getVoteUp());
        postList.get(post.getId()).setVoteDown(post.getVoteDown());

    }

    public void getPostList(GetPostListListener getPostListListener) {
        List<Post> tmp = new ArrayList<>();
        tmp.addAll(postList);
        Collections.sort(tmp);
        getPostListListener.onResponse(true,"",tmp);
    }

    public int countCheckedPosts(User currentUser) {
        List<Post> filteredPostList = new ArrayList<>();

        for(Post currentPost : postList) {
            if( currentPost.getUser().equals(currentUser.getUsername()) ) filteredPostList.add(currentPost) ;
        }
        return filteredPostList.size();
    }


    public void getPostRelatedToCheckedUser(GetPostListListener getPostListListener, User currentUser) {
        List<Post> filteredPostList = new ArrayList<>();

        for(Post currentPost : postList) {
            if( currentPost.getUser().equals(currentUser.getUsername()) ) filteredPostList.add(currentPost) ;
        }

        getPostListListener.onResponse(true, "", filteredPostList);
    }

    public int countContributedPosts(User currentUser){
        List <Post> filteredPost = new ArrayList<>();
        for(Comments comment : commentsList) {

            if(comment.getUser().getUsername().equals(currentUser.getUsername())) {
                Post relatedPost = postList.get(comment.getPostId());
                filteredPost.add(relatedPost);
            }
        }

        HashSet<Post> dummySet = new HashSet<>();
        dummySet.addAll(filteredPost);
        return dummySet.size();
    }

    public void getPostRelatedToContributedUser(GetPostListListener getPostListListener, User currentUser) {
        List <Post> filteredPost = new ArrayList<>();
        for(Comments comment : commentsList) {

            if(comment.getUser().getUsername().equals(currentUser.getUsername())) {
                Post relatedPost = postList.get(comment.getPostId());
                filteredPost.add(relatedPost);
            }
        }
        getPostListListener.onResponse(true, "", filteredPost);
    }

    public void getCommentList(GetCommentListListener getCommentListListener) {

        List<Comments> relatedCommentsList = new ArrayList<Comments>();
        System.out.println(commentsList.size());

        for (Comments c : commentsList) {
            System.out.println(c.getContent());
            if (postList.get(c.getPostId()).isSelected()) {
                relatedCommentsList.add(c);
            }
        }
        Collections.sort(relatedCommentsList);

        getCommentListListener.onResponse(true,"",relatedCommentsList);

    }

    public void addPost(Post post, User userAsked, AddPostListener listener, Bitmap bitmap) {
        if (post == null) {
            if (listener != null) {
                listener.onResponse(false, "Isi komentarnya terlebih dahulu", null);
            }
        } else {
            new AddPostTask().execute(new Object[] {post, userAsked, listener, bitmap});
        }
    }

    public void addComment(String content, User userAsked, int postId, AddCommentListener listener) {
        System.out.println("anjing ini komen" + userAsked.getUsername() + content);
        if (content == null) {
            if (listener != null) {
                listener.onResponse(false, "Isi komentarnya terlebih dahulu", null);
            }
        } else if (postId < 0) {
            if (listener != null) {
                listener.onResponse(false, "Error on post select", null);
            }
        } else {
            new AddCommentTask().execute(new Object[] {content, userAsked, postId, listener});
        }
    }

    // TODO: to be update
    public Bitmap getPostImage(Post post) {
        Bitmap bitmap = null;
        FileInputStream fin = null;
        if (post.getPicture() != null) {
            try {
                fin = new FileInputStream(context.getFileStreamPath(post.getPicture()));
                bitmap = BitmapFactory.decodeStream(fin);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fin != null) {
                        fin.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        } else return null;
    }


    // TODO: to be update
    private void saveBitmapTofile(Bitmap bitmap, String name) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(context.getFileStreamPath(name));
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deserializeData() {
        File file = context.getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                commentsList = (List<Comments>) objectInputStream.readObject();
                postList = (List<Post>) objectInputStream.readObject();
                notificationHashMap = (HashMap<String, List<Notification>>) objectInputStream.readObject();
                lastNotifId = objectInputStream.readInt();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                commentsList = new ArrayList<Comments>();
                postList = new ArrayList<Post>();
                notificationHashMap = new HashMap<>();
                lastNotifId = 0;
            }
        } else {
            commentsList = new ArrayList<Comments>();
            postList = new ArrayList<Post>();
            notificationHashMap = new HashMap<>();
            lastNotifId = 0;
        }
    }

    public void serializeData() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(commentsList);
            objectOutputStream.writeObject(postList);
            objectOutputStream.writeObject(notificationHashMap);
            objectOutputStream.writeInt(lastNotifId);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AddCommentTask extends AsyncTask<Object,Void,Comments> {
        AddCommentListener listener;

        @Override
        protected Comments doInBackground(Object... objects) {
            String comment = String.valueOf(objects[0]).toString();
            User userAsked = (User) objects[1];
            int postId = (int) objects[2];
            listener = (AddCommentListener) objects[3];


            Comments commentCreated = new Comments();
            commentCreated.setId(commentsList.size());
            commentCreated.setContent(comment);
            commentCreated.setUser(userAsked);
            commentCreated.setDate(new Date());
            commentCreated.setPostId(postId);

            System.out.println(commentsList.size());

            commentsList.add(commentCreated);

            System.out.println(commentsList.size());

            String userAffected = getSinglePost(postId).getUser();
            Notification notification = new Notification(lastNotifId, Notification.NotificationType.COMMENT, userAffected, userAsked.getUsername(), postId, new Date());
            if (notificationHashMap.get(userAffected) == null) {
                List<Notification> listNotif = new ArrayList<>();
                listNotif.add(notification);
                notificationHashMap.put(userAffected, listNotif);
            } else {
                notificationHashMap.get(userAffected).add(notification);
            }


            serializeData();

            return commentCreated;
        }

        @Override
        protected void onPostExecute(Comments comments) {
            super.onPostExecute(comments);
            boolean added = (comments == null) ? false : true;
            String message = added ? "Added" : "Not Valid";
            if (listener != null)
                listener.onResponse(added,message,comments);
        }
    }

    private class AddPostTask extends AsyncTask<Object,Void,Post> {
        AddPostListener listener;

        @Override
        protected Post doInBackground(Object... objects) {
            Post post = (Post) objects[0];
            User userAsked = (User) objects[1];
            listener = (AddPostListener) objects[2];
            Bitmap bitmap = (Bitmap) objects[3];

            Post postCreated = new Post();
            postCreated.setId(postList.size());
            postCreated.setTitle(post.getTitle());
            postCreated.setContent(post.getContent());
            postCreated.setDate(new Date());
            postCreated.setUser(userAsked.getUsername());


            if (bitmap != null) {
                if (postCreated.getPicture() == null) postCreated.setPicture("post" + postList.size() + ".png");
                saveBitmapTofile(bitmap, postCreated.getPicture());
            }
            if(postCreated.getPicture() == null){
                Log.i("null kok", "null kokk");
            }

            postList.add(postCreated);

            serializeData();

            return postCreated;
        }

        @Override
        protected void onPostExecute(Post posts) {
            super.onPostExecute(posts);
            boolean added = (posts == null) ? false : true;
            String message = added ? "Added" : "Not Valid";
            if (listener != null)
                listener.onResponse(added,message,posts);
        }
    }

    public interface GetPostListListener {
        void onResponse(boolean success, String message, List<Post> posts);
    }

    public interface AddCommentListener {
        void onResponse(boolean added, String message, Comments comments);
    }

    public interface AddPostListener {
        void onResponse(boolean added, String message, Post post);
    }

    public interface GetCommentListListener {
        void onResponse(boolean success, String message, List<Comments> comments);
    }

    public interface GetNotificationListener {
        void onResponse(boolean success, String message, List<Notification> notifications);
    }
}

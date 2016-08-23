package ga.hoax.hilangnyatemanindiakami.hoaxga.data;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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

    public DataService(Context context) {
        this.context = context;
        deserializeData();
    }

    public void setPostChosen(int position, boolean checked) {
        postList.get(position).setSelected(checked);
        serializeData();
    }

    public void getPostList(GetPostListListener getPostListListener) {
        getPostListListener.onResponse(true,"",postList);
    }

    public void getCommentList(GetCommentListListener getCommentListListener) {
        if (postList.get(0).isSelected()) {
            Collections.sort(postList);
            getCommentListListener.onResponse(true,"",commentsList);
        } else {
            List<Comments> commentsList = new ArrayList<Comments>();
            for (Comments c : this.commentsList) {
                if (postList.get(c.getPostId()).isSelected()) {
                    commentsList.add(c);
                    break;
                }
            }
            Collections.sort(commentsList);
            getCommentListListener.onResponse(true,"",commentsList);
        }

    }

    public void addComment(String content, User userAsked, int postId, AddCommentListener listener) {
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

    private void deserializeData() {
        File file = context.getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                commentsList = (List<Comments>) objectInputStream.readObject();
                postList = (List<Post>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                commentsList = new ArrayList<Comments>();
                postList = new ArrayList<Post>();
            }
        } else {
            commentsList = new ArrayList<Comments>();
            postList = new ArrayList<Post>();
        }
    }

    public void serializeData() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(commentsList);
            objectOutputStream.writeObject(postList);
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
            String comment = String.valueOf(objects[0]).trim().toString();
            User userAsked = (User) objects[1];
            int postId = (int) objects[2];
            listener = (AddCommentListener) objects[3];

            Comments commentCreated = new Comments();
            commentCreated.setId(commentsList.size());
            commentCreated.setContent(comment);
            commentCreated.setUser(userAsked);
            commentCreated.setPostId(postId);

            commentsList.add(commentCreated);
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

    public interface GetPostListListener {
        void onResponse(boolean success, String message, List<Post> posts);
    }

    public interface AddCommentListener {
        void onResponse(boolean added, String message, Comments comments);
    }

    public interface GetCommentListListener {
        void onResponse(boolean success, String message, List<Comments> comments);
    }
}

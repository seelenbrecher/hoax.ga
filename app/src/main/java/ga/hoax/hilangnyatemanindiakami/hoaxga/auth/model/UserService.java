package ga.hoax.hilangnyatemanindiakami.hoaxga.auth.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuwali on 08/01/16.
 */
public class UserService {
    private static String FILE_NAME = "data_users.dat";

    private static UserService instance;

    public static UserService getInstance(Context context) {
        if(instance==null)
            instance = new UserService(context);
        return  instance;
    }

    private Context context;
    private User currentUser;
    private List<User> users;

    public UserService(Context context) {
        this.context = context;
        deserializeUsers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void login(String username, String password, LoginListener listener) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            if (listener != null)
                listener.onResponse(false,"Please enter username and password",null);
        }
        else {
            new LoginTask().execute(new Object[]{username, password, listener});
        }
    }

    public void logout() {
        setCurrentUser(null);
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    // TODO: to be update
    public void updateProfile(String name, String email, String school, String major, UpdateListener listener, Bitmap bitmap) {
        if (name == null || email == null || school == null || major == null) {
            if (listener != null) {
                listener.onResponse(false,"Jangan ada field yang kosong", null);
            }
        } else {
            new UpdateTask().execute(new Object[]{name, email, school, major, bitmap, listener});
        }
    }

    public void registerUser(String username, String password, String email, RegisterListener listener) {
        if(username == null || password == null || email == null || username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()){
            if(listener!=null)
                listener.onResponse(false,"Please enter username, password, and email",null);
        }
        else {
            new RegisterTask().execute(new Object[]{username, password, email, listener});
        }
    }

    private void deserializeUsers() {
        File file = context.getFileStreamPath(FILE_NAME);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                users = (List<User>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                users = new ArrayList<User>();
            }
        } else {
            users = new ArrayList<User>();
        }
    }

    private void serializeUser() {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(users);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: to be update
    public Bitmap getProfileImage(User user) {
        Bitmap bitmap = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(context.getFileStreamPath(user.getProfileImage()));
            bitmap = BitmapFactory.decodeStream(fin);
        }catch (Exception e) {
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
    }

    // TODO: to be update
    private void saveBitmapTofile(Bitmap bitmap,String name) {
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

    private class LoginTask extends AsyncTask<Object,Void,User> {
        LoginListener listener;

        @Override
        protected User doInBackground(Object... objects) {
            String username = String.valueOf(objects[0]).trim().toLowerCase();
            String password = String.valueOf(objects[1]).trim().toLowerCase();

            listener = (LoginListener) objects[2];

            User tempUser = new User();
            tempUser.setUsername(username);
            tempUser.setPassword(password);
            boolean exist = users.contains(tempUser);
            if (exist) {
                int index = users.indexOf(tempUser);
                User user = users.get(index);
                UserService.this.setCurrentUser(user);
                return user;
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            boolean loggedin = (user == null) ? false : true;
            String message = loggedin ? "Logged In" : "Invalid Username or Password";
            if (listener != null)
                listener.onResponse(loggedin, message, user);
        }
    }

    private class UpdateTask extends AsyncTask<Object,Void,User> {
        UpdateListener listener;

        @Override
        protected User doInBackground(Object... objects) {
            String name = String.valueOf(objects[0]).trim();
            String email = String.valueOf(objects[1]).trim();
            Bitmap bitmap = (Bitmap) objects[4];
            listener = (UpdateListener) objects[5];

            User user = getCurrentUser();

            user.setName(name);
            user.setEmail(email);

            saveBitmapTofile(bitmap, user.getProfileImage());

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            boolean updated = (user == null) ? false : true;
            String message = updated ? "Updated" : "Not Valid";
            if (listener != null)
                listener.onResponse(updated, message, user);
        }
    }

    private class RegisterTask extends AsyncTask<Object,Void,User> {
        RegisterListener listener;

        @Override
        protected User doInBackground(Object... objects) {
            String username = String.valueOf(objects[0]).trim().toLowerCase();
            String password = String.valueOf(objects[1]).trim();
            String email = String.valueOf(objects[2]).trim().toLowerCase();
            listener = (RegisterListener)objects[3];

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);

            boolean exist = users.contains(user);
            if (!exist) {
                user.setEmail(email);
                user.setId(users.size());
                users.add(user);
                serializeUser();
                UserService.this.setCurrentUser(user);
                return user;
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            boolean registered = (user == null) ? false : true;
            String message = registered ? "Registered" : "Username is unavailable";
            if (listener != null)
                listener.onResponse(registered,message,user);
        }
    }

    public interface LoginListener {
        public void onResponse(boolean loggedIn, String message, User user);
    }

    public interface RegisterListener {
        public void onResponse(boolean registered, String message, User user);
    }

    public interface UpdateListener {
        public void onResponse(boolean updated, String message, User user);
    }

    // TODO: to be update
    public interface GetUserListListener {
        public void onResponse(boolean success, String message, List<User> users);
    }
}

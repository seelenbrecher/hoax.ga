package ga.hoax.hilangnyatemanindiakami.hoaxga.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ga.hoax.hilangnyatemanindiakami.hoaxga.R;

/**
 * Created by kuwali on 8/27/16.
 */
public class NotificationFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        return view;
    }
}

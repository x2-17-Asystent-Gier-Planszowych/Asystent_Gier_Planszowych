package pwcdma.asystentgierplanszowych.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.server.UserController;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private EditText usernameText, emailText, aboutText;
    private UserController controller;

    public ProfileFragment() {
        controller = new UserController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}

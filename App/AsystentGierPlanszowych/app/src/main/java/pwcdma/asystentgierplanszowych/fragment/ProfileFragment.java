package pwcdma.asystentgierplanszowych.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.server.UserController;

import static pwcdma.asystentgierplanszowych.activity.LogInActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private EditText emailText, aboutText, currentPasswordText, newPasswordText;
    private Button saveButton;
    private UserController controller;

    public ProfileFragment() {
        controller = new UserController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        emailText = v.findViewById(R.id.email_text);
        aboutText = v.findViewById(R.id.about_text);
        currentPasswordText = v.findViewById(R.id.current_password);
        newPasswordText = v.findViewById(R.id.new_password);
        saveButton = v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new SaveButtonOnClickListener());
        return v;
    }

    private class SaveButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String email = emailText.getText().toString();
            String about = aboutText.getText().toString();
            String currentPassword = currentPasswordText.getText().toString();
            String newPassword = newPasswordText.getText().toString();

            boolean isDataValid = true;
            if (!isEmailValid(email)){
                emailText.setError(getString(R.string.error_invalid_email));
                emailText.requestFocus();
                isDataValid = false;
            }
            if (!isPasswordValid(newPassword)){
                newPasswordText.setError(getString(R.string.error_invalid_password));
                newPasswordText.requestFocus();
                isDataValid = false;
            }

            if (isDataValid)
                new ChangeProfileTask(email, about, hashPassword(newPassword))
                        .execute();
        }
    }

    private class ChangeProfileTask extends AsyncTask<Void, Void, Boolean> {

        private String email, about, password;

        public ChangeProfileTask(String email, String about, String password) {
            this.email = email;
            this.about = about;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            return null;
        }
    }
}

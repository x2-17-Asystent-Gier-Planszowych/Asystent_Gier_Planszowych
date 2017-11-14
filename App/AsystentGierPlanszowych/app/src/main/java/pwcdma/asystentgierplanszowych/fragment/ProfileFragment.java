package pwcdma.asystentgierplanszowych.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.model.User;
import pwcdma.asystentgierplanszowych.server.UserController;

import static pwcdma.asystentgierplanszowych.activity.LogInActivity.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private EditText emailText, aboutText, currentPasswordText, newPasswordText;
    private Button saveButton;
    private UserController controller;
    private User userData;
    private String login, password;

    public ProfileFragment() {
        controller = new UserController();
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userData = User.getUserData(new File(context.getFilesDir(), "user_data.json"));
        login = userData.getUsername();
        password = userData.getPassword();
    }*/

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
        /*emailText.setText(userData.getEmail());
        aboutText.setText(userData.getAbout());*/
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
            if (!TextUtils.isEmpty(currentPassword) && hashPassword(currentPassword).equals(password)){
                currentPasswordText.setError(getString(R.string.error_incorrect_password));
                currentPasswordText.requestFocus();
                isDataValid = false;
            }
            if (!TextUtils.isEmpty(newPassword) && !isPasswordValid(newPassword)){
                newPasswordText.setError(getString(R.string.error_invalid_password));
                newPasswordText.requestFocus();
                isDataValid = false;
            }

            if (isDataValid)
                new UpdateProfileTask(email, about, newPassword).execute();
        }
    }

    private class UpdateProfileTask extends AsyncTask<Void, Void, Boolean> {

        private String email, about, password;

        public UpdateProfileTask(String email, String about, String password) {
            this.email = email;
            this.about = about;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... args) {
            try {
                boolean success = true;
                success &= controller.changeEmail(login, email);
                success &= controller.changeAbout(login, about);
                if (!TextUtils.isEmpty(password))
                    success &= controller.changePassword(login, hashPassword(password));
                return success;
            } catch(IOException e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            int message;
            if (success)
                message = R.string.update_profile_success;
            else
                message = R.string.update_profile_error;

            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}

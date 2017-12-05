package pwcdma.asystentgierplanszowych.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.activity.AddGameActivity;
import pwcdma.asystentgierplanszowych.activity.EditDate;
import pwcdma.asystentgierplanszowych.activity.EditPasswd;
import pwcdma.asystentgierplanszowych.activity.LogInActivity;
import pwcdma.asystentgierplanszowych.activity.MainActivity;
import pwcdma.asystentgierplanszowych.activity.SignUpActivity;
import pwcdma.asystentgierplanszowych.activity.StartActivity;
import pwcdma.asystentgierplanszowych.model.UsefullValues;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView emailText,aboutText,logintext,edytuj_dane,edit_password;
    private Button saveButton;
    //private UserController controller;
   // private User userData;
    private String login, password;

    public ProfileFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        login = UsefullValues.name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logintext =(TextView) v.findViewById(R.id.username_text);
        edytuj_dane = (TextView) v.findViewById(R.id.edytuj_dane);
        logintext.setText(login);
        emailText =(TextView) v.findViewById(R.id.email_text);
        emailText.setText(UsefullValues.email);
        aboutText =(TextView) v.findViewById(R.id.about_text);
        aboutText.setText(UsefullValues.about);
        edytuj_dane.setOnClickListener(onClickListener);
        edit_password = (TextView) v.findViewById(R.id.edit_password);
        edit_password.setOnClickListener(onClickListener);

        return v;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.edytuj_dane:
                    startActivityForResult(new Intent(getContext(), EditDate.class),0 );
                    break;
                case R.id.edit_password:
                    startActivityForResult(new Intent(getContext(), EditPasswd.class),0 );
                    break;
            }
        }
    };


}

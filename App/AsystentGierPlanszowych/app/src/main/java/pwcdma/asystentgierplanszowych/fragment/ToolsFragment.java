package pwcdma.asystentgierplanszowych.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import pwcdma.asystentgierplanszowych.R;


public class ToolsFragment extends Fragment {

    private static final String TAG = ToolsFragment.class.getSimpleName();

    private ImageView mDiceImageButton;
    private ImageView mTimerImageButton;
    private ImageView mTableImageButton;

    private OnFragmentInteractionListener mListener;

    public ToolsFragment() {
        Log.d(TAG, "ToolsFragment: ");
    }

    public static ToolsFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        ToolsFragment fragment = new ToolsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        findViews(view);
        setOnClickListeners();
        return view;
    }

    private void setOnClickListeners(){
        mTimerImageButton.setOnClickListener(onClickListenerTimerBtn);
        mDiceImageButton.setOnClickListener(onClickListenerDiceBtn);
        mTableImageButton.setOnClickListener(getOnClickListenerTableBtn);
    }

    private View.OnClickListener onClickListenerDiceBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.toolsLL, new DiceFragment(), "DICE_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };


    private View.OnClickListener onClickListenerTimerBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.toolsLL, new TimerFragment(), "TIMER_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    private View.OnClickListener getOnClickListenerTableBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.toolsLL, new TableFragment(), "TIMER_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    private void findViews(View view) {
        mDiceImageButton = view.findViewById(R.id.toolsFragmentDiceButton);
        mTimerImageButton = view.findViewById(R.id.toolsFragmentTimerButton);
        mTableImageButton = view.findViewById(R.id.toolsFragmentTableButton);
    }

    public void onButtonPressed(Uri uri) {
        Log.d(TAG, "onButtonPressed: ");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

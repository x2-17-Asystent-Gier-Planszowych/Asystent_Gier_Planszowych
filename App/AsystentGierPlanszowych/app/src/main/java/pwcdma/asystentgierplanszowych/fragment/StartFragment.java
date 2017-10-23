package pwcdma.asystentgierplanszowych.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pwcdma.asystentgierplanszowych.model.StartViewPagerItem;
import pwcdma.asystentgierplanszowych.R;

public class StartFragment extends android.support.v4.app.Fragment {
    private final String TAG = StartFragment.class.getSimpleName();

    private ImageView mIvImage;
    private TextView mTvText;


    private static String ARG_FRAGMENT_ITEM_TEXT = "ARG_FRAGMENT_ITEM_TEXT";
    private static String ARG_FRAGMENT_ITEM_IMAGE_ID = "ARG_FRAGMENT_ITEM_IMAGE_ID";

    private OnFragmentInteractionListener mListener;

    public StartFragment() {

    }

    public static StartFragment newInstance(StartViewPagerItem startViewPagerItem) {
        StartFragment fragment = new StartFragment();
        fragment.setArguments(createBundle(startViewPagerItem));
        return fragment;
    }

    private static Bundle createBundle(StartViewPagerItem startViewPagerItem) {
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_ITEM_IMAGE_ID, startViewPagerItem.getImage());
        args.putString(ARG_FRAGMENT_ITEM_TEXT, startViewPagerItem.getText());
        return args;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_start_tab, container, false);
        findViews(rootView);
        setImage(rootView);
        setTextUnderTitle();
        return rootView;
    }


    private void findViews(ViewGroup container) {
        mIvImage = container.findViewById(R.id.homeScreenOnBoardingImage);
        mTvText = container.findViewById(R.id.homeScreenOnBoardingText);
    }


    private void setTextUnderTitle() {
        mTvText.setText(getArguments().get(ARG_FRAGMENT_ITEM_TEXT).toString());
    }

    private void setImage(ViewGroup container) {
        Log.d(TAG, "setImage: " + getArguments().get(ARG_FRAGMENT_ITEM_IMAGE_ID));
        Context context = container.getContext();
        Picasso.with(context)
                .load(getArguments().getInt(ARG_FRAGMENT_ITEM_IMAGE_ID))
                .placeholder(getArguments().getInt(ARG_FRAGMENT_ITEM_IMAGE_ID))
                .error(android.R.drawable.btn_star_big_off)
                .into(mIvImage);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

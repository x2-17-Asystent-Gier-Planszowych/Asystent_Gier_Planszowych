package pwcdma.asystentgierplanszowych.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import pwcdma.asystentgierplanszowych.R;


public class TableFragment extends Fragment {
    private ScrollView mSvTable;
    private ScrollView mSvEditTable;
    private FloatingActionMenu mFam;
    private FloatingActionButton mFabEditOrSaveTable;
    private FloatingActionButton mFabCreateTable;
    private FloatingActionButton mFabResizeTable;

    private static final String TAG = TableFragment.class.getSimpleName();


    private OnFragmentInteractionListener mListener;

    public TableFragment() {
        Log.d(TAG, "ToolsFragment: ");
    }

    public static TableFragment newInstance() {
        Log.d(TAG, "newInstance: ");
        TableFragment fragment = new TableFragment();
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
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        findViews(view);
        setOnClickListeners();
        return view;
    }

    private void setOnClickListeners() {
        mFabEditOrSaveTable.setOnClickListener(onClickListenerEditOrSaveTable);
    }

    private View.OnClickListener onClickListenerEditOrSaveTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mSvEditTable.isShown()){
                Log.d(TAG, "onClickEditOrSaveTable: " + mSvEditTable.isShown() );
            } else {
                Log.d(TAG, "onClickEditOrSaveTable: " + mSvEditTable.isShown() );
            }
        }
    };

    private View.OnClickListener onClickListenerResizeTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickResizeTable: ");
        }
    };
    private View.OnClickListener onClickListenerCreateTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickCreateTable: ");
        }
    };
    private void findViews(View view) {
        mSvTable = view.findViewById(R.id.tableShowView);
        mSvEditTable = view.findViewById(R.id.tableEditView);
        mFam = view.findViewById(R.id.tableMenuFAB);
        mFabCreateTable = view.findViewById(R.id.fabTableCreate);
        mFabEditOrSaveTable = view.findViewById(R.id.fabTableEditOrSave);
        mFabResizeTable = view.findViewById(R.id.fabTableResize);
    }

    public void onButtonPressed(Uri uri) {
        Log.d(TAG, "onButtonPressed: ");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
}

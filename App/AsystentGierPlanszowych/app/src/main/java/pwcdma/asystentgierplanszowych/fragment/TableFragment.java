package pwcdma.asystentgierplanszowych.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;

import static android.view.View.GONE;


public class TableFragment extends Fragment {
    private ScrollView mSvTable;
    private ScrollView mSvEditTable;
    private FloatingActionMenu mFam;
    private FloatingActionButton mFabEditOrSaveTable;
    private FloatingActionButton mFabCreateTable;
    private FloatingActionButton mFabResizeTable;
    private List<TextView> mTableTitles = new ArrayList<>();
    private List<List<TextView>> mCells = new ArrayList<>();
    private List<TextView> mRow01 = new ArrayList<>();
    private List<TextView> mRow02 = new ArrayList<>();
    private List<TextView> mRow03 = new ArrayList<>();
    private List<TextView> mRow04 = new ArrayList<>();
    private List<TextView> mRow05 = new ArrayList<>();
    private List<TextView> mRow06 = new ArrayList<>();
    private List<TextView> mRow07 = new ArrayList<>();
    private List<TextView> mRow08 = new ArrayList<>();
    private List<TextView> mRow09 = new ArrayList<>();
    private List<TextView> mRow10 = new ArrayList<>();
    private List<TextView> mTableTitlesEdit = new ArrayList<>();
    private List<List<EditText>> mCellsEdit = new ArrayList<>();
    private List<EditText> mRowEdit01 = new ArrayList<>();
    private List<EditText> mRowEdit02 = new ArrayList<>();
    private List<EditText> mRowEdit03 = new ArrayList<>();
    private List<EditText> mRowEdit04 = new ArrayList<>();
    private List<EditText> mRowEdit05 = new ArrayList<>();
    private List<EditText> mRowEdit06 = new ArrayList<>();
    private List<EditText> mRowEdit07 = new ArrayList<>();
    private List<EditText> mRowEdit08 = new ArrayList<>();
    private List<EditText> mRowEdit09 = new ArrayList<>();
    private List<EditText> mRowEdit10 = new ArrayList<>();
    private String[] titles;
    private String[][] cells;
    private int mNumberOfRows;
    private int mNumberOfCols;
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
            if (!mSvEditTable.isShown()) {
                Log.d(TAG, "onClickEditOrSaveTable: " + mSvEditTable.isShown());
                mSvEditTable.setVisibility(View.VISIBLE);
                mSvTable.setVisibility(View.GONE);
            } else {
                Log.d(TAG, "onClickEditOrSaveTable: " + mSvEditTable.isShown());
                mSvEditTable.setVisibility(View.GONE);
                mSvTable.setVisibility(View.VISIBLE);
                setTexts();
            }
            mFam.close(true);
        }
    };
private void getTexts(){
   for(int i = 0 ; i < mNumberOfRows; i++){
       for(int j = 0; j < mNumberOfCols; j++){
           cells[i][j] = mCellsEdit.get(mNumberOfCols).get(mNumberOfRows).getText().toString();
           Log.d(TAG, "getTexts: " + cells[i][j]);
           // TODO: 17.11.2017 na tym stanęłam 
       }
   }
}

    private void setTexts(){
        for (List<TextView> list: mCells) {
            for (TextView text: list) {
//                text.setText();
            }
        }
    }

    private View.OnClickListener onClickListenerResizeTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickResizeTable: ");
            mFam.close(true);
        }
    };
    private View.OnClickListener onClickListenerCreateTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickCreateTable: ");
            mFam.close(true);
        }
    };


    private void findViews(View view) {
        mSvTable = view.findViewById(R.id.tableShowView);
        mSvEditTable = view.findViewById(R.id.tableEditView);
        mFam = view.findViewById(R.id.tableMenuFAB);
        mFabCreateTable = view.findViewById(R.id.fabTableCreate);
        mFabEditOrSaveTable = view.findViewById(R.id.fabTableEditOrSave);
        mFabResizeTable = view.findViewById(R.id.fabTableResize);

        mTableTitles.add((TextView) view.findViewById(R.id.column01));
        mTableTitles.add((TextView) view.findViewById(R.id.column02));
        mTableTitles.add((TextView) view.findViewById(R.id.column03));
        mTableTitles.add((TextView) view.findViewById(R.id.column04));
        mTableTitles.add((TextView) view.findViewById(R.id.column05));
        mTableTitles.add((TextView) view.findViewById(R.id.column06));
        mTableTitles.add((TextView) view.findViewById(R.id.column07));
        mTableTitles.add((TextView) view.findViewById(R.id.column08));
        mTableTitles.add((TextView) view.findViewById(R.id.column09));
        mTableTitles.add((TextView) view.findViewById(R.id.column10));

        mRow01.add((TextView) view.findViewById(R.id.cell0101));
        mRow01.add((TextView) view.findViewById(R.id.cell0201));
        mRow01.add((TextView) view.findViewById(R.id.cell0301));
        mRow01.add((TextView) view.findViewById(R.id.cell0401));
        mRow01.add((TextView) view.findViewById(R.id.cell0501));
        mRow01.add((TextView) view.findViewById(R.id.cell0601));
        mRow01.add((TextView) view.findViewById(R.id.cell0701));
        mRow01.add((TextView) view.findViewById(R.id.cell0801));
        mRow01.add((TextView) view.findViewById(R.id.cell0901));
        mRow01.add((TextView) view.findViewById(R.id.cell1001));

        mRow02.add((TextView) view.findViewById(R.id.cell0102));
        mRow02.add((TextView) view.findViewById(R.id.cell0202));
        mRow02.add((TextView) view.findViewById(R.id.cell0302));
        mRow02.add((TextView) view.findViewById(R.id.cell0402));
        mRow02.add((TextView) view.findViewById(R.id.cell0502));
        mRow02.add((TextView) view.findViewById(R.id.cell0602));
        mRow02.add((TextView) view.findViewById(R.id.cell0702));
        mRow02.add((TextView) view.findViewById(R.id.cell0802));
        mRow02.add((TextView) view.findViewById(R.id.cell0902));
        mRow02.add((TextView) view.findViewById(R.id.cell1002));

        mRow01.add((TextView) view.findViewById(R.id.cell0103));
        mRow01.add((TextView) view.findViewById(R.id.cell0203));
        mRow01.add((TextView) view.findViewById(R.id.cell0303));
        mRow01.add((TextView) view.findViewById(R.id.cell0403));
        mRow01.add((TextView) view.findViewById(R.id.cell0503));
        mRow01.add((TextView) view.findViewById(R.id.cell0603));
        mRow01.add((TextView) view.findViewById(R.id.cell0703));
        mRow01.add((TextView) view.findViewById(R.id.cell0803));
        mRow01.add((TextView) view.findViewById(R.id.cell0903));
        mRow01.add((TextView) view.findViewById(R.id.cell1003));

        mRow02.add((TextView) view.findViewById(R.id.cell0104));
        mRow02.add((TextView) view.findViewById(R.id.cell0204));
        mRow02.add((TextView) view.findViewById(R.id.cell0304));
        mRow02.add((TextView) view.findViewById(R.id.cell0404));
        mRow02.add((TextView) view.findViewById(R.id.cell0504));
        mRow02.add((TextView) view.findViewById(R.id.cell0604));
        mRow02.add((TextView) view.findViewById(R.id.cell0704));
        mRow02.add((TextView) view.findViewById(R.id.cell0804));
        mRow02.add((TextView) view.findViewById(R.id.cell0904));
        mRow02.add((TextView) view.findViewById(R.id.cell1004));

        mRow01.add((TextView) view.findViewById(R.id.cell0105));
        mRow01.add((TextView) view.findViewById(R.id.cell0205));
        mRow01.add((TextView) view.findViewById(R.id.cell0305));
        mRow01.add((TextView) view.findViewById(R.id.cell0405));
        mRow01.add((TextView) view.findViewById(R.id.cell0505));
        mRow01.add((TextView) view.findViewById(R.id.cell0605));
        mRow01.add((TextView) view.findViewById(R.id.cell0705));
        mRow01.add((TextView) view.findViewById(R.id.cell0805));
        mRow01.add((TextView) view.findViewById(R.id.cell0905));
        mRow01.add((TextView) view.findViewById(R.id.cell1005));

        mRow02.add((TextView) view.findViewById(R.id.cell0106));
        mRow02.add((TextView) view.findViewById(R.id.cell0206));
        mRow02.add((TextView) view.findViewById(R.id.cell0306));
        mRow02.add((TextView) view.findViewById(R.id.cell0406));
        mRow02.add((TextView) view.findViewById(R.id.cell0506));
        mRow02.add((TextView) view.findViewById(R.id.cell0606));
        mRow02.add((TextView) view.findViewById(R.id.cell0706));
        mRow02.add((TextView) view.findViewById(R.id.cell0806));
        mRow02.add((TextView) view.findViewById(R.id.cell0906));
        mRow02.add((TextView) view.findViewById(R.id.cell1006));

        mRow01.add((TextView) view.findViewById(R.id.cell0107));
        mRow01.add((TextView) view.findViewById(R.id.cell0207));
        mRow01.add((TextView) view.findViewById(R.id.cell0307));
        mRow01.add((TextView) view.findViewById(R.id.cell0407));
        mRow01.add((TextView) view.findViewById(R.id.cell0507));
        mRow01.add((TextView) view.findViewById(R.id.cell0607));
        mRow01.add((TextView) view.findViewById(R.id.cell0707));
        mRow01.add((TextView) view.findViewById(R.id.cell0807));
        mRow01.add((TextView) view.findViewById(R.id.cell0907));
        mRow01.add((TextView) view.findViewById(R.id.cell1007));

        mRow02.add((TextView) view.findViewById(R.id.cell0108));
        mRow02.add((TextView) view.findViewById(R.id.cell0208));
        mRow02.add((TextView) view.findViewById(R.id.cell0308));
        mRow02.add((TextView) view.findViewById(R.id.cell0408));
        mRow02.add((TextView) view.findViewById(R.id.cell0508));
        mRow02.add((TextView) view.findViewById(R.id.cell0608));
        mRow02.add((TextView) view.findViewById(R.id.cell0708));
        mRow02.add((TextView) view.findViewById(R.id.cell0808));
        mRow02.add((TextView) view.findViewById(R.id.cell0908));
        mRow02.add((TextView) view.findViewById(R.id.cell1008));

        mRow01.add((TextView) view.findViewById(R.id.cell0109));
        mRow01.add((TextView) view.findViewById(R.id.cell0209));
        mRow01.add((TextView) view.findViewById(R.id.cell0309));
        mRow01.add((TextView) view.findViewById(R.id.cell0409));
        mRow01.add((TextView) view.findViewById(R.id.cell0509));
        mRow01.add((TextView) view.findViewById(R.id.cell0609));
        mRow01.add((TextView) view.findViewById(R.id.cell0709));
        mRow01.add((TextView) view.findViewById(R.id.cell0809));
        mRow01.add((TextView) view.findViewById(R.id.cell0909));
        mRow01.add((TextView) view.findViewById(R.id.cell1009));

        mRow02.add((TextView) view.findViewById(R.id.cell0110));
        mRow02.add((TextView) view.findViewById(R.id.cell0210));
        mRow02.add((TextView) view.findViewById(R.id.cell0310));
        mRow02.add((TextView) view.findViewById(R.id.cell0410));
        mRow02.add((TextView) view.findViewById(R.id.cell0510));
        mRow02.add((TextView) view.findViewById(R.id.cell0610));
        mRow02.add((TextView) view.findViewById(R.id.cell0710));
        mRow02.add((TextView) view.findViewById(R.id.cell0810));
        mRow02.add((TextView) view.findViewById(R.id.cell0910));
        mRow02.add((TextView) view.findViewById(R.id.cell1010));

        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn01));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn02));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn03));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn04));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn05));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn06));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn07));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn08));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn09));
        mTableTitlesEdit.add((TextView) view.findViewById(R.id.editColumn10));

        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0101));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0201));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0301));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0401));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0501));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0601));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0701));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0801));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0901));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell1001));

        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0102));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0202));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0302));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0402));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0502));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0602));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0702));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0802));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0902));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell1002));

        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0103));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0203));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0303));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0403));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0503));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0603));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0703));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0803));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0903));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell1003));

        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0104));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0204));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0304));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0404));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0504));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0604));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0704));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0804));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0904));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell1004));

        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0105));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0205));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0305));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0405));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0505));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0605));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0705));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0805));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0905));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell1005));

        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0106));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0206));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0306));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0406));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0506));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0606));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0706));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0806));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0906));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell1006));

        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0107));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0207));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0307));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0407));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0507));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0607));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0707));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0807));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0907));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell1007));

        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0108));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0208));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0308));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0408));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0508));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0608));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0708));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0808));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0908));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell1008));

        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0109));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0209));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0309));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0409));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0509));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0609));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0709));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0809));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell0909));
        mRowEdit01.add((EditText) view.findViewById(R.id.editCell1009));

        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0110));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0210));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0310));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0410));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0510));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0610));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0710));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0810));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell0910));
        mRowEdit02.add((EditText) view.findViewById(R.id.editCell1010));

        mCells.add(mRow01);
        mCells.add(mRow02);
        mCells.add(mRow03);
        mCells.add(mRow04);
        mCells.add(mRow05);
        mCells.add(mRow06);
        mCells.add(mRow07);
        mCells.add(mRow08);
        mCells.add(mRow09);
        mCells.add(mRow10);


        mCellsEdit.add(mRowEdit01);
        mCellsEdit.add(mRowEdit02);
        mCellsEdit.add(mRowEdit03);
        mCellsEdit.add(mRowEdit04);
        mCellsEdit.add(mRowEdit05);
        mCellsEdit.add(mRowEdit06);
        mCellsEdit.add(mRowEdit07);
        mCellsEdit.add(mRowEdit08);
        mCellsEdit.add(mRowEdit09);
        mCellsEdit.add(mRowEdit10);
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

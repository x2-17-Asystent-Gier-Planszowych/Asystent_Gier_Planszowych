package pwcdma.asystentgierplanszowych.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;


public class TableFragment extends Fragment {
    private ScrollView mSvTable;
    private ScrollView mSvEditTable;
    private FloatingActionMenu mFam;
    private FloatingActionButton mFabShowResultsTable;
    private FloatingActionButton mFabCreateTable;
    private FloatingActionButton mFabResizeTable;
    private FloatingActionButton mFabClearTable;
    private List<TextView> mResults = new ArrayList<>();
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
    private int cells[][];
    private int mNumberOfRows = 10;
    private int mNumberOfCols = 10;
    private int rowSum[] = new int[mNumberOfRows];
    private Dialog dialog;
    private NumberPicker mNumOfRowsPicker;
    private NumberPicker mNumOfColsPicker;
    private Button dialogButtonOk;

    private LinearLayout mEmptyTableLL;

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
        setupDialog();
        mEmptyTableLL.setVisibility(View.VISIBLE);
        mSvEditTable.setVisibility(View.GONE);
        mFabResizeTable.setVisibility(View.GONE);
        mFabShowResultsTable.setVisibility(View.GONE);
        mFabClearTable.setVisibility(View.GONE);
        cells = new int[mNumberOfRows][mNumberOfCols];
        return view;
    }

    private void setOnClickListeners() {
        mFabResizeTable.setOnClickListener(onClickListenerResizeTable);
        mFabCreateTable.setOnClickListener(onClickListenerCreateTable);
        mFabShowResultsTable.setOnClickListener(onClickListenerShowResults);
        mFabClearTable.setOnClickListener(onClickListenerClearTable);
    }

    private void getTexts() {
        for (int i = 0; i < mNumberOfRows; i++) {
            for (int j = 0; j < mNumberOfCols; j++) {
            }
        }
    }


    private View.OnClickListener onClickListenerResizeTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickResizeTable: ");
            resizeTable();
            mFam.close(true);
        }
    };
    private View.OnClickListener onClickListenerCreateTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickCreateTable: ");
            createTable();
            mFam.close(true);
        }
    };
    private View.OnClickListener onClickListenerShowResults = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClickShowResultsTable: ");
            showResults();
            mFam.close(true);
        }
    };

    private View.OnClickListener onClickListenerClearTable = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick:ClearTable ");
            clearTable();
            mFam.close(true);
        }
    };


    private void createTable() {
        Log.d(TAG, "createTable: ");
        redrawTable();
        mFabResizeTable.setVisibility(View.VISIBLE);
        mFabCreateTable.setVisibility(View.GONE);
        mFabShowResultsTable.setVisibility(View.VISIBLE);
        mFabClearTable.setVisibility(View.VISIBLE);
    }

    private void resizeTable() {
        Log.d(TAG, "resizeTable: ");
        clearSums();
        redrawTable();
    }

    private void clearTable() {
        Log.d(TAG, "clearTable: ");
        clearSums();
        drawTable();
    }

    private void redrawTable() {
        Log.d(TAG, "redrawTable: ");
        dialogButtonOk = dialog.findViewById(R.id.tableSizePickerOkButton);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mNumberOfRows = mNumOfRowsPicker.getValue();
                mNumberOfCols = mNumOfColsPicker.getValue();
                Log.d(TAG, "onClick: " + mNumberOfRows + " : " + mNumberOfCols);
                cells = new int[mNumberOfRows][mNumberOfCols];
                mSvEditTable.setVisibility(View.VISIBLE);
                drawTable();
            }
        });
        dialog.show();
    }

    private void drawTable() {
        Log.d(TAG, "drawTable: ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mCellsEdit.get(i).get(j).setText("");
                mCellsEdit.get(i).get(j).setVisibility(View.INVISIBLE);
            }
        }
        for (int i = 0; i < 10; i++) {
            mTableTitlesEdit.get(i).setVisibility(View.INVISIBLE);
        }
        for (int i = 0; i < mNumberOfCols; i++) {
            mTableTitlesEdit.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < mNumberOfRows; i++) {
            for (int j = 0; j < mNumberOfCols; j++) {
                mCellsEdit.get(i).get(j).setVisibility(View.VISIBLE);
            }
        }
    }


    private void setupDialog() {
        Log.d(TAG, "setupDialog: ");
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_table_picker);
        dialog.setTitle("Wybierz rozmiar tabeli");

        mNumOfRowsPicker = dialog.findViewById(R.id.tableSizePickerRowsPicker);
        mNumOfColsPicker = dialog.findViewById(R.id.tableSizePickerColsPicker);

        mNumOfRowsPicker.setMaxValue(10);
        mNumOfRowsPicker.setMinValue(1);
        mNumOfColsPicker.setMaxValue(10);
        mNumOfColsPicker.setMinValue(1);
    }


    private void showResults() {
        Log.d(TAG, "showResults: ");
        clearSums();
        iterateTable();
        showRowResults();
    }


    private void iterateTable() {
        Log.d(TAG, "iterateTable2: ");
        for (int i = 0; i < mNumberOfRows; i++) {
            List<EditText> temp = mCellsEdit.get(i);
            for (int j = 0; j < mNumberOfCols; j++) {
                Log.d(TAG, "iterateTable2: " + temp.get(j).getText().toString());
                if (temp.get(j).getText().toString().equals("")) {
                    cells[i][j] = 0;
                } else {
                    cells[i][j] = Integer.parseInt(temp.get(j).getText().toString());
                    Log.d(TAG, "iterateTable2: " + cells[i][j]);
                }
                rowSum[i] += cells[i][j];
            }
        }
    }

    private void clearSums() {
        Log.d(TAG, "clearSums: ");
        for (int i = 0; i < 10; i++) {
            rowSum[i] = 0;
            mResults.get(i).setText("");
        }
    }


    public void showRowResults() {
        Log.d(TAG, "showRowResults: ");
        for (int i = 0; i < mNumberOfRows; i++) {
            mResults.get(i).setText(String.valueOf(rowSum[i]));
        }
    }

    private void findViews(View view) {
        Log.d(TAG, "findViews: ");
        mEmptyTableLL = view.findViewById(R.id.tableEmptyLayout);
        mSvEditTable = view.findViewById(R.id.tableEditView);
        mFam = view.findViewById(R.id.tableMenuFAB);
        mFabCreateTable = view.findViewById(R.id.fabTableCreate);
        mFabShowResultsTable = view.findViewById(R.id.fabTableShowResults);
        mFabResizeTable = view.findViewById(R.id.fabTableResize);
        mFabClearTable = view.findViewById(R.id.fabTableClear);

        mResults.add((TextView) view.findViewById(R.id.result01));
        mResults.add((TextView) view.findViewById(R.id.result02));
        mResults.add((TextView) view.findViewById(R.id.result03));
        mResults.add((TextView) view.findViewById(R.id.result04));
        mResults.add((TextView) view.findViewById(R.id.result05));
        mResults.add((TextView) view.findViewById(R.id.result06));
        mResults.add((TextView) view.findViewById(R.id.result07));
        mResults.add((TextView) view.findViewById(R.id.result08));
        mResults.add((TextView) view.findViewById(R.id.result09));
        mResults.add((TextView) view.findViewById(R.id.result10));

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

        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0103));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0203));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0303));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0403));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0503));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0603));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0703));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0803));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell0903));
        mRowEdit03.add((EditText) view.findViewById(R.id.editCell1003));

        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0104));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0204));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0304));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0404));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0504));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0604));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0704));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0804));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell0904));
        mRowEdit04.add((EditText) view.findViewById(R.id.editCell1004));

        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0105));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0205));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0305));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0405));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0505));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0605));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0705));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0805));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell0905));
        mRowEdit05.add((EditText) view.findViewById(R.id.editCell1005));

        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0106));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0206));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0306));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0406));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0506));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0606));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0706));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0806));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell0906));
        mRowEdit06.add((EditText) view.findViewById(R.id.editCell1006));

        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0107));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0207));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0307));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0407));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0507));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0607));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0707));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0807));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell0907));
        mRowEdit07.add((EditText) view.findViewById(R.id.editCell1007));

        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0108));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0208));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0308));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0408));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0508));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0608));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0708));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0808));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell0908));
        mRowEdit08.add((EditText) view.findViewById(R.id.editCell1008));

        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0109));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0209));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0309));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0409));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0509));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0609));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0709));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0809));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell0909));
        mRowEdit09.add((EditText) view.findViewById(R.id.editCell1009));

        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0110));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0210));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0310));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0410));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0510));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0610));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0710));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0810));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell0910));
        mRowEdit10.add((EditText) view.findViewById(R.id.editCell1010));

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

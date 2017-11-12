package pwcdma.asystentgierplanszowych.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.adapter.ResultListItemAdapter;
import pwcdma.asystentgierplanszowych.model.Dice;
import pwcdma.asystentgierplanszowych.model.Result;

public class DiceFragment extends Fragment {

    private final static String TAG = DiceFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView mDiceRollResult1;
    private TextView mDiceRollResult2;
    private TextView mDiceRollResult3;
    private TextView mDiceRollResult4;
    private TextView mDiceRollResult5;
    private TextView mDiceRollResult6;
    private LinearLayout mLlTopRowOFDices;
    private LinearLayout mLlBottomRowOFDices;
    private LinearLayout mDiceNotPickedLayout;
    private Button mRollDiceButton;
    private AlertDialog.Builder builder;
    private AlertDialog mPickDiceDialog;
    private AlertDialog mShowHistoryDialog;
    private AlertDialog mPickNumberOfDicesDialog;
    private FloatingActionButton mFabShowHistory;
    private FloatingActionButton mFabNumberOfDices;
    private FloatingActionButton mFabNumberOfDiceSides;
    private FloatingActionMenu mFloatingActionMenu;
    private int mPickedDice = 1;
    private int mNumberOfDices = -1;
    private String[] diceNamesList = {"K4", "K6", "K8", "K10", "K12", "K20"};
    private Byte[] diceNumberList = {1, 2, 3, 4, 5, 6};
    private List<Dice> dices = new ArrayList<>();
    private int result[] = new int[6];
    private List<Result> resultItemList = new ArrayList<>();
    private List<ImageView> diceImages1 = new ArrayList<>();
    private List<ImageView> diceImages2 = new ArrayList<>();
    private List<ImageView> diceImages3 = new ArrayList<>();
    private List<ImageView> diceImages4 = new ArrayList<>();
    private List<ImageView> diceImages5 = new ArrayList<>();
    private List<ImageView> diceImages6 = new ArrayList<>();
    private List<List<ImageView>> diceImages = new ArrayList<>();
    private List<TextView> diceResults = new ArrayList<>();

    public DiceFragment() {
    }


    public static DiceFragment newInstance(String param1, String param2) {
        Log.d(TAG, "newInstance: ");
        DiceFragment fragment = new DiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_dice, container, false);
        findViews(view);
        mDiceRollResult1.setText("");
        addDicesToList();
        addDiceImagesToList(view);
        setupDiceResultsList();
        setupPickDiceDialog();
        setupHistoryDialog();
        setupNumberOfDicesDialog();
        setOnClickListerners();
        setupFAM();
        disableButton(mRollDiceButton);
        mFabShowHistory.setEnabled(false);
        mFabNumberOfDiceSides.setEnabled(false);
        return view;
    }

    private void setupFAM() {
        Log.d(TAG, "setupFAM: ");

    }

    private void setOnClickListerners() {
        Log.d(TAG, "setOnClickListerners: ");
        mRollDiceButton.setOnClickListener(onClickListenerRoll);
        mFabShowHistory.setOnClickListener(onClickListenerShowHistory);
        mFabNumberOfDices.setOnClickListener(onClickListenerPickNumberOfDices);
        mFabNumberOfDiceSides.setOnClickListener(onClickListenerPick);
    }

    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setEnabled(true);
    }

    private void findViews(View view) {
        Log.d(TAG, "findViews: " + view);
        mRollDiceButton = view.findViewById(R.id.diceRollDiceBtn);
        mDiceRollResult1 = view.findViewById(R.id.diceResult1);
        mDiceRollResult2 = view.findViewById(R.id.diceResult2);
        mDiceRollResult3 = view.findViewById(R.id.diceResult3);
        mDiceRollResult4 = view.findViewById(R.id.diceResult4);
        mDiceRollResult5 = view.findViewById(R.id.diceResult5);
        mDiceRollResult6 = view.findViewById(R.id.diceResult6);
        mDiceNotPickedLayout = view.findViewById(R.id.diceNotPickedLayout);
        mLlTopRowOFDices = view.findViewById(R.id.diceTopDicesLL);
        mLlBottomRowOFDices = view.findViewById(R.id.diceBottomDicesLL);
        mFloatingActionMenu = view.findViewById(R.id.menuFAB);
        mFabShowHistory = view.findViewById(R.id.fabShowHistory);
        mFabNumberOfDices = view.findViewById(R.id.fabNumberOfDices);
        mFabNumberOfDiceSides = view.findViewById(R.id.fabNumberOfSides);
    }

    private void setupDiceResultsList() {
        diceResults.add(mDiceRollResult1);
        diceResults.add(mDiceRollResult2);
        diceResults.add(mDiceRollResult3);
        diceResults.add(mDiceRollResult4);
        diceResults.add(mDiceRollResult5);
        diceResults.add(mDiceRollResult6);
    }

    private void addDicesToList() {
        Log.d(TAG, "addDicesToList: ");
        dices.add(new Dice("K4", 4, false));
        dices.add(new Dice("K6", 6, false));
        dices.add(new Dice("K8", 8, false));
        dices.add(new Dice("K10", 10, false));
        dices.add(new Dice("K12", 12, false));
        dices.add(new Dice("K20", 20, false));
    }

    private void addDiceImagesToList(View view) {
        Log.d(TAG, "addDiceImagesToList: ");
        diceImages1.add((ImageView) view.findViewById(R.id.diceK4image1));
        diceImages1.add((ImageView) view.findViewById(R.id.diceK6image1));
        diceImages1.add((ImageView) view.findViewById(R.id.diceK8image1));
        diceImages1.add((ImageView) view.findViewById(R.id.diceK10image1));
        diceImages1.add((ImageView) view.findViewById(R.id.diceK12image1));
        diceImages1.add((ImageView) view.findViewById(R.id.diceK20image1));

        diceImages2.add((ImageView) view.findViewById(R.id.diceK4image2));
        diceImages2.add((ImageView) view.findViewById(R.id.diceK6image2));
        diceImages2.add((ImageView) view.findViewById(R.id.diceK8image2));
        diceImages2.add((ImageView) view.findViewById(R.id.diceK10image2));
        diceImages2.add((ImageView) view.findViewById(R.id.diceK12image2));
        diceImages2.add((ImageView) view.findViewById(R.id.diceK20image2));

        diceImages3.add((ImageView) view.findViewById(R.id.diceK4image3));
        diceImages3.add((ImageView) view.findViewById(R.id.diceK6image3));
        diceImages3.add((ImageView) view.findViewById(R.id.diceK8image3));
        diceImages3.add((ImageView) view.findViewById(R.id.diceK10image3));
        diceImages3.add((ImageView) view.findViewById(R.id.diceK12image3));
        diceImages3.add((ImageView) view.findViewById(R.id.diceK20image3));

        diceImages4.add((ImageView) view.findViewById(R.id.diceK4image4));
        diceImages4.add((ImageView) view.findViewById(R.id.diceK6image4));
        diceImages4.add((ImageView) view.findViewById(R.id.diceK8image4));
        diceImages4.add((ImageView) view.findViewById(R.id.diceK10image4));
        diceImages4.add((ImageView) view.findViewById(R.id.diceK12image4));
        diceImages4.add((ImageView) view.findViewById(R.id.diceK20image4));

        diceImages5.add((ImageView) view.findViewById(R.id.diceK4image5));
        diceImages5.add((ImageView) view.findViewById(R.id.diceK6image5));
        diceImages5.add((ImageView) view.findViewById(R.id.diceK8image5));
        diceImages5.add((ImageView) view.findViewById(R.id.diceK10image5));
        diceImages5.add((ImageView) view.findViewById(R.id.diceK12image5));
        diceImages5.add((ImageView) view.findViewById(R.id.diceK20image5));

        diceImages6.add((ImageView) view.findViewById(R.id.diceK4image6));
        diceImages6.add((ImageView) view.findViewById(R.id.diceK6image6));
        diceImages6.add((ImageView) view.findViewById(R.id.diceK8image6));
        diceImages6.add((ImageView) view.findViewById(R.id.diceK10image6));
        diceImages6.add((ImageView) view.findViewById(R.id.diceK12image6));
        diceImages6.add((ImageView) view.findViewById(R.id.diceK20image6));

        diceImages.add(diceImages1);
        diceImages.add(diceImages2);
        diceImages.add(diceImages3);
        diceImages.add(diceImages4);
        diceImages.add(diceImages5);
        diceImages.add(diceImages6);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    private void setupPickDiceDialog() {
        Log.d(TAG, "setupPickDiceDialog: ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_dice_dialog_list_item, diceNamesList);

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.diceFragmentPickADiceText);
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPickedDice = which;
            }
        });
        builder.setPositiveButton(R.string.diceFragmentPickADicePickBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + mPickedDice);
                placeDice(mPickedDice);
                enableButton(mRollDiceButton);
                mDiceNotPickedLayout.setVisibility(View.GONE);
                mLlTopRowOFDices.setVisibility(View.VISIBLE);
                mLlBottomRowOFDices.setVisibility(View.VISIBLE);
                cleanResultsOnDices();
            }
        });
        builder.setNegativeButton(R.string.diceFragmentPickADiceCancelBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPickDiceDialog.dismiss();
            }
        });
        mPickDiceDialog = builder.create();
    }

    private void setupNumberOfDicesDialog() {
        Log.d(TAG, "setupNumberOfDicesDialog: ");
        ArrayAdapter<Byte> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_dice_dialog_list_item, diceNumberList);

        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.dicePickNoOFDicesText);
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + which);
                mNumberOfDices = which + 1;
            }
        });
        builder.setPositiveButton(R.string.diceFragmentPickADicePickBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + mNumberOfDices);
                mFabNumberOfDiceSides.setEnabled(true);
                cleanResultsOnDices();
                placeDice(mPickedDice);
            }
        });
        builder.setNegativeButton(R.string.diceFragmentPickADiceCancelBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPickNumberOfDicesDialog.dismiss();
            }
        });
        mPickNumberOfDicesDialog = builder.create();
    }


    private void placeDice(int pickedDice) {
        for (List<ImageView> temp : diceImages) {
            for (ImageView diceImage : temp) {
                diceImage.setVisibility(View.GONE);
            }
        }
        for (int i = 0; i < mNumberOfDices; i++) {
            List<ImageView> temp = diceImages.get(i);
            for (ImageView diceImage : temp) {
                if (diceImage == temp.get(pickedDice)) {
                    diceImage.setVisibility(View.VISIBLE);
                } else {
                    diceImage.setVisibility(View.GONE);
                }
            }
        }
    }


    private void setupHistoryDialog() {
        Log.d(TAG, "setupHistoryDialog: ");
        ResultListItemAdapter adapter = new ResultListItemAdapter(getContext(), resultItemList);
        builder = new AlertDialog.Builder(getContext());
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + which);
            }
        });
        builder.setPositiveButton(R.string.diceFragmentPickADiceOkBtn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: " + which);
                mShowHistoryDialog.dismiss();
            }
        });
        mShowHistoryDialog = builder.create();
    }


    private View.OnClickListener onClickListenerPick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: ");
            mPickDiceDialog.show();
        }
    };

    private View.OnClickListener onClickListenerRoll = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: " + mNumberOfDices );
            fetchResults();
            showResultsOnDiceAndList();
            mFabShowHistory.setEnabled(true);
        }
    };

    private void cleanResultsOnDices(){
        for(int i= 0; i < 5 ; i++){
            diceResults.get(i).setText("");
            result[i] = 0;
        }

    }

    private void fetchResults() {
        for (int i = 0; i < mNumberOfDices; i++) {
            result[i] = dices.get(mPickedDice).roll();
        }
    }

    private void showResultsOnDiceAndList(){
        String name = dices.get(mPickedDice).getName();
        for (int i = 0; i < 5; i++) {
            if (result[i] != 0) {
                diceResults.get(i).setText(String.valueOf(result[i]));
                resultItemList.add(new Result(name, result[i]));
            } else diceResults.get(i).setText("");
        }
    }
    private View.OnClickListener onClickListenerPickNumberOfDices = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            mPickNumberOfDicesDialog.show();
        }
    };

    private View.OnClickListener onClickListenerShowHistory = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: ");
            mShowHistoryDialog.show();
        }
    };

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

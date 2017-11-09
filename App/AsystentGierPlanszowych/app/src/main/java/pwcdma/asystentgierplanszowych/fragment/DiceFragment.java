package pwcdma.asystentgierplanszowych.fragment;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.adapter.ResultListItemAdapter;
import pwcdma.asystentgierplanszowych.model.Dice;
import pwcdma.asystentgierplanszowych.model.Result;

public class DiceFragment extends Fragment {

    private final static String TAG = DiceFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView mDiceRollResult;
    private TextView mDicePickedTestTV;
    private Button mPickDiceButton;
    private Button mRollDiceButton;
    private Button mShowDiceRollHistoryBtn;
    private AlertDialog.Builder builder;
    private AlertDialog mPickDiceDialog;
    private AlertDialog mShowHistoryDialog;
    private int mPickedDice = 1; //1 = K6
    private String[] items = {"K4", "K6", "K8", "K10", "K12", "K20"};
    private ArrayList<Dice> dices = new ArrayList<>();
    private ArrayList<Result> resultItemList = new ArrayList<>();
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
        mDiceRollResult.setText("");
        addDicesToList();
        setupDialog();
        setupHistoryDialog();
        setOnClickListerners();
        return view;
    }

    private void setOnClickListerners() {
        Log.d(TAG, "setOnClickListerners: ");
        mPickDiceButton.setOnClickListener(onClickListenerPick);
        mRollDiceButton.setOnClickListener(onClickListenerRoll);
        mShowDiceRollHistoryBtn.setOnClickListener(onClickListenerShowHistory);
    }


    private void findViews(View view) {
        Log.d(TAG, "findViews: " + view);
        mDicePickedTestTV = view.findViewById(R.id.dicePicked);
        mPickDiceButton = view.findViewById(R.id.dicePickDiceBtn);
        mRollDiceButton = view.findViewById(R.id.diceRollDiceBtn);
        mShowDiceRollHistoryBtn = view.findViewById(R.id.diceResultHistoryBtn);
        mDiceRollResult = view.findViewById(R.id.diceResult);
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

    private void setupDialog() {
        Log.d(TAG, "setupDialog: ");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_dice_dialog_list_item, items);
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
                mDicePickedTestTV.setText(items[mPickedDice]);
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


    private void setupHistoryDialog() {
        Log.d(TAG, "setupDialog: ");
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
            int result = dices.get(mPickedDice).roll();
            String name = dices.get(mPickedDice).getName();
            Log.d(TAG, "onClick: Roll " + result + " " + name);
            mDiceRollResult.setText(String.valueOf(result));
            resultItemList.add(new Result(name,result));
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

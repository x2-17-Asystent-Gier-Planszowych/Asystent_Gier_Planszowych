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
    private ImageView mNoDicePickedImg;
    private TextView mNoDicePickedText;
    private LinearLayout mDiceNotPickedLayout;
    private Button mPickDiceButton;
    private Button mRollDiceButton;
    private Button mShowDiceRollHistoryBtn;
    private AlertDialog.Builder builder;
    private AlertDialog mPickDiceDialog;
    private AlertDialog mShowHistoryDialog;
    private int mPickedDice = -1;
    private String[] items = {"K4", "K6", "K8", "K10", "K12", "K20"};
    private List<Dice> dices = new ArrayList<>();
    private List<Result> resultItemList = new ArrayList<>();
    private List<ImageView> diceImages1 = new ArrayList<>();
    private List<ImageView> diceImages2 = new ArrayList<>();
    private List<ImageView> diceImages3 = new ArrayList<>();
    private List<ImageView> diceImages4 = new ArrayList<>();
    private List<ImageView> diceImages5 = new ArrayList<>();
    private List<ImageView> diceImages6 = new ArrayList<>();

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
        setupPickDiceDialog();
        setupHistoryDialog();
        setOnClickListerners();
        disableButton(mRollDiceButton);
        disableButton(mShowDiceRollHistoryBtn);
        return view;
    }

    private void setOnClickListerners() {
        Log.d(TAG, "setOnClickListerners: ");
        mPickDiceButton.setOnClickListener(onClickListenerPick);
        mRollDiceButton.setOnClickListener(onClickListenerRoll);
        mShowDiceRollHistoryBtn.setOnClickListener(onClickListenerShowHistory);
    }

    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    private void enableButton(Button button) {
        button.setEnabled(true);
    }

    private void findViews(View view) {
        Log.d(TAG, "findViews: " + view);
        mPickDiceButton = view.findViewById(R.id.dicePickDiceBtn);
        mRollDiceButton = view.findViewById(R.id.diceRollDiceBtn);
        mShowDiceRollHistoryBtn = view.findViewById(R.id.diceResultHistoryBtn);
        mDiceRollResult1 = view.findViewById(R.id.diceResult1);
        mDiceRollResult2 = view.findViewById(R.id.diceResult2);
        mDiceRollResult3 = view.findViewById(R.id.diceResult3);
        mDiceRollResult4 = view.findViewById(R.id.diceResult4);
        mDiceRollResult5 = view.findViewById(R.id.diceResult5);
        mDiceRollResult6 = view.findViewById(R.id.diceResult6);
        mDiceNotPickedLayout = view.findViewById(R.id.diceNotPickedLayout);
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
                placeDice(mPickedDice);
                enableButton(mRollDiceButton);
                mDiceNotPickedLayout.setVisibility(View.GONE);

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

    private void placeDice(int pickedDice) {
        for (ImageView temp : diceImages1) {
            if (temp == diceImages1.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
        for (ImageView temp : diceImages2) {
            if (temp == diceImages2.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
        for (ImageView temp : diceImages3) {
            if (temp == diceImages3.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
        for (ImageView temp : diceImages4) {
            if (temp == diceImages4.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
        for (ImageView temp : diceImages5) {
            if (temp == diceImages5.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
        for (ImageView temp : diceImages6) {
            if (temp == diceImages6.get(pickedDice)) {
                temp.setVisibility(View.VISIBLE);
            } else {
                temp.setVisibility(View.GONE);
            }
        }
    }


    private void setupHistoryDialog() {
        Log.d(TAG, "setupPickDiceDialog: ");
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
            int result[] = new int[6];
            for (int i = 0; i <= 5; i++) {
                result[i] = dices.get(mPickedDice).roll();
            }

            String name = dices.get(mPickedDice).getName();
            Log.d(TAG, "onClick: Roll " + result + " " + name);
            mDiceRollResult1.setText(String.valueOf(result[0]));
            mDiceRollResult2.setText(String.valueOf(result[1]));
            mDiceRollResult3.setText(String.valueOf(result[2]));
            mDiceRollResult4.setText(String.valueOf(result[3]));
            mDiceRollResult5.setText(String.valueOf(result[4]));
            mDiceRollResult6.setText(String.valueOf(result[5]));
            for (int i = 0; i <= 5; i++) {
                resultItemList.add(new Result(name, result[i]));
            }
            enableButton(mShowDiceRollHistoryBtn);
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

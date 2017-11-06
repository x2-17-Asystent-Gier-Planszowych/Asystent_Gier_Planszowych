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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import pwcdma.asystentgierplanszowych.R;
import pwcdma.asystentgierplanszowych.model.Dice;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiceFragment extends Fragment {

    private final static String TAG = DiceFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private TextView mDiceRollResult;
    private TextView mDicePickedTestTV;
    private Button mDicePickButton;
    private Button mDiceRollButton;
    private AlertDialog.Builder builder;
    private AlertDialog mPickDiceDialog;
    private int mPickedDice = 0;
    private String[] items = {"K4", "K6", "K8", "K10", "K12", "K20"};
    private ArrayList<Dice> dices = new ArrayList<>();

    public DiceFragment() {
        // Required empty public constructor
    }


    public static DiceFragment newInstance(String param1, String param2) {
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
        addDicesToList();
        setupDialog();
        mDicePickButton.setOnClickListener(onClickListenerPick);
        mDiceRollButton.setOnClickListener(onClickListenerRoll);
        return view;
    }

    private void findViews(View view) {
        Log.d(TAG, "findViews: ");
        mDicePickedTestTV = view.findViewById(R.id.dicePicked);
        mDicePickButton = view.findViewById(R.id.dicePickDiceBtn);
        mDiceRollButton = view.findViewById(R.id.diceRollDiceBtn);
        mDiceRollResult = view.findViewById(R.id.diceResult);
    }

    private void addDicesToList() {
        dices.add(new Dice("K4", 4, false));
        dices.add(new Dice("K6", 6, false));
        dices.add(new Dice("K8", 8, false));
        dices.add(new Dice("K10", 10, false));
        dices.add(new Dice("K12", 12, false));
        dices.add(new Dice("K20", 20, false));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
        super.onDetach();
        mListener = null;
    }

    private void setupDialog() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.custom_dice_dialog, items);
        builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.diceFragmentPickADiceText);
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPickedDice = which;
            }
        });
        builder.setPositiveButton(R.string.diceFragmentPickADiceOKBtn, new DialogInterface.OnClickListener() {
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


    private View.OnClickListener onClickListenerPick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPickDiceDialog.show();
        }
    };

    private View.OnClickListener onClickListenerRoll = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: Roll " + dices.get(mPickedDice).roll());
            mDiceRollResult.setText(String.valueOf(dices.get(mPickedDice).roll()));
        }
    };


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}

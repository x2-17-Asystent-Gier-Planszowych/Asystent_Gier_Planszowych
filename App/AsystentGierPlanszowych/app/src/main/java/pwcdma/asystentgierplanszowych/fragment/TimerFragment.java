package pwcdma.asystentgierplanszowych.fragment;

import android.app.Dialog;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.v4.app.Fragment;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.ikovac.timepickerwithseconds.TimePicker;

import pwcdma.asystentgierplanszowych.R;

import static android.content.Context.VIBRATOR_SERVICE;


public class TimerFragment extends Fragment {

    private final int SECOND = 1000;
    private OnFragmentInteractionListener mListener;
    private Button timerStart;
    private Button timerCancel;
    private Button timerPause;
    private Button timerPick;
    private TextView timeTextView;
    private Button testdialogButtonOk;
    private NumberPicker minutesPicker;
    private NumberPicker secondsPicker;
    private boolean isOn = false;
    private boolean isPause = false;
    private CountDownTimer timer;

    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;
    private NumberPicker mSecondPicker;

    private MediaPlayer mediaPlayer;
    private AudioAttributes audioAttributes;
    Vibrator vibrator;

    public TimerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        timerPick = (Button) view.findViewById(R.id.testTimerPick);
        timeTextView = (TextView) view.findViewById(R.id.testTextViewTime);
        timerStart = (Button) view.findViewById(R.id.timerStart);
        timerPause = (Button) view.findViewById(R.id.timerPause);
        timerCancel = (Button) view.findViewById(R.id.timerCancel);

        timerStart.setOnClickListener(onClickListenerStart);
        timerPause.setOnClickListener(onClickListenerPause);
        timerCancel.setOnClickListener(onClickListenerCancel);

        timerPick.setOnClickListener(onClickListenerPick);


        minutesPicker = (NumberPicker) view.findViewById(R.id.minutesPicker);
        secondsPicker = (NumberPicker) view.findViewById(R.id.secondPicker);

        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);

        setTimerPicker(minutesPicker);
        setTimerPicker(secondsPicker);

        return view;
    }

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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setTimerPicker(NumberPicker picker) {
        picker.setMaxValue(59);
        picker.setMinValue(0);
    }


    private View.OnClickListener onClickListenerPick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOn) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_time_picker);
                dialog.setTitle("Nastaw klpesydrę");

                mHourPicker = (NumberPicker) dialog.findViewById(R.id.testHourPicker);
                mMinutePicker = (NumberPicker) dialog.findViewById(R.id.testMinutesPicker);
                mSecondPicker = (NumberPicker) dialog.findViewById(R.id.testSecondsPicker);

                mHourPicker.setMaxValue(24);
                mHourPicker.setMinValue(0);
                mMinutePicker.setMaxValue(59);
                mMinutePicker.setMinValue(0);
                mSecondPicker.setMaxValue(59);
                mSecondPicker.setMinValue(0);



                testdialogButtonOk = (Button) dialog.findViewById(R.id.testDialogButtonOK);
                testdialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        timeTextView.setText(String.format("%02d:%02d:%02d",mHourPicker.getValue(),mMinutePicker.getValue(),mSecondPicker.getValue()));
                    }
                });
                dialog.show();
            }
        }
    };

    private View.OnClickListener onClickListenerStart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOn) {
                isOn = true;
                isPause = false;
                disablePicker();
                startCounting();
            }
        }
    };

    private View.OnClickListener onClickListenerPause = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isOn) {
                isPause = true;
                isOn = false;
            }
        }
    };

    private View.OnClickListener onClickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isOn) {
                isPause = true;
                isOn = false;
                secondsPicker.setValue(0);
                minutesPicker.setValue(0);
                unlockPicker();
            }
        }
    };

    private void startCounting() {
        int time2 = 3600 *  mHourPicker.getValue() +  60 * mMinutePicker.getValue() + mSecondPicker.getValue();
        final int time = 60 * minutesPicker.getValue() + secondsPicker.getValue();
        timer = new CountDownTimer(time2 * SECOND, SECOND) {
            public void onTick(long millisUntilFinished) {
                if (isPause) {
                    timer.cancel();
                    return;
                }
                int[] time2 = splitToComponentTimes(millisUntilFinished / SECOND);
                timeTextView.setText(String.format("%02d:%02d:%02d",time2[0],time2[1],time2[2]));
                minutesPicker.setValue(time2[1]);
                secondsPicker.setValue(time2[2]);
            }

            public void onFinish() {
                isOn = false;
                unlockPicker();
                timeTextView.setText("00:00:00");
                secondsPicker.setValue(0);
                if (vibrator.hasVibrator()) {
                    vibre();
                }
            }
        }.start();
    }

    private void vibre() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(SECOND, 10));
        } else {
            ((Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE)).vibrate(SECOND);
        }
    }

    private void unlockPicker() {
        minutesPicker.setEnabled(true);
        secondsPicker.setEnabled(true);
    }

    private void disablePicker() {
        minutesPicker.setEnabled(false);
        secondsPicker.setEnabled(false);
    }

    public static int[] splitToComponentTimes(long longVal) {
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;
    }
}
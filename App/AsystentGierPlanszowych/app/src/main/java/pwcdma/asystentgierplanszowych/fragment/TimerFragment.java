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
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

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
    private boolean isOn = false;
    private boolean isPause = false;
    private CountDownTimer timer;

    private ImageView mPlayImgBtn;
    private ImageView mPauseImgBtn;
    private ImageView mStopImgBtn;
    private ImageView mRewindImgBtn;
    private ImageView mSetTimerCountdownImgBtn;

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
        timeTextView = view.findViewById(R.id.textViewTime);
        mPlayImgBtn = view.findViewById(R.id.timerPlayButton);
        mPauseImgBtn = view.findViewById(R.id.timerPauseButton);
        mStopImgBtn = view.findViewById(R.id.timerStopButton);
        mRewindImgBtn = view.findViewById(R.id.timerRewindButton);
        mSetTimerCountdownImgBtn = view.findViewById(R.id.timerSetCountdownButton);

        mPlayImgBtn.setOnClickListener(onClickListenerPlay);
        mPauseImgBtn.setOnClickListener(onClickListenerPause);
        mStopImgBtn.setOnClickListener(onClickListenerCancel);
        mSetTimerCountdownImgBtn.setOnClickListener(onClickListenerSetCountdownTimer);
        mRewindImgBtn.setOnClickListener(onClickListenerRewind);


        vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);


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


    private View.OnClickListener onClickListenerSetCountdownTimer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOn) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.custom_time_picker);
                dialog.setTitle("Nastaw klpesydrę");

                mHourPicker = dialog.findViewById(R.id.timePickerHourPicker);
                mMinutePicker = dialog.findViewById(R.id.timePickerMinutesPicker);
                mSecondPicker = dialog.findViewById(R.id.timePickerSecondsPicker);

                mHourPicker.setMaxValue(24);
                mHourPicker.setMinValue(0);
                mMinutePicker.setMaxValue(59);
                mMinutePicker.setMinValue(0);
                mSecondPicker.setMaxValue(59);
                mSecondPicker.setMinValue(0);


                testdialogButtonOk = (Button) dialog.findViewById(R.id.timePickerOkButton);
                testdialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        timeTextView.setText(String.format("%02d:%02d:%02d", mHourPicker.getValue(), mMinutePicker.getValue(), mSecondPicker.getValue()));
                    }
                });
                dialog.show();
            }
        }
    };

    private View.OnClickListener onClickListenerRewind = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // TODO: 29.10.2017 zaimplementować metodę do resetowania odliczania do ustawionego czasu w timepickerze
            // TODO: 29.10.2017 ogarnąc to tak by nie powstawał koflikt z anulowaniem odliczania
        }
    };


    private View.OnClickListener onClickListenerPlay = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isOn) {
                isOn = true;
                isPause = false;
                mPlayImgBtn.setEnabled(false);
                mPlayImgBtn.setVisibility(View.INVISIBLE);
                mPauseImgBtn.setEnabled(true);
                mPauseImgBtn.setVisibility(View.VISIBLE);
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
                mPlayImgBtn.setEnabled(true);
                mPlayImgBtn.setVisibility(View.VISIBLE);
                mPauseImgBtn.setEnabled(false);
                mPauseImgBtn.setVisibility(View.INVISIBLE);
            }
        }
    };

    private View.OnClickListener onClickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isOn) {
                // TODO: 29.10.2017 dodać funkcjonalność, by po zanulowaniu odliczania zerować timepickera
                isPause = true;
                isOn = false;
                mPlayImgBtn.setEnabled(true);
                mPlayImgBtn.setVisibility(View.VISIBLE);
                mPauseImgBtn.setEnabled(false);
                mPauseImgBtn.setVisibility(View.INVISIBLE);
                timeTextView.setText(R.string.timerFragmentTimePlaceholder);
                unlockPicker();
            }
        }
    };

    private void startCounting() {
        int time2 = 3600 * mHourPicker.getValue() + 60 * mMinutePicker.getValue() + mSecondPicker.getValue();

        timer = new CountDownTimer(time2 * SECOND, SECOND) {
            public void onTick(long millisUntilFinished) {
                if (isPause) {
                    timer.cancel();
                    return;
                }
                int[] time2 = splitToComponentTimes(millisUntilFinished / SECOND);

                timeTextView.setText(String.format("%02d:%02d:%02d", time2[0], time2[1], time2[2]));
            }

            public void onFinish() {
                isOn = false;
                unlockPicker();
                timeTextView.setText(R.string.timerFragmentTimePlaceholder);
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
        mSetTimerCountdownImgBtn.setEnabled(true);
    }

    private void disablePicker() {
        mSetTimerCountdownImgBtn.setEnabled(false);
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
package com.tuda.whatagame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tuda.whatagame.brokenview.AutoBrokenView;
import com.tuda.whatagame.brokenview.BrokenCallback;
import com.tuda.whatagame.brokenview.BrokenTouchListener;
import com.tuda.whatagame.brokenview.BrokenView;
import com.tuda.whatagame.customview.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameFragment extends BaseFragment {

    private TextView mTvResult;
    private TextView mTvOption1;
    private TextView mTvOption2;
    private TextView mTvOption3;
    private TextView mTvOption4;
    private TextView mTvTime;
    private String mStrOperator = "+-x";
    private Random mRandom;
    private StringBuilder mStrBuilder;
    private StringBuilder mStrBuilderOperator;
    private String mStrRequest1, mStrRequest2, mStrRequest3, mStrRequest4, mStrResult;
    private List<String> mListRequest;
    private RelativeLayout mRlOption1;
    private RelativeLayout mRlOption2;
    private RelativeLayout mRlOption3;
    private RelativeLayout mRlOption4;
    private int mIntResult;
    private CountDownTimer mTimer;
    private int mIntTime;
    private int mIntQuestion = 0;
    private int mIntUserPoint;
    private CircleProgressBar mPbTime;
    private AutoBrokenView mAutoWhite;
    private BrokenView mBrokenView;
    private Point mPoint;
    private int mIntPrefQuestion = 0;
    private Vibrator mVibrator;

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initListeners() {
        mRlOption1.setOnClickListener(this);
        mRlOption2.setOnClickListener(this);
        mRlOption3.setOnClickListener(this);
        mRlOption4.setOnClickListener(this);

        mBrokenView.setCallback(new BrokenCallback() {
            @Override
            public void onCancel(View v) {
                super.onCancel(v);
                Log.d(TAG,"onCancel");
                moveToReplay();
            }
        });

    }

    @Override
    protected void initViews(final View view) {
        mTvResult = view.findViewById(R.id.tv_result);
        mTvOption1 = view.findViewById(R.id.tv_option_1);
        mTvOption2 = view.findViewById(R.id.tv_option_2);
        mTvOption3 = view.findViewById(R.id.tv_option_3);
        mTvOption4 = view.findViewById(R.id.tv_option_4);

        mRlOption1 = view.findViewById(R.id.rl_option_1);
        mRlOption2 = view.findViewById(R.id.rl_option_2);
        mRlOption3 = view.findViewById(R.id.rl_option_3);
        mRlOption4 = view.findViewById(R.id.rl_option_4);

        mTvTime = view.findViewById(R.id.tv_time);

        mPbTime = view.findViewById(R.id.pb_time);

        mBrokenView = BrokenView.add2Window(getActivity());

        view.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                mPoint = new Point((int)rect.exactCenterX(),(int)rect.exactCenterY());

                mAutoWhite = new AutoBrokenView.Builder(mBrokenView).setViewPoint(view,mPoint).build();
            }
        });

        mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {
        mRandom = new Random();
    }

    @Override
    protected void getData() {
        mIntPrefQuestion = PrefUtil.getSharedPreferenceInt(getActivity(), Constants.USER_QUESTION, 0);
        mIntUserPoint = PrefUtil.getSharedPreferenceInt(getActivity(), Constants.USER_POINT, 0);
        countDownTimer();
        setRandomQuestion();
    }


    private void countDownTimer() {
        mTimer = new CountDownTimer(11000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"onTick");
                mIntTime = (int) (millisUntilFinished / 1000);
                mPbTime.setProgressWithAnimation(mIntTime - 1);
                mTvTime.setText((mIntTime - 1) + "");
            }

            @Override
            public void onFinish() {
                Log.d(TAG,"onFinish");
                mPbTime.stopAnimation();
                PrefUtil.setSharedPreferenceInt(getActivity(), Constants.USER_POINT, mIntUserPoint);

                mAutoWhite.setAutoBrokenView();

            }
        };
        mTimer.start();
    }

    private void setRandomQuestion() {
        mStrRequest1 = getRandomRequest();
        mStrRequest2 = getRandomRequest();
        mStrRequest3 = getRandomRequest();
        mStrRequest4 = getRandomRequest();

        mTvOption1.setText(mStrRequest1);
        mTvOption2.setText(mStrRequest2);
        mTvOption3.setText(mStrRequest3);
        mTvOption4.setText(mStrRequest4);

        mListRequest = new ArrayList<>();
        mListRequest.add(mStrRequest1);
        mListRequest.add(mStrRequest2);
        mListRequest.add(mStrRequest3);
        mListRequest.add(mStrRequest4);

        mStrResult = getRandomResult();

        mIntResult = calculate(mStrResult.replace("x","*"));
        mTvResult.setText(mIntResult + "");
    }

    private String getRandomResult() {
        return mListRequest.get(mRandom.nextInt(mListRequest.size()));
    }

    private String getRandomRequest() {
        mStrBuilder = new StringBuilder();
        mStrBuilder.append(getRandomNumber());
        mStrBuilder.append(getRandomOperator());
        mStrBuilder.append(getRandomNumber());
        mStrBuilder.append(getRandomOperator());
        mStrBuilder.append(getRandomNumber());
        mStrBuilder.append(getRandomOperator());
        mStrBuilder.append(getRandomNumber());
        return mStrBuilder.toString();
    }

    private int calculate(String request) {
        int result = 0;
        String noMinus = request.replace("-", "+-");
        String[] byPluses = noMinus.split("\\+");

        for (String multipl : byPluses) {
            String[] byMultipl = multipl.split("\\*");
            int multiplResult = 1;
            for (String operand : byMultipl) {
                multiplResult *= Double.parseDouble(operand);
            }
            result += multiplResult;
        }

        return result;
    }

    private String getRandomOperator() {
        mStrBuilderOperator = new StringBuilder();
        mStrBuilderOperator.append(mStrOperator.charAt(mRandom.nextInt(mStrOperator.length())));
        return mStrBuilderOperator.toString();
    }

    private int getRandomNumber() {
        return mRandom.nextInt(9 - 1 + 1) + 1;
    }

    private void checkResult(int result) {
        if (result == mIntResult) {
            Log.d(TAG, "That's right");

            mTimer.cancel();

            mPbTime.stopAnimation();
            mPbTime.setProgress(11);

            setRandomQuestion();

            mTimer.start();
            mIntQuestion++;
            mIntPrefQuestion++;
            if (mIntQuestion % 5 == 0) {
                mIntUserPoint += 5;
                PrefUtil.setSharedPreferenceInt(getActivity(), Constants.USER_POINT, mIntUserPoint);
            }
        } else {
            if (Build.VERSION.SDK_INT >=26){
                mVibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
            }else {
                mVibrator.vibrate(500);
            }


            mAutoWhite.setAutoBrokenView();
            Log.d(TAG, "Oh no");
            mTimer.cancel();
            mPbTime.stopAnimation();
            PrefUtil.setSharedPreferenceInt(getActivity(), Constants.USER_POINT, mIntUserPoint);

        }
    }

    private void moveToReplay() {
        StartFragment startFragment = new StartFragment();
        replaceFragment(startFragment,startFragment.getClass().getName());
    }

    @Override
    public void onClick(View v) {
        String option;
        int result = 0;
        switch (v.getId()) {
            case R.id.rl_option_1:
                option = mTvOption1.getText().toString();
                result = calculate(option.replace("x","*"));
                checkResult(result);
                break;
            case R.id.rl_option_2:
                option = mTvOption2.getText().toString();
                result = calculate(option.replace("x","*"));
                checkResult(result);
                break;
            case R.id.rl_option_3:
                option = mTvOption3.getText().toString();
                result = calculate(option.replace("x","*"));
                checkResult(result);
                break;
            case R.id.rl_option_4:
                option = mTvOption4.getText().toString();
                result = calculate(option.replace("x","*"));
                checkResult(result);
                break;
        }
    }
}

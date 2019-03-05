package com.tuda.whatagame;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartFragment extends BaseFragment {

    private ImageView mIvStartGame;
    private ImageView mIvTutorialGame;
    private ImageView mIvPointGame;
    private ImageView mIvExitGame;

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_start;
    }

    @Override
    protected void initListeners() {
        mIvStartGame.setOnClickListener(this);
        mIvTutorialGame.setOnClickListener(this);
        mIvPointGame.setOnClickListener(this);
        mIvExitGame.setOnClickListener(this);
    }

    @Override
    protected void initViews(View view) {
        mIvStartGame = view.findViewById(R.id.iv_start_game);
        mIvTutorialGame = view.findViewById(R.id.iv_tutorial_game);
        mIvPointGame = view.findViewById(R.id.iv_point_game);
        mIvExitGame = view.findViewById(R.id.iv_exit_game);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {

    }

    private void moveToGame() {
        GameFragment gameFragment = new GameFragment();
        replaceFragment(gameFragment, gameFragment.getClass().getName());
    }

    private void moveToTutorial() {
        TutorialFragment tutorialFragment = new TutorialFragment();
        replaceFragment(tutorialFragment, tutorialFragment.getClass().getName());
    }

    private void moveToPoint() {
        PointFragment pointFragment = new PointFragment();
        replaceFragment(pointFragment, pointFragment.getClass().getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_start_game:
                moveToGame();
                break;
            case R.id.iv_tutorial_game:
                moveToTutorial();
                break;
            case R.id.iv_point_game:
                moveToPoint();
                break;
            case R.id.iv_exit_game:
                getActivity().finish();
                break;
        }
    }
}

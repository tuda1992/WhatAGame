package com.tuda.whatagame;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuda.whatagame.showcaseview.MaterialShowcaseSequence;
import com.tuda.whatagame.showcaseview.MaterialShowcaseView;
import com.tuda.whatagame.showcaseview.ShowcaseConfig;

public class TutorialFragment extends BaseFragment {

    private RelativeLayout mRlTime;
    private RelativeLayout mRlPoint;
    private RelativeLayout mRlQuestion;
    private LinearLayout mLlAnswer;
    private TextView mTvNumberAnswer;
    private static final String SHOWCASE_ID = "sequence example";

    @Override
    protected void onBackPressFragment() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initViews(View view) {
        mRlTime = view.findViewById(R.id.rl_time);
        mRlPoint = view.findViewById(R.id.rl_point);
        mRlQuestion = view.findViewById(R.id.rl_question);
        mLlAnswer = view.findViewById(R.id.ll_answer);
        mTvNumberAnswer = view.findViewById(R.id.tv_number_answer);
    }

    @Override
    protected void initDatas(Bundle savedInstanceState) {

    }

    @Override
    protected void getData() {
        presentShowcaseSequence();
    }

    @Override
    public void onClick(View v) {

    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(200); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(getActivity(), SHOWCASE_ID);

        sequence.setConfig(config);

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mRlTime)
                        .setDismissText("1")
                        .setContentText("Thời gian để trả lời câu hỏi, bạn có 10s/câu")
                        .withRectangleShape(true)
                        .build(),null

        );


        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mTvNumberAnswer)
                        .setDismissText("2")
                        .setContentText("Số câu trả lời đúng trong từng lần chơi")
                        .withRectangleShape(true)
                        .build(),null
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mRlPoint)
                        .setDismissText("3")
                        .setContentText("Tổng điểm có được sau các lần chơi, điểm đổi được quà nên chơi nhiều vô nhé!!!")
                        .withRectangleShape(true)
                        .build(),null
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mRlQuestion)
                        .setDismissText("4")
                        .setContentText("Phần câu hỏi mà ứng dụng đưa ra, cứ trả lời đúng 5 câu bạn sẽ nhận được 5 điểm, nếu chưa đủ mốc 5 câu mà tạch thì bạn biết rồi đó, không có điểm nào đâu!!!")
                        .withRectangleShape(true)
                        .build(),null
        );

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(getActivity())
                        .setTarget(mLlAnswer)
                        .setDismissText("5")
                        .setContentText("Cuối cùng là phần trả lời, bạn có 4 lựa chọn cho câu hỏi phía trên, tỉnh táo vào nhé, nhắc lại điểm đổi được quà đấy!!!")
                        .withRectangleShape(true)
                        .build(), new MaterialShowcaseSequence.OnSequenceItemClickListener() {
                    @Override
                    public void onClick() {
                        moveToStart();
                    }
                }
        );

        sequence.start();

    }

    private void moveToStart() {
        StartFragment startFragment = new StartFragment();
        replaceFragment(startFragment, startFragment.getClass().getName());
    }


}

package com.huang.viewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huang.viewdemo.view.GuDongView;
import com.huang.viewdemo.view.LoadingView;
import com.huang.viewdemo.view.PlayView;
import com.huang.viewdemo.view.SleepView;
import com.huang.viewdemo.view.SmileView;
import com.huang.viewdemo.view.SwipeView;
import com.huang.viewdemo.view.TimeDelayClockView;
import com.huang.viewdemo.view.TimeView;

public class MainActivity extends Activity {

    private LoadingView mLoadingView;
    private GuDongView mGuDongView;
    private TimeView mTimeView;
    private PlayView mPlayView;
    private TimeDelayClockView mTimeDelayClockView;
    private SleepView mSleepView;
    private SmileView mSmileView;
    private SwipeView mSwipeView;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //     mLoadingView= findViewById(R.id.lv_progress);
        mGuDongView = findViewById(R.id.gd_progress);
    //    mTimeView = findViewById(R.id.tv_progress);
//        mPlayView = findViewById(R.id.gv_paly);
    //    mTimeDelayClockView = findViewById(R.id.cv_dealy);
     //   mSleepView = findViewById(R.id.sv_sleep);
      //  mSmileView = findViewById(R.id.sv_smile);
//        mSwipeView = findViewById(R.id.sv_sv);
        mButton = findViewById(R.id.btn_begin);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mSmileView.startAnimation();
//                mLoadingView.setProgress(100);
//                mLoadingView.startAnimation();

                mGuDongView.setProgress(80);
                mGuDongView.startAnimation();
//
//                mTimeView.setProgress(90);
//                mTimeView.startAnimation();

 //               mPlayView.start();

//                mSleepView.setProgress(255);
//                mSleepView.startAnimation();
            }
        });
    }
}

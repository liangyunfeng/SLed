package com.lyf.app.sled.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lyf.app.sled.R;
import com.lyf.app.sled.utils.Constant;
import com.lyf.app.sled.utils.WakeLock;
import com.lyf.app.sled.widget.MarqueeSurfaceView;

/**
 * Created by yunfeng.l on 2017/11/10.
 */

public class FullScreenActivity extends AppCompatActivity {
    private final static String TAG = "FullScreenActivity";

    private float mTextSize = 100;
    private int mTextColor;
    private int mBackgroundColor;
    private int mDirection = 0;
    private int mSpeed = 4;
    private int message_shark = 0;
    private int color_cnt = 0;

    private int message_stopShark = 1;
    private boolean mIsRainbowColor = false;
    private boolean mIsLedEffect = false;
    private boolean mIsStopShark = false;
    private String mTextContent;
    MarqueeSurfaceView marqueeView;
    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Intent intent = getIntent();
        mTextContent = intent.getStringExtra("textContent");
        mBackgroundColor = intent.getIntExtra("backGroundColor", Color.BLUE);
        mTextColor = intent.getIntExtra("textColor", Color.RED);
        mDirection = intent.getIntExtra("direction", 0);
        mIsLedEffect = intent.getBooleanExtra("ledMode", false);
        mIsRainbowColor = intent.getBooleanExtra("rainbow", false);
        mSpeed = intent.getIntExtra("speed", 4) + 12;
        mIsStopShark = intent.getBooleanExtra("isStopShark", false);
        marqueeView = (com.lyf.app.sled.widget.MarqueeSurfaceView) findViewById(R.id.my_marquee);
        marqueeView.setFocusable(true);
        marqueeView.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        WakeLock.acquire(this);
        marqueeView.initMarquee(mTextColor, getResources().getDimensionPixelSize(R.dimen.led_full_text_size),
                mDirection, mSpeed, mIsRainbowColor, mBackgroundColor, mIsLedEffect);
        marqueeView.setTextAlpha(99);
        marqueeView.setText(mTextContent);

        marqueeView.startScroll();

        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == message_shark) {
                    color_cnt = color_cnt % Constant.Color.LedColorIds.length;
                    marqueeView.setTextColor(Constant.Color.LedColorIds[color_cnt]);
                    color_cnt++;

                    if (mIsStopShark) {
                        myHandler.sendEmptyMessage(message_stopShark);
                    } else {
                        myHandler.sendEmptyMessageDelayed(message_shark, 500);
                    }
                } else if (msg.what == message_stopShark) {
                    marqueeView.setTextColor(mTextColor);
                }
            }
        };
        if (mIsStopShark) {
            myHandler.sendEmptyMessage(message_stopShark);
        } else {
            myHandler.sendEmptyMessage(message_shark);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (marqueeView != null) {
            marqueeView.stopScroll();
        }
        WakeLock.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

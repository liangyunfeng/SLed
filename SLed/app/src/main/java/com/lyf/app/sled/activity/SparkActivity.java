package com.lyf.app.sled.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.lyf.app.sled.utils.WakeLock;
import com.lyf.app.sled.widget.SparkView;

/**
 * Created by yunfeng.l on 2018/1/25.
 */

public class SparkActivity extends Activity {
    private final static String TAG = "SparkActivity";

    private Context mContext;
    private SparkView mSparkView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mSparkView = new SparkView(mContext);
        setContentView(mSparkView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WakeLock.acquire(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WakeLock.release();
    }
}

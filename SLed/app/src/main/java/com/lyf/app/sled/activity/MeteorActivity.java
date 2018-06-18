package com.lyf.app.sled.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.lyf.app.sled.utils.WakeLock;
import com.lyf.app.sled.widget.MeteorView;

/**
 * Created by yunfeng.l on 2018/1/25.
 */

public class MeteorActivity extends Activity {
    private final static String TAG = "MeteorActivity";

    private Context mContext;
    private MeteorView mMeteorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        mMeteorView = new MeteorView(mContext);
        setContentView(mMeteorView);
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

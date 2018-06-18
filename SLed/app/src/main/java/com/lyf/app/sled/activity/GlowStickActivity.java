package com.lyf.app.sled.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.lyf.app.sled.R;
import com.lyf.app.sled.neon.NeonPath;
import com.lyf.app.sled.neon.NeonStyle;
import com.lyf.app.sled.neon.path.BasePath;
import com.lyf.app.sled.utils.WakeLock;
import com.lyf.app.sled.widget.NeonStickView;

/**
 * Created by yunfeng.l on 2018/1/29.
 */
public class GlowStickActivity extends Activity {
    private final static String TAG = "GlowStickActivity";

    private Context mContext;
    NeonStickView mStickView;
    NeonStyle mNeonStyle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mNeonStyle = NeonStyle.getInstance();
        boolean needLandscape = false;
        if (mNeonStyle != null && mNeonStyle.getNeonPathList() != null) {
            NeonPath neonPath = mNeonStyle.getNeonPathList().get(mNeonStyle.getNeonPathIndex());
            if (neonPath != null && neonPath.getBasePath() != null) {
                BasePath basePath = neonPath.getBasePath();
                if (basePath.getWidth() > basePath.getHeight()) {
                    needLandscape = true;
                }
            }
        }
        if (needLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mContext = this;
        setContentView(R.layout.activity_glow_stick);

        mStickView = (NeonStickView) findViewById(R.id.stick_view);
        mStickView.setDrawMargin(200);
        mStickView.setNeonStyle(mNeonStyle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WakeLock.acquire(mContext);
        if (mStickView != null) {
            mStickView.startPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mStickView != null) {
            mStickView.stopPreview();
        }
        WakeLock.release();
    }
}

package com.lyf.app.sled.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lyf.app.sled.meteor.StarManager;
import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/2/6.
 */

public class MeteorView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final static String TAG = "MeteorView";

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private int mWidth, mHeight;
    private StarManager mStarManager;
    private Thread mThread;
    private volatile boolean isRun;

    public MeteorView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        mWidth = metric.widthPixels;
        mHeight = metric.heightPixels;

        mStarManager = new StarManager(mWidth, mHeight);

        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void run() {
        long curTime = 0;
        while (isRun) {
            try {
                Log.v(TAG, "run() : " + Thread.currentThread() + ", isRun = " + isRun);
                curTime = System.currentTimeMillis();
                Canvas canvas = mHolder.lockCanvas(null);
                if (canvas != null && mStarManager != null) {
                    synchronized (mHolder) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        mStarManager.drawStars(canvas);
                    }
                }
                if (canvas != null)
                    mHolder.unlockCanvasAndPost(canvas);
                curTime = System.currentTimeMillis() - curTime;
                if (curTime < 30) {
                    Thread.sleep(30 - curTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (Constant.DEBUG)
            Log.v(TAG, "surfaceChanged");
        drawBackgound();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Constant.DEBUG)
            Log.v(TAG, "surfaceCreated");
        if (mStarManager == null) {
            mStarManager = new StarManager(mWidth, mHeight);
        }
        if (mThread == null || (mThread != null && !isRun)) {
            isRun = true;
            mThread = new Thread(this);
            mThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (Constant.DEBUG)
            Log.v(TAG, "surfaceDestroyed");
        if (mThread != null && isRun) {
            isRun = false;
            try {
                mThread.join();
            } catch (InterruptedException ex) {
            }
        }
        if (mStarManager != null) {
            mStarManager = null;
        }
    }

    private void drawBackgound() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }
}

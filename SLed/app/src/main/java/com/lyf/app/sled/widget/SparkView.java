package com.lyf.app.sled.widget;

import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lyf.app.sled.spark.SparkManager;
import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/2/6.
 */

public class SparkView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final static String TAG = "SparkView";

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private int mWidth, mHeight;
    private SparkManager mSparkManager;
    private Thread mThread;
    private volatile boolean isRun;

    public SparkView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        mWidth = metric.widthPixels;
        mHeight = metric.heightPixels;

        mSparkManager = new SparkManager(mWidth, mHeight);

        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void run() {
        long curTime = 0;
        while (isRun) {
            try {
                Log.v(TAG, "run() : " + Thread.currentThread() + ", isRun = " + isRun);       // 这行解决了进入几次卡顿的问题？
                curTime = System.currentTimeMillis();
                Canvas canvas = mHolder.lockCanvas(null);
                if (canvas != null && mSparkManager != null) {
                    synchronized (mHolder) {
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        mSparkManager.drawSpark(canvas);
                    }
                }
                if (canvas != null)
                    mHolder.unlockCanvasAndPost(canvas);
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
        if (mSparkManager == null) {
            mSparkManager = new SparkManager(mWidth, mHeight);
            //mSparkManager.setActive(true);
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
        if (mSparkManager != null) {
            //mSparkManager.setActive(false);
            mSparkManager = null;
        }
    }

    private void drawBackgound() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }
}

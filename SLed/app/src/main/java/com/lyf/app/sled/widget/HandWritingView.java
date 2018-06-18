package com.lyf.app.sled.widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lyf.app.sled.R;
import com.lyf.app.sled.neon.NeonColor;
import com.lyf.app.sled.widget.action.Action;
import com.lyf.app.sled.widget.action.ActionManager;
import com.lyf.app.sled.widget.action.PathAction;

/**
 * Created by yunfeng.l on 2018/1/26.
 */

public class HandWritingView extends SurfaceView implements SurfaceHolder.Callback {

    private final static String TAG = "HandWritingView";

    private SurfaceHolder mSurfaceHolder = null;
    private Action mCurAction = null;
    private int mCurrentColor = Color.BLACK;
    private int mCurrentSize = 10;
    private int mCurrentBackgroundColor = Color.WHITE;
    private boolean mCurrentFlashOnOff = false;
    private int mCurrentFlashFrequencyLevel = 1;
    private int mCurrentFlashColorArray[] = new int[5];
    private boolean canFlash = true;
    public Paint mPaint;
    public ActionManager mActionManager;
    private UpdateViewThread mThread;
    public int mCurrentStep = 0;
    public int mMaxStep = 0;

    ActionManager.ForwardBackCallback mUndoRedoCallback;
    ActionManager.ForwardBackCallback mForwardBackCallback = new ActionManager.ForwardBackCallback() {
        @Override
        public void onForwardBackChanged(boolean canBack, boolean canForward) {
            if (mUndoRedoCallback != null) {
                mUndoRedoCallback.onForwardBackChanged(canBack, canForward);
            }
            refreshSurfaceView();
        }
    };

    public enum ActionType {
        Point, Path, Line, Rect, Circle, FilledRect, FilledCircle, Eraser
    }

    private ActionType type = ActionType.Path;

    public HandWritingView(Context context) {
        super(context);
        init();
    }

    public HandWritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HandWritingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(mCurrentSize);
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mCurrentBackgroundColor = getResources().getColor(R.color.hand_writing_background_default_color);
        mCurrentColor = NeonColor.get()[0][4];

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        this.setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = mSurfaceHolder.lockCanvas();
        canvas.drawColor(mCurrentBackgroundColor);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        mActionManager = new ActionManager(mForwardBackCallback);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void refreshSurfaceView() {
        if (mSurfaceHolder != null) {
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(mCurrentBackgroundColor);
            if (mActionManager != null) {
                mActionManager.draw(canvas, null);
            }
            mSurfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_CANCEL) {
            return false;
        }

        float touchX = event.getRawX();
        float touchY = event.getRawY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setCurAction(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(mCurrentBackgroundColor);
                if (mActionManager != null) {
                    mActionManager.draw(canvas, null);
                }
                mCurAction.move(touchX, touchY);
                mCurAction.draw(canvas);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                break;
            case MotionEvent.ACTION_UP:
                //mActions.add(mCurAction);
                if (mActionManager != null) {
                    mActionManager.add(mCurAction);
                }
                mCurAction = null;
                break;
            default:
                break;

        }
        return super.onTouchEvent(event);
    }

    public void setCurAction(float x, float y) {
        switch (type) {
            case Path:
                mCurAction = new PathAction(x, y, mCurrentSize, mCurrentColor);
                break;
        }
    }

    public void setPaintSize(int size) {
        mCurrentSize = size;
    }

    public int getPaintSize() {
        return mCurrentSize;
    }

    public void setPaintColor(int color) {
        mCurrentColor = color;
    }

    public int getPaintColor() {
        return mCurrentColor;
    }

    public void setBGColor(int color) {
        mCurrentBackgroundColor = color;
        refreshSurfaceView();
    }

    public int getBGColor() {
        return mCurrentBackgroundColor;
    }

    public void setFlash(boolean flash) {
        canFlash = flash;
    }

    public void startShaking() {
        Log.d(TAG, "startShaking");
        if (!canFlash) {
            return;
        }
        if (mThread == null || (mThread != null && !mThread.isRun)) {
            mThread = new UpdateViewThread(mSurfaceHolder);
            mThread.start();
        }
    }

    public void stopShaking(boolean isFinal) {
        Log.d(TAG, "stopShaking");
        if (mThread != null && mThread.isRun) {
            mThread.requstExit();
        }
        if (!isFinal) {
            refreshSurfaceView();
        }
    }

    public void redo() {
        if (mActionManager != null && mActionManager.canForward()) {
            mActionManager.forward();
        }
    }

    public void undo() {
        if (mActionManager != null && mActionManager.canBack()) {
            mActionManager.back();
        }
    }

    public void setForwardBackCallback(ActionManager.ForwardBackCallback callback) {
        mUndoRedoCallback = callback;
    }

    class UpdateViewThread extends Thread {
        private SurfaceHolder holder;

        public volatile boolean isRun;//是否在运行
        public int[] colorIds = new int[]{
                R.color.aliceblue,
                R.color.bisque,
                R.color.aqua
        };
        public int color_cnt = 0;

        public UpdateViewThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        public void onDraw() {
            try {
                if (holder != null) {
                    Canvas canvas = holder.lockCanvas();
                    color_cnt = color_cnt % colorIds.length;
                    canvas.drawColor(mCurrentBackgroundColor);
                    mPaint.setColor(getResources().getColor(colorIds[color_cnt++]));
                    if (mActionManager != null) {
                        mActionManager.draw(canvas, mPaint);
                    }
                    holder.unlockCanvasAndPost(canvas);
                }
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRun) {
                Log.v(TAG, "run() : " + Thread.currentThread() + ", isRun = " + isRun);
                onDraw();
            }
        }

        public void requstExit() {
            isRun = false;
            try {
                join();
            } catch (InterruptedException ex) {

            }
        }
    }
}


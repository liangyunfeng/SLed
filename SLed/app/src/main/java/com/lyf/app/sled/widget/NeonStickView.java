package com.lyf.app.sled.widget;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lyf.app.sled.neon.NeonColor;
import com.lyf.app.sled.neon.NeonPath;
import com.lyf.app.sled.neon.NeonStyle;
import com.lyf.app.sled.neon.path.BasePath;
import com.lyf.app.sled.utils.Constant;

public class NeonStickView extends SurfaceView implements SurfaceHolder.Callback {
    private final static String TAG = "NeonStickView";
    public Context mContext;

    private float mCurSize = 15;
    private int mCurColor = NeonColor.get()[1][0];
    private int mCurFillColor = NeonColor.get()[1][4];
    ;
    private int mCurBGColor = Color.BLACK;
    private int mSpeed = 4;

    private long SleepMillisArray[] = {
            2000, 1000, 800, 600, 400, 200, 50
    };

    private boolean canFlash = true;

    private SurfaceHolder mSurfaceHolder = null;
    ;

    private Paint mPaint;
    private Paint mFillPaint;

    private NeonStickViewThread mThread;

    private NeonStyle mNeonStyle;
    private NeonPath mNeonPath;

    private BasePath mBasePath;

    private int mPosX = 0;
    private int mPosY = 0;
    private float mScale = 1.0F;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mDrawMargin = 100;

    public int[] ColorIds = new int[]{
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.DKGRAY,
            Color.CYAN
    };

    public int[] FillColorIds = new int[]{
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.DKGRAY,
            Color.CYAN
    };

    public NeonStickView(Context context) {
        this(context, null);
    }

    public NeonStickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NeonStickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public void setBGColor(int color) {
        mCurBGColor = color;
    }

    public void setPatinColor(int color) {
        mCurColor = color;
        if (mPaint != null)
            mPaint.setColor(mCurColor);
    }

    public void setPatinFillColor(int color) {
        mCurFillColor = color;
        if (mFillPaint != null)
            mFillPaint.setColor(mCurFillColor);
    }

    public void setNeonStyle(NeonStyle style) {
        mNeonStyle = style;
        initNeon();
    }

    public NeonStyle getNeonStyle() {
        return mNeonStyle;
    }

    public void initNeon() {
        if (mNeonStyle != null) {
            mCurSize = mNeonStyle.getTextSize();
            mCurColor = NeonColor.get()[mNeonStyle.getTextColorIndex()][0];
            mCurFillColor = NeonColor.get()[mNeonStyle.getTextColorIndex()][4];
            mCurBGColor = mNeonStyle.getBackgroundColor();
            mSpeed = mNeonStyle.getSpeed();
            canFlash = mNeonStyle.isFlash();
            for (int i = 0; i < ColorIds.length; i++) {
                ColorIds[i] = NeonColor.get()[mNeonStyle.getFlashColorIndex(i)][0];
                FillColorIds[i] = NeonColor.get()[mNeonStyle.getFlashColorIndex(i)][4];
            }
            if (mNeonStyle.getNeonPathList() != null) {
                mNeonPath = mNeonStyle.getNeonPathList().get(mNeonStyle.getNeonPathIndex());
            }
            if (mNeonPath != null) {
                mBasePath = mNeonPath.getBasePath();
                updateFromNeonPath();
            }
        }
    }

    public void setFlash(boolean flash) {
        canFlash = flash;
    }

    public void setFlashColor(int index, int color) {
        if (index >= 0 && index < ColorIds.length) {
            ColorIds[index] = color;
        }
    }

    public void init(AttributeSet attrs, int defStyleAttr) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mCurSize / 5.0F);
        mPaint.setMaskFilter(new BlurMaskFilter(mCurSize / 2.0F, BlurMaskFilter.Blur.SOLID));

        mFillPaint = new Paint();
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setStrokeJoin(Paint.Join.ROUND);
        mFillPaint.setStrokeCap(Paint.Cap.ROUND);

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public void setNeonPath(NeonPath neonPath) {
        mNeonPath = neonPath;
        if (mNeonPath != null) {
            mBasePath = mNeonPath.getBasePath();
            updateFromNeonPath();
        }
    }

    public NeonPath getNeonPath() {
        return mNeonPath;
    }

    public void updateFromNeonPath() {
        if (mBasePath == null)
            return;
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        if (mBasePath.getWidth() != 0 && mBasePath.getHeight() != 0) {
            float widthScale = ((float) (mWidth - mDrawMargin) / mBasePath.getWidth());
            float heightScale = ((float) (mHeight - mDrawMargin) / mBasePath.getHeight());
            mScale = widthScale > heightScale ? heightScale : widthScale;
        }
        mScale = ((float) Math.floor(10.0F * mScale) / 10.0F);
        mPosX = ((int) ((mWidth - mScale * mBasePath.getWidth()) / 2.0F));
        mPosY = ((int) ((mHeight - mScale * mBasePath.getHeight()) / 2.0F));
        if (Constant.DEBUG)
            Log.v("lyf", "updateFromNeonPath mScale = " + mScale, new Exception());
        mBasePath.setScale(mScale);
    }

    public void setDrawMargin(int margin) {
        mDrawMargin = margin;
    }

    public long getSleepMilliseconds() {
        return SleepMillisArray[mSpeed];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        updateFromNeonPath();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mThread != null)
            mThread.isRun = true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mThread != null)
            mThread.isRun = false;
    }

    public void startPreview() {
        if (mThread == null || (mThread != null && !mThread.isRun)) {
            mThread = new NeonStickViewThread(mSurfaceHolder);
            mThread.start();
        }
    }

    public void stopPreview() {
        if (mThread != null && mThread.isRun) {
            mThread.requstExit();
        }
    }

    class NeonStickViewThread extends Thread {

        private SurfaceHolder holder;
        public volatile boolean isRun;
        public int color_cnt = 0;

        public NeonStickViewThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        public void onDraw() {
            try {
                if (holder != null && mBasePath != null) {
                    Canvas canvas = holder.lockCanvas();
                    canvas.drawColor(mCurBGColor);
                    if (canFlash) {
                        color_cnt = color_cnt % ColorIds.length;
                        mFillPaint.setColor(FillColorIds[color_cnt]);
                        mPaint.setColor(ColorIds[color_cnt++]);
                    } else {
                        mPaint.setColor(mCurColor);
                        mFillPaint.setColor(mCurFillColor);
                    }
                    canvas.translate(mPosX, mPosY);
                    canvas.scale(mBasePath.getScale(), mBasePath.getScale());
                    if (mNeonPath.getNeonPathKey() != NeonPath.LOVE) {
                        canvas.drawPath(mBasePath.path(), mFillPaint);
                    }
                    canvas.drawPath(mBasePath.path(), mPaint);
                    if (Constant.DEBUG)
                        Log.v("lyf", "x = " + mPosX + ", y = " + mPosY + ", scale = " + mBasePath.getScale() + "," + mBasePath.getWidth() + ", " + mBasePath.getHeight() + ", " + mWidth + ", " + mHeight + ", " + getMeasuredWidth() + ", " + getMeasuredHeight());
                    holder.unlockCanvasAndPost(canvas);
                }
                Thread.sleep(getSleepMilliseconds());
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


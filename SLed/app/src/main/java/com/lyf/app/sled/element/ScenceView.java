package com.lyf.app.sled.element;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.lyf.app.sled.R;
import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/2/8.
 */

public abstract class ScenceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private final static String TAG = "ScenceView";

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Thread mSpriteThread;
    private boolean isRunning;

    protected Scence mScence;

    private int itemNum;
    private int itemColor = 0xffffffff;
    private boolean randColor = false;
    private boolean clip_scale = false;
    private int clip_radius = 0;
    private int clip_step = 0;
    private int clip_dur_time = 2000;

    public ScenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    public ScenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScenceView);
        itemNum = a.getInt(R.styleable.ScenceView_itemNum, 0);
        itemColor = a.getColor(R.styleable.ScenceView_itemColor, 0xffffffff);
        randColor = a.getBoolean(R.styleable.ScenceView_randColor, false);
        clip_scale = a.getBoolean(R.styleable.ScenceView_clipScale, false);
        a.recycle();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    public ScenceView(Context context) {
        super(context);
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    /**
     * 需要初始话的效果场景
     *
     * @return
     */
    protected abstract Scence initScence(int itemNum);

    /**
     * need init scence
     *
     * @param itemNum
     * @param itemColor
     * @return
     */
    protected abstract Scence initScence(int itemNum, int itemColor);

    /**
     * need init scence
     *
     * @param itemNum
     * @param itemColor
     * @param randColor
     * @return
     */
    protected abstract Scence initScence(int itemNum, int itemColor, boolean randColor);

    private void init() {

        if (itemNum == 0) {
            itemNum = 100;
        }

        clip_step = getWidth() < getHeight() ? getHeight() : getWidth();
        clip_step /= 2;
        clip_radius = 0;
        mScence = initScence(itemNum, itemColor, randColor);
    }

    private void animLogic() {
        if (mScence != null) {
            mScence.move();
        }

        clip_radius += 30 * clip_step / clip_dur_time;
        if (clip_radius > clip_step) {
            clip_scale = false;
        }
    }

    @Override
    public void run() {
        long curTime = 0;
        while (isRunning) {
            try {
                Log.v(TAG, "run() : " + Thread.currentThread() + ", isRunning = " + isRunning);
                curTime = System.currentTimeMillis();
                animLogic();
                mCanvas = mHolder.lockCanvas(null);
                if (mCanvas != null) {
                    synchronized (mHolder) {
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        if (mScence != null) {
                            if (clip_scale) {
                                Path path = new Path();
                                path.addCircle(getWidth() / 2, getHeight() / 2, clip_radius, Path.Direction.CCW);
                                mCanvas.clipPath(path);
                                mScence.draw(mCanvas);
                            } else {
                                mScence.draw(mCanvas);
                            }
                        }
                    }
                }
                mHolder.unlockCanvasAndPost(mCanvas);
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
        //drawBackgound();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Constant.DEBUG)
            Log.v(TAG, "surfaceCreated");
        if (mScence == null) {
            init();
        }
        if (mSpriteThread == null || (mSpriteThread != null && !isRunning)) {
            isRunning = true;
            mSpriteThread = new Thread(this);
            mSpriteThread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (Constant.DEBUG)
            Log.v(TAG, "surfaceDestroyed");
        if (mSpriteThread != null && isRunning) {
            isRunning = false;
            try {
                mSpriteThread.join();
            } catch (InterruptedException ex) {
            }
        }
    }

    private void drawBackgound() {
        mCanvas = mHolder.lockCanvas();
        mCanvas.drawColor(Color.BLACK);
        mHolder.unlockCanvasAndPost(mCanvas);
    }
}
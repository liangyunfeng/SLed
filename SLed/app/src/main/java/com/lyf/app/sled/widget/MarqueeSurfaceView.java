package com.lyf.app.sled.widget;

/**
 * Created by yunfeng.l on 2017/11/8.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.lyf.app.sled.R;
import com.lyf.app.sled.utils.Constant;
import com.lyf.app.sled.utils.Utils;

public class MarqueeSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final static String TAG = "MarqueeSurfaceView";
    public Context mContext;

    private float mTextSize = 100; //字体大小
    private int mTextColor = Color.RED; //字体的颜色
    private int mBackgroundColor = Color.WHITE;//背景色
    private boolean mIsRepeat;//是否重复滚动
    private int mStartPoint;// 开始滚动的位置  0是从最左面开始    1是从最末尾开始
    private int mDirection;//滚动方向 0 向左滚动   1向右滚动
    private int mSpeed;//滚动速度

    private boolean mIsRainbowColor = true;
    private boolean mIsLedEffect = true;

    private SurfaceHolder holder;
    private TextPaint mTextPaint;
    private MarqueeViewThread mThread;
    private String margueeString;
    private int textWidth = 0, textHeight = 0;
    private int ShadowColor = Color.BLACK;
    public int currentX = 0;// 当前x的位置
    public int sepX = 4;//每一步滚动的距离

    public static final int ROLL_OVER = 100;

    public MarqueeSurfaceView(Context context) {
        this(context, null);
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs, defStyleAttr);
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    public void setSpeed(int speed) {
        sepX = speed;
    }

    public void setBackGroundColor(int color) {
        mBackgroundColor = color;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        if (mTextPaint != null)
            mTextPaint.setColor(mTextColor);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int actionMasked = ev.getActionMasked() & MotionEvent.ACTION_MASK;
        if (actionMasked == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setTextAlpha(int alpha) {
        if (mTextPaint != null)
            mTextPaint.setAlpha(alpha);
    }

    public void setText(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            measurementsText(msg);
        }
    }

    public void setShadowMode(boolean isShadowMode) {
        if (isShadowMode) {
            mTextPaint.setShadowLayer(5, 3, 3, ShadowColor);
        }
    }

    public void setIsRainbowColor(boolean isRainbowColor) {
        mIsRainbowColor = isRainbowColor;
        if (mIsRainbowColor) {
            LinearGradient mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                    Constant.Color.LedRainBowColorIds, null, Shader.TileMode.REPEAT);
            if (mTextPaint != null)
                mTextPaint.setShader(mLinearGradient);
        } else {
            LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, 0,
                    Color.RED, Color.RED, Shader.TileMode.REPEAT);
            if (mTextPaint != null) {
                mTextPaint.setShader(null);
                mTextPaint.setColor(mTextColor);
                mTextPaint.setStrokeWidth(0.5f);
                mTextPaint.setFakeBoldText(true);
            }
        }
    }

    public void setIsLedEffect(boolean isLedEffect) {
        mIsLedEffect = isLedEffect;
    }

    public void initMarquee(int color, int textSize, int direction, int speed, boolean isRainbowColor, int backgroundColor, boolean isLedEffect) {
        mTextColor = color;
        mTextSize = textSize;
        mBackgroundColor = backgroundColor;
        sepX = speed;
        mIsRainbowColor = isRainbowColor;
        mDirection = direction;
        mIsLedEffect = isLedEffect;
    }

    public void init(AttributeSet attrs, int defStyleAttr) {
        mTextColor = Color.RED;
        mTextSize = 300;
        mBackgroundColor = Color.BLUE;
        mIsRepeat = true;
        mStartPoint = 0;
        mDirection = 0;
        mSpeed = 20;
        mIsRainbowColor = true;
        mIsLedEffect = true;
        holder = this.getHolder();
        holder.addCallback(this);
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
    }

    protected void measurementsText(String msg) {
        margueeString = msg;
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setStrokeWidth(0.5f);
        mTextPaint.setFakeBoldText(true);
        // 设定阴影(柔边, X 轴位移, Y 轴位移, 阴影颜色)
//        mTextPaint.setShadowLayer(5, 3, 3, ShadowColor);
        textWidth = (int) mTextPaint.measureText(margueeString);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //textHeight = (int) fontMetrics.bottom;
        textHeight = (int) Math.abs(fontMetrics.descent + fontMetrics.ascent);
        if (Constant.DEBUG)
            Log.v(TAG, "MarqueeViewThread: 0 : descent = " + fontMetrics.descent + ", ascent = " + fontMetrics.ascent + ", textHeight = " + textHeight);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        if (mStartPoint == 0)
            currentX = 0;
        else
            currentX = width - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
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

    /**
     * 开始滚动
     */
    public void startScroll() {
        if (mThread != null && mThread.isRun)
            return;
        mThread = new MarqueeViewThread(holder);//创建一个绘图线程
        mThread.start();
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        if (mThread != null) {
            mThread.isRun = false;
            mThread.interrupt();
        }
        mThread = null;
    }

    /**
     * 线程
     */
    class MarqueeViewThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;//是否在运行

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        int centeYLine = paddingTop + contentHeight / 2;//中心线

        private Bitmap renderText(CharSequence text, Paint paint) {
            //Bitmap bitmap = Bitmap.createBitmap(mDrawableWidth, mDrawableHeight, Bitmap.Config.ARGB_8888);
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
            //canvas.drawText(text.toString(),0,yPos,paint);
            canvas.drawText(margueeString, currentX, centeYLine + Utils.dip2px(getContext(), textHeight) / 2, paint);
            return bitmap;
        }

        public MarqueeViewThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        public void onDraw() {
            try {
                synchronized (holder) {
                    if (TextUtils.isEmpty(margueeString)) {
                        Thread.sleep(1000);//睡眠时间为1秒
                        return;
                    }
                    Canvas canvas = holder.lockCanvas();
                    int paddingLeft = getPaddingLeft();
                    int paddingTop = getPaddingTop();
                    int paddingRight = getPaddingRight();
                    int paddingBottom = getPaddingBottom();

                    int contentWidth = getWidth() - paddingLeft - paddingRight;
                    int contentHeight = getHeight() - paddingTop - paddingBottom;

                    int centeYLine = paddingTop + contentHeight / 2;//中心线
                    if (Constant.DEBUG)
                        Log.v(TAG, "MarqueeViewThread: 1 : paddingLeft = " + paddingLeft + ", paddingTop = " + paddingTop + ", paddingRight = " + paddingRight + ", paddingBottom = " + paddingBottom + ", contentWidth = " + contentWidth + ", contentHeight = " + contentHeight);
                    if (mDirection == 0) {//向左滚动
                        if (currentX <= -textWidth) {
                            if (!mIsRepeat) {//如果是不重复滚动
                                mHandler.sendEmptyMessage(ROLL_OVER);
                            }
                            currentX = contentWidth;
                        } else {
                            currentX -= sepX;
                        }
                    } else {//  向右滚动
                        if (currentX >= contentWidth) {
                            if (!mIsRepeat) {//如果是不重复滚动
                                mHandler.sendEmptyMessage(ROLL_OVER);
                            }
                            currentX = -textWidth;
                        } else {
                            currentX += sepX;
                        }

                    }

                    if (canvas != null && mIsLedEffect) {
                        canvas.drawColor(getResources().getColor(R.color.darkblue));
                        Paint circlePaint = new Paint();
                        circlePaint.setColor(Color.BLACK);
                        int x = 15, y = 15;
                        int radius = 15;

                        for (x = 15; x < getWidth(); ) {
                            for (y = 15; y < getHeight() + 15; ) {
                                canvas.drawCircle(x, y, radius, circlePaint);
                                y = y + radius * 2;
                            }
                            x = x + radius * 2;
                        }
                        final Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
                        mTextPaint.setAlpha(95);
                        mTextPaint.setXfermode(xfermode);
                    } else if (canvas != null) {
                        canvas.drawColor(mBackgroundColor);
                    }
                    LinearGradient mLinearGradient;
                    if (mIsRainbowColor) {
                        mLinearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, Constant.Color.LedRainBowColorIds, null, Shader.TileMode.REPEAT);
                        mTextPaint.setShader(mLinearGradient);
                    } else {
                        mTextPaint.setShader(null);
                    }
                    //Bitmap bit = renderText(margueeString,mTextPaint);
                    //canvas.drawText(margueeString, currentX, centeYLine + Utils.dip2px(getContext(), textHeight) / 2, mTextPaint);
                    canvas.drawText(margueeString, currentX, centeYLine + textHeight / 2, mTextPaint);
                    if (Constant.DEBUG)
                        Log.v(TAG, "MarqueeViewThread: 2 : currentX = " + currentX + ", centeYLine = " + centeYLine + ", textHeight = " + textHeight + ", " + (centeYLine + Utils.dip2px(getContext(), textHeight) / 2));
                    holder.unlockCanvasAndPost(canvas);//结束锁定画图，并提交改变。

                    int a = textWidth / margueeString.trim().length();
                    int b = a / sepX;
                    int c = mSpeed / b == 0 ? 1 : mSpeed / b;
                    if (Constant.DEBUG)
                        Log.d(TAG, "mSpeed = " + mSpeed + c);
                    Thread.sleep(c);//睡眠时间为移动的频率
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRun) {
                onDraw();
            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ROLL_OVER:
                    stopScroll();
                    if (mOnMargueeListener != null) {
                        mOnMargueeListener.onRollOver();
                    }
                    break;
            }
        }
    };

    public void reset() {
        int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        if (mStartPoint == 0)
            currentX = 0;
        else
            currentX = contentWidth;
    }

    /**
     * 滚动回调
     */
    public interface OnMargueeListener {
        void onRollOver();//滚动完毕
    }

    OnMargueeListener mOnMargueeListener;

    public void setOnMargueeListener(OnMargueeListener mOnMargueeListener) {
        this.mOnMargueeListener = mOnMargueeListener;
    }
}

package com.lyf.app.sled.music;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.lyf.app.sled.element.ElementItem;
import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/2/8.
 */

public class MusicKey extends ElementItem {

    private Paint mPaint = new Paint();
    private float mPosX;
    private float mCenterY;
    private float maxH;
    private float mDistance;
    private float mItemWidth;
    private float mItemHeight;
    private int id;
    private final int itemNum = 30;
    private int mLevel;
    private int count = 0;
    private float X_SPACE = 2;

    public MusicKey(float x, int width, int height) {
        this(width, height);
        mPosX = x;
    }

    public MusicKey(float x, int width, int height, int color) {
        this(x, width, height);
        mColor = color;
        mPaint.setColor(color);
        initPaint();
    }

    public MusicKey(float x, int width, int height, int color, boolean randColor) {
        super(width, height, color, randColor);
        mPosX = x;
        mCenterY = height / 2.0F;
        maxH = height / 3.0F;
        mItemHeight = maxH / itemNum;
        mDistance = mItemHeight / 4.0F;
        randomColor();
        mPaint.setColor(mColor);
        initPaint();
    }

    public MusicKey(float x, int width, int height, int color, boolean randColor, float itemWidth, int id) {
        super(width, height, color, randColor);
        this.id = id;
        mPosX = x;
        mCenterY = height / 2.0F;
        maxH = height / 3.0F;
        mItemWidth = itemWidth;
        mItemHeight = maxH / itemNum;
        mDistance = mItemHeight / 4.0F;
        randomColor();
        mPaint.setColor(mColor);
        initPaint();
    }

    public MusicKey(int width, int height) {
        super(width, height);

        mCenterY = height / 2.0F;
        maxH = height / 3.0F;
        mItemHeight = maxH / itemNum;
        mDistance = mItemHeight / 4.0F;

        mPaint.setColor(0xffffffff);
        initPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        //绘制向上的音符块
        mPaint.setAlpha(255);
        for (int i = 0; i < mLevel; i++) {
            canvas.drawRect(mPosX + X_SPACE, mCenterY - (i + 1) * mItemHeight - i * mDistance, mPosX + mItemWidth - X_SPACE, mCenterY - i * (mItemHeight + mDistance), mPaint);
        }
        //绘制向下的镜像音符块
        mPaint.setAlpha(50);
        for (int i = 0; i < mLevel; i++) {
            canvas.drawRect(mPosX + X_SPACE, mCenterY + i * (mItemHeight + mDistance), mPosX + mItemWidth - X_SPACE, mCenterY + (i + 1) * mItemHeight + i * mDistance, mPaint);
        }
    }

    @Override
    public void move() {
        count++;
        if (count >= 6) {
            count = 0;
            mLevel = getLevel(id);
        }
    }

    private int getLevel(int id) {
        int level;
        if (id < 2 || id > 22) {
            level = 1 + mRandom.nextInt(itemNum / 3);
        } else if ((id >= 2 && id <= 4) || (id >= 20 && id <= 22)) {
            level = 4 + mRandom.nextInt(itemNum / 2 - 3);
        } else {
            level = 6 + mRandom.nextInt(itemNum - 5);
        }
        return level;
    }

    private void initPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(10F, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected void randomColor() {
        if (isRandColor) {
            mColor = Constant.Color.ColorIds[(id % Constant.Color.ColorIds.length)];
        }
    }
}

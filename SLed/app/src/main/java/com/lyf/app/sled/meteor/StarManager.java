package com.lyf.app.sled.meteor;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

import com.lyf.app.sled.R;
import com.lyf.app.sled.utils.Constant;

import java.util.Random;

/**
 * Created by yunfeng.l on 2018/2/7.
 */

public class StarManager {

    private Paint mStarPaint;

    private int mWidth, mHeight;

    private int MAX_WIDTH;

    private int MIN_WIDTH = 10;

    private int MAX_HEIGHT;

    private int MIN_HEIGHT = 5;

    private int MAX_SPEED;

    private int MIN_SPEED = 20;

    private float BLUR_SIZE = 30F;

    private int STAR_NUM = 10;

    private Star mStars[];

    private Random mRandom = new Random();

    private int[] ColorIds = new int[]{
            Color.BLUE, Color.GREEN, Color.RED,
            Color.WHITE, Color.YELLOW, Color.MAGENTA
    };

    public StarManager(int width, int height) {
        this(width, height, 10);
    }

    public StarManager(int width, int height, int num) {
        mWidth = width;
        mHeight = height;
        STAR_NUM = num;
        mStars = new Star[STAR_NUM];
        for (int i = 0; i < mStars.length; i++) {
            mStars[i] = new Star(mWidth, mHeight);
        }
        MAX_WIDTH = mWidth / 12;
        MAX_HEIGHT = mHeight / 2;
        MAX_SPEED = mHeight / 20;
        MIN_SPEED = mHeight / 50;
        initPaint();
    }

    public void drawStars(Canvas canvas) {
        if (Constant.DEBUG)
            Log.v("lyf", "drawStars 1");
        for (Star star : mStars) {
            if (!star.isActive()) {
                makeStar(star);
            }
            //mStarPaint.setColor(ColorIds[mRandom.nextInt(ColorIds.length)]);
            //mStarPaint.setColor(star.getColor());
            star.draw(canvas, mStarPaint);
            star.move();
            if ((star.getCount() % 8) == 0) {
                star.setColor(ColorIds[mRandom.nextInt(ColorIds.length)]);
            }
        }
    }

    private void makeStar(Star star) {
        if (star != null) {
            star.setWidth(mRandom.nextInt(MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH);
            star.setHeight(mRandom.nextInt(MAX_HEIGHT - MIN_HEIGHT) + MIN_HEIGHT);
            star.setSpeed(mRandom.nextInt(MAX_SPEED - MIN_SPEED) + MIN_SPEED);
            star.setX(mRandom.nextInt(mWidth - star.getWidth()));
            star.setY(-star.getHeight());
            star.setColor(ColorIds[mRandom.nextInt(ColorIds.length)]);
            star.setCount(0);
            star.setActive(true);
        }
    }

    private void initPaint() {
        mStarPaint = new Paint();
        mStarPaint.setAntiAlias(true);
        mStarPaint.setDither(true);
        mStarPaint.setStrokeJoin(Paint.Join.ROUND);
        mStarPaint.setStrokeCap(Paint.Cap.ROUND);
        //mStarPaint.setStrokeWidth(3);
        //mStarPaint.setStyle(Paint.Style.STROKE);
        //mStarPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));

        //mStarPaint.setShadowLayer(float radius, float dx, float dy, int shadowColor);
        //mStarPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        mStarPaint.setStyle(Paint.Style.FILL);
        mStarPaint.setMaskFilter(new BlurMaskFilter(BLUR_SIZE, BlurMaskFilter.Blur.SOLID));
    }
}

package com.lyf.app.sled.meteor;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yunfeng.l on 2018/2/7.
 */

public class Star implements StarBase {
    private int mSreenWidth;
    private int mSreenHeight;
    private int mWidth;
    private int mHeight;
    private int mPosX;
    private int mPosY;
    private int mSpeed;
    private float mBlueSize;
    private int mColor;
    private boolean isActive;
    private int cnt = 0;

    public Star(int screenWidth, int screenHeight) {
        mSreenWidth = screenWidth;
        mSreenHeight = screenHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getX() {
        return mPosX;
    }

    public void setX(int x) {
        mPosX = x;
    }

    public int getY() {
        return mPosY;
    }

    public void setY(int y) {
        mPosY = y;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public float getBlueSize() {
        return mBlueSize;
    }

    public void setBlueSize(float size) {
        mBlueSize = size;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCount(int count) {
        cnt = count;
    }

    public int getCount() {
        return cnt;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        if ((mPosY + mHeight) <= 0 || mPosY >= mSreenHeight) {
            return;
        }
        paint.setColor(mColor);
        canvas.drawRect(mPosX, mPosY > 0 ? mPosY : 0, mPosX + mWidth, (mPosY + mHeight) > mSreenHeight ? mSreenHeight : (mPosY + mHeight), paint);

        /*if ((mPosY + (cnt * 20 % mHeight) + 5) <= 0 || (mPosY + (cnt * 20 % mHeight)) >= mSreenHeight) {
            return;
        }
        paint.setColor(Color.WHITE);
        canvas.drawRect(mPosX, (mPosY + (cnt * 20 % mHeight)) > 0 ? (mPosY + (cnt * 20 % mHeight)) : 0, mPosX + mWidth, (mPosY + (cnt * 20 % mHeight) + 5) < mSreenHeight ? (mPosY + (cnt * 20 % mHeight) + 5) : mSreenHeight, paint);*/
    }

    @Override
    public void move() {
        mPosY += mSpeed;
        if (cnt < Integer.MAX_VALUE) {
            cnt++;
        } else {
            cnt = 0;
        }
        if (mPosY >= mSreenHeight) {
            isActive = false;
            cnt = 0;
        }
    }
}

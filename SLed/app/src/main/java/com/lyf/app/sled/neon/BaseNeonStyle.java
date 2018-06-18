package com.lyf.app.sled.neon;

import android.graphics.Color;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public abstract class BaseNeonStyle {

    protected int mBackgroundColor = Color.BLACK;
    protected int mTextColorIndex = 1;
    protected int mSpeed = 4;
    protected boolean isFlash = true;
    protected int mTextSize = 15;
    protected int mFlashColorIndex[] = {0, 4, 8, 12, 16};

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setTextColorIndex(int index) {
        mTextColorIndex = index;
    }

    public int getTextColorIndex() {
        return mTextColorIndex;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public boolean isFlash() {
        return isFlash;
    }

    public void setFlash(boolean flash) {
        isFlash = flash;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int size) {
        mTextSize = size;
    }

    public void setFlashColorIndex(int index, int pos) {
        mFlashColorIndex[index] = pos;
    }

    public int getFlashColorIndex(int index) {
        return mFlashColorIndex[index];
    }
}

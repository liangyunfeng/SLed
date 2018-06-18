package com.lyf.app.sled.widget.action;

/**
 * Created by yunfeng.l on 2018/1/25.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Action {
    int mColor;
    int mSize;

    public Action() {
        mColor = Color.BLACK;
    }

    public Action(int color) {
        this.mColor = color;
    }

    public void setPaintSize(int size) {
        mSize = size;
    }

    public int getPaintSize() {
        return mSize;
    }

    public void setPaintColor(int color) {
        mColor = color;
    }

    public int getPaintColor() {
        return mColor;
    }

    public abstract void draw(Canvas canvas);

    public abstract void move(float mx, float my);

    public abstract void draw(Paint paint, Canvas canvas);
}
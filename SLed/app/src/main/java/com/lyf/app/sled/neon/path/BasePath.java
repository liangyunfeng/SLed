package com.lyf.app.sled.neon.path;

import android.graphics.Path;
import android.util.Log;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public abstract class BasePath {

    protected Path mPath;
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected float mScale = 1.0F;
    //protected float mThumbnailScale = 1.0F;

    public BasePath(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setScale(float scale) {
        Log.v("lyf", "setScale scale = " + scale, new Exception());
        mScale = scale;
    }

    public float getScale() {
        return mScale;
    }

    /*public void setThumbnailScale(float scale) {
        Log.v("lyf", "mThumbnailScale mThumbnailScale = " + scale, new Exception());
        mThumbnailScale = scale;
    }

    public float getThumbnailScale() {
        return mThumbnailScale;
    }*/

    public abstract Path path();

}

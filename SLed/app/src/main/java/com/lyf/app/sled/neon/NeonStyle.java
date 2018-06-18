package com.lyf.app.sled.neon;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class NeonStyle extends BaseNeonStyle {

    private static NeonStyle mNeonStyle;

    private List<NeonPath> mNeonPathList;
    private int mCurNeonPath = 0;

    private NeonStyle() {
        init();
    }

    public static NeonStyle getInstance() {
        if (mNeonStyle == null) {
            synchronized (NeonStyle.class) {
                if (mNeonStyle == null) {
                    mNeonStyle = new NeonStyle();
                }
            }
        }
        return mNeonStyle;
    }

    public void addNeonPath(NeonPath path) {
        if (mNeonPathList == null)
            mNeonPathList = new ArrayList<>();
        mNeonPathList.add(path);
    }

    public List<NeonPath> getNeonPathList() {
        return mNeonPathList;
    }

    private void init() {
        addNeonPath(new NeonPath(NeonPath.LOVE));
        addNeonPath(new NeonPath(NeonPath.STAR));
        addNeonPath(new NeonPath(NeonPath.MAGICSTAR));
        addNeonPath(new NeonPath(NeonPath.CROWN));
        addNeonPath(new NeonPath(NeonPath.BEAR));
        addNeonPath(new NeonPath(NeonPath.STARMOON));
        addNeonPath(new NeonPath(NeonPath.SWORD));
    }

    public NeonStyle reset() {
        if (mNeonStyle != null) {
            mCurNeonPath = 0;
            mBackgroundColor = Color.BLACK;
            mTextColorIndex = 1;
            mTextSize = 15;
            isFlash = true;
            mSpeed = 4;
            mFlashColorIndex[0] = 0;
            mFlashColorIndex[1] = 4;
            mFlashColorIndex[2] = 8;
            mFlashColorIndex[3] = 12;
            mFlashColorIndex[4] = 16;
        }
        return mNeonStyle;
    }

    public int getNeonPathIndex() {
        return mCurNeonPath;
    }

    public void setNeonPathIndex(int index) {
        mCurNeonPath = index;
    }
}

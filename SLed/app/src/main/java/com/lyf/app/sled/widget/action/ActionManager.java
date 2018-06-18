package com.lyf.app.sled.widget.action;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yunfeng.l on 2018/1/26.
 */

public class ActionManager {
    private List<Action> mActions;
    private int mCurrentStep = 0;
    private int mMaxStep = 0;
    private boolean canForward = false;
    private ForwardBackCallback mForwardBackCallback;
    private HashMap<Integer, BlurMaskFilter> mBlurMap;

    public ActionManager() {
        this(null);
    }

    public ActionManager(ForwardBackCallback callback) {
        mActions = new ArrayList<>();
        mForwardBackCallback = callback;
        mBlurMap = new HashMap<>();
    }

    public boolean canForward() {
        return canForward;
    }

    public boolean canBack() {
        return mCurrentStep > 0;
    }

    public boolean forward() {
        if (!canForward || mCurrentStep == mMaxStep) {
            return canForward;
        }
        mCurrentStep++;
        if (mCurrentStep == mMaxStep) {
            canForward = false;
        }
        callbackForwardBack();
        return canForward;
    }

    public boolean back() {
        if (isEmpty())
            return false;

        mCurrentStep--;
        if (!canForward) {
            canForward = true;
        }
        callbackForwardBack();
        return mCurrentStep == 0;
    }

    public boolean isEmpty() {
        return mCurrentStep == 0;
    }

    public boolean add(Action action) {
        boolean needUpdate = false;
        if (canForward && mCurrentStep != mMaxStep) {
            removeAfter(mCurrentStep);
            if (canForward) {
                canForward = false;
                needUpdate = true;
            }
        }
        mActions.add(action);
        mCurrentStep++;
        mMaxStep = mCurrentStep;
        if (mCurrentStep == 1) {
            needUpdate = true;
        }
        if (needUpdate) {
            callbackForwardBack();
        }
        return true;
    }

    private void removeAfter(int index) {
        if (mMaxStep != mActions.size()) {
            return;
        }
        while (mMaxStep > index) {
            mActions.remove(mMaxStep - 1);
            mMaxStep--;
        }
    }

    public List<Action> getActions() {
        return mActions;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isEmpty()) {
            return;
        }
        for (int i = 0; i < mCurrentStep; i++) {
            Action a = mActions.get(i);
            if (a != null) {
                if (paint != null) {
                    //paint.setMaskFilter(new BlurMaskFilter(a.getPaintSize() + 10, BlurMaskFilter.Blur.SOLID));
                    a.draw(paint, canvas);
                } else {
                    a.draw(canvas);
                }
            }
        }
    }

    private void callbackForwardBack() {
        if (mForwardBackCallback != null) {
            mForwardBackCallback.onForwardBackChanged(canBack(), canForward);
        }
    }

    public void setForwardBackCallback(ForwardBackCallback callback) {
        mForwardBackCallback = callback;
    }

    public interface ForwardBackCallback {
        public void onForwardBackChanged(boolean canBack, boolean canForward);
    }
}
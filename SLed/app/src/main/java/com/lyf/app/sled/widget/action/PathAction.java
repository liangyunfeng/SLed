package com.lyf.app.sled.widget.action;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/1/26.
 */

public class PathAction extends Action {
    private final static String TAG = "PathAction";

    Path mPath;

    public PathAction() {
        mPath = new Path();
        mSize = 1;
    }

    public PathAction(float x, float y, int size, int color) {
        super(color);
        mPath = new Path();
        this.mSize = size;
        mPath.moveTo(x, y);
        mPath.lineTo(x, y);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(mColor);
        //paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
        paint.setStrokeWidth(mSize);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPath(mPath, paint);
    }

    @Override
    public void move(float mx, float my) {
        mPath.lineTo(mx, my);
    }

    @Override
    public void draw(Paint paint, Canvas canvas) {
        if (Constant.DEBUG)
            Log.d(TAG, "draw" + paint.getColor());
        canvas.drawPath(mPath, paint);
    }
}

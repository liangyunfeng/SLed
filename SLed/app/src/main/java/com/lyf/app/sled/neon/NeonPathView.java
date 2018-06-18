package com.lyf.app.sled.neon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lyf.app.sled.neon.path.BasePath;
import com.lyf.app.sled.utils.Constant;

/**
 * Created by yunfeng.l on 2018/1/30.
 */

public class NeonPathView extends View {

    private final static String TAG = "NeonPathView";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private NeonPath mNeonPath;
    private float mScale = 1.0F;
    private int mPosX = 0;
    private int mPosY = 0;

    public NeonPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private boolean hasView() {
        return (mNeonPath != null) && (mNeonPath.getPath() != null) && (mNeonPath.getBasePath() != null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasView()) {
            mPaint.setStrokeWidth(5.0F);
            int[] neonColorArray = NeonColor.get()[mNeonPath.getColorId()];
            mPaint.setColor(neonColorArray[4]);   // yellow color
            //mPaint.setColor(Color.BLACK);
            canvas.save();
            canvas.translate(mPosX, mPosY);
            canvas.scale(mScale, mScale);
            canvas.drawPath(mNeonPath.getPath(), mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mNeonPath == null) {
            return;
        }
        if (mNeonPath.getBasePath() == null) {
            return;
        }
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        BasePath basePath = mNeonPath.getBasePath();
        if (basePath.getWidth() != 0 && basePath.getHeight() != 0) {
            float widthScale = (((float) (width - 50)) / basePath.getWidth());
            float heightScale = (((float) (height - 50)) / basePath.getHeight());
            mScale = widthScale > heightScale ? heightScale : widthScale;
        }
        mScale = ((float) Math.floor(10.0F * mScale) / 10.0F);
        mPosX = ((int) ((width - mScale * basePath.getWidth()) / 2.0F));
        mPosY = ((int) ((height - mScale * basePath.getHeight()) / 2.0F));
        //basePath.setThumbnailScale(mScale);
        if (Constant.DEBUG)
            Log.v(TAG, "onMeasure: x = " + mPosX + ", y = " + mPosY + ", mScale = " + mScale + ", " + width + ", " + height + ", " + basePath.getWidth() + ", " + basePath.getHeight());
    }

    public void setNeonPath(NeonPath neonPath) {
        if (Constant.DEBUG)
            Log.v(TAG, "setNeonPath");
        mNeonPath = neonPath;
    }
}

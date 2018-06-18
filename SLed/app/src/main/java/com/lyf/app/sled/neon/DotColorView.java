package com.lyf.app.sled.neon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.lyf.app.sled.R;

/**
 * Created by yunfeng.l on 2018/1/31.
 */

public class DotColorView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBorderPaint;
    private int padding = 10;
    private int[] patternColors;
    private int strokeWidth = 5;

    public DotColorView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        mPaint.setStyle(Paint.Style.FILL);
        mBorderPaint = new Paint(mPaint);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(strokeWidth);
        mBorderPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.yellow, context.getTheme()));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (patternColors == null || mPaint == null) {
            return;
        }
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (patternColors != null && patternColors.length > 0 && width > 0 && height > 0) {
            int cenX = width / 2;
            int cenY = height / 2;
            int radius = cenX - strokeWidth - padding;
            if (isSelected())
                canvas.drawCircle(cenX, cenY, radius + 2, mBorderPaint);
            mPaint.setColor(patternColors[(patternColors.length - 1)]);
            canvas.drawCircle(cenX, cenY, radius, mPaint);
        }
    }

    public void setPatternColors(int[] patternColors) {
        this.patternColors = patternColors;
    }
}

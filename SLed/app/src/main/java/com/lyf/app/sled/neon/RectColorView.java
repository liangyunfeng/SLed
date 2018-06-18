package com.lyf.app.sled.neon;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;

import com.lyf.app.sled.R;

/**
 * Created by yunfeng.l on 2018/1/31.
 */

public class RectColorView extends View {
    private Paint mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public RectColorView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(4);
        mBorderPaint.setColor(getResources().getColor(R.color.white));
        mBorderPaint.setMaskFilter(new BlurMaskFilter(20F, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);




    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (width > 0 && height > 0) {
            canvas.drawRect(0, 0, width, height, mBorderPaint);
        }
    }
}

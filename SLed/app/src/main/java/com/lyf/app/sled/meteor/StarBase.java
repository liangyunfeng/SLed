package com.lyf.app.sled.meteor;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yunfeng.l on 2018/2/7.
 */

public interface StarBase {

    /**
     * 绘制效果
     *
     * @param canvas
     */
    public void draw(Canvas canvas, Paint paint);

    /**
     * 效果元素变化
     */
    public void move();

}

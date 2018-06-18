package com.lyf.app.sled.element;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yunfeng.l on 2018/2/7.
 */

public interface Element {

    /**
     * 绘制效果
     *
     * @param canvas
     */
    public void draw(Canvas canvas);

    /**
     * 效果元素变化
     */
    public void move();

}

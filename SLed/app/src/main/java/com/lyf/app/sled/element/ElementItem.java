package com.lyf.app.sled.element;

import java.util.Random;

/**
 * Created by yunfeng.l on 2018/2/8.
 */

public abstract class ElementItem implements Element {
    /**
     * 显示区域的宽度
     */
    protected int width;
    /**
     * 显示区域的高度
     */
    protected int height;
    /**
     * 效果元素的随机对象
     */
    protected Random mRandom;

    protected int mColor;

    protected boolean isRandColor;

    public ElementItem(int width, int height){
        this.width = width;
        this.height = height;
        mRandom = new Random();
    }

    public ElementItem(int width, int height, int color){
        this.width = width;
        this.height = height;
        mColor = color;
        mRandom = new Random();
    }

    public ElementItem(int width, int height, int color, boolean randColor){
        this.width = width;
        this.height = height;
        mColor = color;
        isRandColor = randColor;
        mRandom = new Random();
    }

    protected void randomColor(){
        if(isRandColor) {
            int alpha = 200;
            int r = mRandom.nextInt(255);
            int g = mRandom.nextInt(255);
            int b = mRandom.nextInt(255);
            mColor = alpha << 24 | r << 16 | g << 8 | b;
        }
    }
}

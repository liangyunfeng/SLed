package com.lyf.app.sled.element;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * 效果场景基类
 * Created by yunfeng.l on 2018/2/8.
 */

public abstract class Scence {

    protected int itemNum = 0;//效果元素数量
    protected int itemColor = 0;//粒子颜色
    protected boolean randColor = false;//rand color
    //效果场景宽高
    protected int width;
    protected int height;
    //效果容器
    protected ArrayList<ElementItem> list = new ArrayList<ElementItem>();
    //如果有图片的需要此属性
    protected Bitmap mBitmap;

    /**
     * 效果场景构造
     *
     * @param width   显示区域宽
     * @param height  显示区域宽
     * @param itemNum 显示区域元素数量
     */
    public Scence(int width, int height, int itemNum) {
        this.width = width;
        this.height = height;
        this.itemNum = itemNum;
        initScence();
    }

    /**
     * 效果场景构造
     *
     * @param width     显示区域宽
     * @param height    显示区域宽
     * @param itemNum   显示区域元素数量
     * @param itemColor 元素color
     */
    public Scence(int width, int height, int itemNum, int itemColor) {
        this.width = width;
        this.height = height;
        this.itemNum = itemNum;
        this.itemColor = itemColor;
        initScence();
    }

    /**
     * @param width
     * @param height
     * @param itemNum
     * @param itemColor
     * @param randColor if true,itemColor Invalid
     */
    public Scence(int width, int height, int itemNum, int itemColor, boolean randColor) {
        this.width = width;
        this.height = height;
        this.itemNum = itemNum;
        this.itemColor = itemColor;
        this.randColor = randColor;
        initScence();
    }

    /**
     * 效果场景构造
     *
     * @param width   显示区域宽
     * @param height  显示区域宽
     * @param itemNum 显示区域元素数量
     * @param bitmap  图片
     */
    public Scence(int width, int height, int itemNum, Bitmap bitmap) {
        this.width = width;
        this.height = height;
        this.itemNum = itemNum;
        mBitmap = bitmap;
        initScence();
    }

    /**
     * 必须要实现的初始场景方法，需要
     */
    protected abstract void initScence();

    public void draw(Canvas canvas) {
        if (list.size() == 0) {
            throw new RuntimeException("Not initScence yet.");
        }
        for (Element item : list) {
            item.draw(canvas);
        }
    }

    public void move() {
        if (list.size() == 0) {
            throw new RuntimeException("Not initScence yet.");
        }
        for (Element item : list) {
            item.move();
        }
    }
}

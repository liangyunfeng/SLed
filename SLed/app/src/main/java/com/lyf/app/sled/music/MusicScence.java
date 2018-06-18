package com.lyf.app.sled.music;

import android.util.Log;

import com.lyf.app.sled.element.Scence;

/**
 * Created by yunfeng.l on 2018/2/8.
 */

public class MusicScence extends Scence{

    public MusicScence(int width, int height, int itemNum) {
        super(width, height, itemNum);
    }

    public MusicScence(int width, int height, int itemNum, int itemColor) {
        super(width, height, itemNum, itemColor);
    }

    public MusicScence(int width, int height, int itemNum, int itemColor, boolean randColor) {
        super(width, height, itemNum, itemColor, randColor);
    }

    @Override
    protected void initScence() {
        float itemSize = (width * 1.0F) / itemNum;
        for(int i = 0; i < itemNum; i++){
            list.add(new MusicKey(i * itemSize, width, height, itemColor, randColor, itemSize, i));
        }
    }
}

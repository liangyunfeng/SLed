package com.lyf.app.sled.music;

import android.content.Context;
import android.util.AttributeSet;

import com.lyf.app.sled.element.Scence;
import com.lyf.app.sled.element.ScenceView;

/**
 * Created by yunfeng.l on 2018/2/8.
 */

public class MusicScenceView extends ScenceView {

    public MusicScenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MusicScenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicScenceView(Context context) {
        super(context);
    }

    @Override
    protected Scence initScence(int itemNum) {
        int width = getWidth();
        int height = getHeight();

        return new MusicScence(width, height, itemNum);
    }

    @Override
    protected Scence initScence(int itemNum, int itemColor) {
        int width = getWidth();
        int height = getHeight();

        return new MusicScence(width, height, itemNum, itemColor);
    }

    @Override
    protected Scence initScence(int itemNum, int itemColor, boolean randColor) {
        int width = getWidth();
        int height = getHeight();

        return new MusicScence(width, height, itemNum, itemColor, randColor);
    }
}

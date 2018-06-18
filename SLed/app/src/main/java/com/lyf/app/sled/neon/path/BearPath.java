package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class BearPath extends BasePath {

    public BearPath() {
        super(62, 121);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(52.0F, 20.0F);
            mPath.cubicTo(51.825001F, 20.0F, 51.654999F, 19.983F, 51.483002F, 19.974001F);
            mPath.cubicTo(52.458F, 22.461F, 53.0F, 25.167F, 53.0F, 28.0F);
            mPath.cubicTo(53.0F, 37.327F, 47.193001F, 45.292F, 39.0F, 48.493F);
            mPath.lineTo(39.0F, 79.625F);
            mPath.cubicTo(36.575001F, 82.908997F, 35.0F, 88.116997F, 35.0F, 94.0F);
            mPath.cubicTo(35.0F, 98.473F, 35.914001F, 102.55F, 37.417F, 105.694F);
            mPath.cubicTo(39.013F, 107.318F, 40.0F, 109.543F, 40.0F, 112.0F);
            mPath.cubicTo(40.0F, 116.971F, 35.971001F, 121.0F, 31.0F, 121.0F);
            mPath.cubicTo(26.028999F, 121.0F, 22.0F, 116.971F, 22.0F, 112.0F);
            mPath.cubicTo(22.0F, 109.543F, 22.987F, 107.318F, 24.583F, 105.694F);
            mPath.cubicTo(26.086F, 102.55F, 27.0F, 98.473F, 27.0F, 94.0F);
            mPath.cubicTo(27.0F, 88.116997F, 25.424999F, 82.908997F, 23.0F, 79.625F);
            mPath.lineTo(23.0F, 48.493F);
            mPath.cubicTo(14.807F, 45.292F, 9.0F, 37.327F, 9.0F, 28.0F);
            mPath.cubicTo(9.0F, 25.167F, 9.542F, 22.461F, 10.517F, 19.974001F);
            mPath.cubicTo(10.345F, 19.983F, 10.175F, 20.0F, 10.0F, 20.0F);
            mPath.cubicTo(4.477F, 20.0F, 0.0F, 15.523F, 0.0F, 10.0F);
            mPath.cubicTo(0.0F, 4.477F, 4.477F, 0.0F, 10.0F, 0.0F);
            mPath.cubicTo(15.179F, 0.0F, 19.438F, 3.936F, 19.948999F, 8.98F);
            mPath.cubicTo(23.197001F, 7.089F, 26.969999F, 6.0F, 31.0F, 6.0F);
            mPath.cubicTo(35.029999F, 6.0F, 38.803001F, 7.089F, 42.050999F, 8.98F);
            mPath.cubicTo(42.562F, 3.936F, 46.820999F, 0.0F, 52.0F, 0.0F);
            mPath.cubicTo(57.522999F, 0.0F, 62.0F, 4.477F, 62.0F, 10.0F);
            mPath.cubicTo(62.0F, 15.523F, 57.522999F, 20.0F, 52.0F, 20.0F);
            mPath.close();
        }
        return mPath;
    }
}

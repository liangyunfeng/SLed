package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class MagicStarPath extends BasePath {

    public MagicStarPath() {
        super(58, 123);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(46.546001F, 36.252998F);
            mPath.cubicTo(51.682999F, 53.681999F, 48.494999F, 59.57F, 37.0F, 53.939999F);
            mPath.lineTo(37.0F, 81.625F);
            mPath.cubicTo(34.575001F, 84.908997F, 33.0F, 90.116997F, 33.0F, 96.0F);
            mPath.cubicTo(33.0F, 100.473F, 33.914001F, 104.55F, 35.417F, 107.694F);
            mPath.cubicTo(37.013F, 109.318F, 38.0F, 111.543F, 38.0F, 114.0F);
            mPath.cubicTo(38.0F, 118.971F, 33.971001F, 123.0F, 29.0F, 123.0F);
            mPath.cubicTo(24.028999F, 123.0F, 20.0F, 118.971F, 20.0F, 114.0F);
            mPath.cubicTo(20.0F, 111.543F, 20.987F, 109.318F, 22.583F, 107.694F);
            mPath.cubicTo(24.086F, 104.55F, 25.0F, 100.473F, 25.0F, 96.0F);
            mPath.cubicTo(25.0F, 90.116997F, 23.424999F, 84.908997F, 21.0F, 81.625F);
            mPath.lineTo(21.0F, 53.926998F);
            mPath.cubicTo(9.484F, 59.578999F, 6.288F, 53.695F, 11.429F, 36.252998F);
            mPath.cubicTo(-5.803F, 22.950001F, -3.568F, 16.02F, 18.136F, 15.461F);
            mPath.cubicTo(25.370001F, -5.158F, 32.605F, -5.158F, 39.839001F, 15.461F);
            mPath.cubicTo(61.542F, 16.02F, 63.778F, 22.950001F, 46.546001F, 36.252998F);
            mPath.close();
            mPath.moveTo(35.749001F, 21.382F);
            mPath.cubicTo(31.26F, 8.87F, 26.771F, 8.87F, 22.281F, 21.382F);
            mPath.cubicTo(8.813F, 21.721001F, 7.426F, 25.926001F, 18.118999F, 33.998001F);
            mPath.cubicTo(14.285F, 46.720001F, 17.917F, 49.319F, 29.014999F, 41.796001F);
            mPath.cubicTo(40.113998F, 49.319F, 43.745998F, 46.720001F, 39.910999F, 33.998001F);
            mPath.cubicTo(50.605F, 25.926001F, 49.217999F, 21.721001F, 35.749001F, 21.382F);
            mPath.close();
        }
        return mPath;
    }
}

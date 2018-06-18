package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class StarPath extends BasePath {

    public StarPath() {
        super(86, 196);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(65.93F, 52.799F);
            mPath.lineTo(69.571999F, 81.998001F);
            mPath.lineTo(51.0F, 73.264F);
            mPath.lineTo(51.0F, 190.0F);
            mPath.cubicTo(51.0F, 193.314F, 48.313999F, 196.0F, 45.0F, 196.0F);
            mPath.lineTo(41.0F, 196.0F);
            mPath.cubicTo(37.686001F, 196.0F, 35.0F, 193.314F, 35.0F, 190.0F);
            mPath.lineTo(35.0F, 73.264F);
            mPath.lineTo(16.427F, 81.998001F);
            mPath.lineTo(20.069F, 52.799F);
            mPath.lineTo(0.006F, 31.322001F);
            mPath.lineTo(28.827999F, 25.77F);
            mPath.lineTo(43.0F, 0.002F);
            mPath.lineTo(57.171001F, 25.77F);
            mPath.lineTo(85.994003F, 31.322001F);
            mPath.lineTo(65.93F, 52.799F);
            mPath.close();
            mPath.moveTo(50.580002F, 34.827999F);
            mPath.lineTo(43.0F, 21.000999F);
            mPath.lineTo(35.419998F, 34.827999F);
            mPath.lineTo(20.003F, 37.806999F);
            mPath.lineTo(30.735001F, 49.331001F);
            mPath.lineTo(28.787001F, 64.999001F);
            mPath.lineTo(43.0F, 58.293999F);
            mPath.lineTo(57.213001F, 64.999001F);
            mPath.lineTo(55.264999F, 49.331001F);
            mPath.lineTo(65.997002F, 37.806999F);
            mPath.lineTo(50.580002F, 34.827999F);
            mPath.close();
        }
        return mPath;
    }
}

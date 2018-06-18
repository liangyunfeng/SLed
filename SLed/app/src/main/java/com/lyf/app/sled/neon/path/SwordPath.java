package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class SwordPath extends BasePath {

    public SwordPath() {
        super(34, 184);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(26.0F, 155.0F);
            mPath.lineTo(20.896999F, 155.0F);
            mPath.lineTo(20.0F, 181.0F);
            mPath.cubicTo(20.0F, 182.657F, 18.657F, 184.0F, 17.0F, 184.0F);
            mPath.cubicTo(15.343F, 184.0F, 14.0F, 182.657F, 14.0F, 181.0F);
            mPath.lineTo(13.103F, 155.0F);
            mPath.lineTo(9.0F, 155.0F);
            mPath.lineTo(0.0F, 147.0F);
            mPath.cubicTo(0.0F, 145.895F, 0.895F, 145.0F, 2.0F, 145.0F);
            mPath.lineTo(12.041F, 145.0F);
            mPath.lineTo(14.0F, 3.0F);
            mPath.cubicTo(14.0F, 1.343F, 15.343F, 0.0F, 17.0F, 0.0F);
            mPath.cubicTo(18.657F, 0.0F, 20.0F, 1.343F, 20.0F, 3.0F);
            mPath.lineTo(21.959F, 145.0F);
            mPath.lineTo(32.0F, 145.0F);
            mPath.cubicTo(33.105F, 145.0F, 34.0F, 145.895F, 34.0F, 147.0F);
            mPath.lineTo(26.0F, 155.0F);
            mPath.close();
        }
        return mPath;
    }
}

package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class CrownPath extends BasePath {

    public CrownPath() {
        super(66, 118);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(60.758999F, 21.888F);
            mPath.lineTo(51.903F, 47.002998F);
            mPath.lineTo(41.0F, 46.772999F);
            mPath.lineTo(41.0F, 76.625F);
            mPath.cubicTo(38.575001F, 79.908997F, 37.0F, 85.116997F, 37.0F, 91.0F);
            mPath.cubicTo(37.0F, 95.473F, 37.914001F, 99.550003F, 39.417F, 102.694F);
            mPath.cubicTo(41.013F, 104.318F, 42.0F, 106.543F, 42.0F, 109.0F);
            mPath.cubicTo(42.0F, 113.971F, 37.971001F, 118.0F, 33.0F, 118.0F);
            mPath.cubicTo(28.028999F, 118.0F, 24.0F, 113.971F, 24.0F, 109.0F);
            mPath.cubicTo(24.0F, 106.543F, 24.987F, 104.318F, 26.583F, 102.694F);
            mPath.cubicTo(28.086F, 99.550003F, 29.0F, 95.473F, 29.0F, 91.0F);
            mPath.cubicTo(29.0F, 85.116997F, 27.424999F, 79.908997F, 25.0F, 76.625F);
            mPath.lineTo(25.0F, 46.772999F);
            mPath.lineTo(14.108F, 47.002998F);
            mPath.lineTo(5.417F, 21.806F);
            mPath.cubicTo(2.546F, 21.806F, 0.004F, 19.09F, 0.004F, 15.961F);
            mPath.cubicTo(0.004F, 12.752F, 2.71F, 10.036F, 5.909F, 10.036F);
            mPath.cubicTo(9.106F, 10.036F, 11.73F, 12.752F, 11.73F, 15.961F);
            mPath.cubicTo(11.73F, 16.867001F, 11.484F, 17.691F, 10.335F, 19.502001F);
            mPath.lineTo(18.780001F, 24.129999F);
            mPath.lineTo(22.306999F, 12.01F);
            mPath.cubicTo(20.011F, 11.928F, 17.715F, 9.378F, 17.715F, 6.25F);
            mPath.cubicTo(17.715F, 2.794F, 20.500999F, -0.005F, 23.945999F, -0.005F);
            mPath.cubicTo(27.306999F, -0.005F, 30.177F, 2.794F, 30.177F, 6.25F);
            mPath.cubicTo(30.177F, 8.143F, 29.191999F, 10.365F, 27.306999F, 11.271F);
            mPath.lineTo(33.127998F, 23.224001F);
            mPath.lineTo(33.292F, 23.224001F);
            mPath.lineTo(38.867001F, 11.271F);
            mPath.cubicTo(37.308998F, 10.036F, 36.409F, 8.225F, 36.409F, 6.25F);
            mPath.cubicTo(36.409F, 2.794F, 39.195F, -0.005F, 42.639999F, -0.005F);
            mPath.cubicTo(46.000999F, -0.005F, 48.870998F, 2.794F, 48.870998F, 6.25F);
            mPath.cubicTo(48.870998F, 9.214F, 47.147999F, 11.189F, 44.608002F, 12.01F);
            mPath.lineTo(47.476002F, 24.129999F);
            mPath.lineTo(55.591999F, 19.584F);
            mPath.cubicTo(54.855999F, 18.76F, 54.280998F, 17.444F, 54.280998F, 15.961F);
            mPath.cubicTo(54.280998F, 12.834F, 56.905998F, 10.118F, 60.102001F, 10.118F);
            mPath.cubicTo(63.299F, 10.118F, 66.004997F, 12.834F, 66.004997F, 15.961F);
            mPath.cubicTo(66.004997F, 19.007F, 63.709F, 21.476F, 60.758999F, 21.888F);
            mPath.close();
        }
        return mPath;
    }
}

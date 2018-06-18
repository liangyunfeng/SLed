package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class StarMoonPath extends BasePath {

    public StarMoonPath() {
        super(54, 143);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(32.835999F, 58.175999F);
            mPath.lineTo(31.875F, 139.84399F);
            mPath.cubicTo(31.875F, 141.50101F, 30.532F, 142.84399F, 28.875F, 142.84399F);
            mPath.lineTo(24.875F, 142.84399F);
            mPath.cubicTo(23.218F, 142.84399F, 21.875F, 141.50101F, 21.875F, 139.84399F);
            mPath.lineTo(20.914F, 58.176998F);
            mPath.cubicTo(9.746F, 55.66F, 1.196F, 46.226002F, 0.015F, 34.608002F);
            mPath.cubicTo(1.216F, 36.424999F, 2.654F, 38.069F, 4.274F, 39.512001F);
            mPath.lineTo(9.289F, 25.000999F);
            mPath.lineTo(1.104F, 11.523F);
            mPath.lineTo(16.868999F, 11.872F);
            mPath.lineTo(27.157F, 0.01F);
            mPath.lineTo(31.771999F, 15.066F);
            mPath.lineTo(45.869999F, 21.025999F);
            mPath.cubicTo(45.870998F, 20.965F, 45.875F, 20.905001F, 45.875F, 20.844F);
            mPath.cubicTo(45.875F, 17.264F, 45.109001F, 13.867F, 43.754002F, 10.789F);
            mPath.cubicTo(49.918999F, 15.737F, 53.875F, 23.323999F, 53.875F, 31.844F);
            mPath.cubicTo(53.875F, 44.706001F, 44.877998F, 55.460999F, 32.835999F, 58.175999F);
            mPath.close();
            mPath.moveTo(4.549F, 39.751999F);
            mPath.cubicTo(8.929F, 43.541F, 14.628F, 45.844002F, 20.875F, 45.844002F);
            mPath.cubicTo(24.055F, 45.844002F, 27.094F, 45.243999F, 29.892F, 44.160999F);
            mPath.lineTo(19.507F, 36.310001F);
            mPath.lineTo(4.549F, 39.751999F);
            mPath.close();
            mPath.moveTo(28.447001F, 18.069F);
            mPath.lineTo(25.447001F, 8.283F);
            mPath.lineTo(18.76F, 15.993F);
            mPath.lineTo(8.513F, 15.767F);
            mPath.lineTo(13.833F, 24.527F);
            mPath.lineTo(10.499F, 34.174F);
            mPath.lineTo(20.474001F, 31.878F);
            mPath.lineTo(28.660999F, 38.067001F);
            mPath.lineTo(29.506001F, 27.886999F);
            mPath.lineTo(37.898998F, 22.065001F);
            mPath.lineTo(28.447001F, 18.069F);
            mPath.close();
            mPath.moveTo(33.401001F, 30.169001F);
            mPath.lineTo(32.331001F, 43.063F);
            mPath.cubicTo(40.181999F, 39.006001F, 45.604F, 30.915001F, 45.858002F, 21.530001F);
            mPath.lineTo(33.401001F, 30.169001F);
            mPath.close();
        }
        return mPath;
    }
}

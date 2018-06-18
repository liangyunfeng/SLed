package com.lyf.app.sled.neon.path;

import android.graphics.Path;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class LovePath extends BasePath {

    public LovePath() {
        super(219, 94);
    }

    @Override
    public Path path() {
        if (mPath == null) {
            mPath = new Path();
            mPath.moveTo(217.073F, 74.310997F);
            mPath.cubicTo(216.64301F, 76.978996F, 215.37199F, 79.718002F, 213.259F, 82.528999F);
            mPath.cubicTo(211.146F, 85.339996F, 208.362F, 87.470001F, 204.907F, 88.919998F);
            mPath.cubicTo(201.451F, 90.371002F, 197.37801F, 91.096001F, 192.688F, 91.096001F);
            mPath.cubicTo(187.496F, 91.096001F, 182.91299F, 90.236F, 178.938F, 88.517998F);
            mPath.cubicTo(174.963F, 86.799004F, 171.991F, 84.560997F, 170.022F, 81.804001F);
            mPath.cubicTo(168.052F, 79.046997F, 166.888F, 76.137001F, 166.53101F, 73.075996F);
            mPath.cubicTo(166.172F, 70.014F, 165.994F, 63.577999F, 165.994F, 53.766998F);
            mPath.lineTo(165.994F, 2.312F);
            mPath.lineTo(188.606F, 2.312F);
            mPath.lineTo(188.606F, 67.516998F);
            mPath.cubicTo(188.606F, 71.311996F, 188.812F, 73.738998F, 189.224F, 74.793999F);
            mPath.cubicTo(189.63499F, 75.850998F, 190.467F, 76.378998F, 191.72099F, 76.378998F);
            mPath.cubicTo(193.153F, 76.378998F, 194.075F, 75.796997F, 194.487F, 74.633003F);
            mPath.cubicTo(194.899F, 73.470001F, 195.105F, 70.722F, 195.105F, 66.389F);
            mPath.lineTo(195.105F, 2.312F);
            mPath.lineTo(217.717F, 2.312F);
            mPath.lineTo(217.717F, 60.426998F);
            mPath.cubicTo(217.717F, 67.015999F, 217.502F, 71.643997F, 217.073F, 74.310997F);
            mPath.close();
            mPath.moveTo(95.617996F, 92.990997F);
            mPath.cubicTo(94.271004F, 88.400002F, 85.671997F, 77.614998F, 73.588997F, 69.731003F);
            mPath.cubicTo(61.129002F, 61.602001F, 42.414001F, 54.0F, 42.556F, 33.665001F);
            mPath.cubicTo(42.813F, -3.361F, 81.816002F, -10.804F, 95.538002F, 21.097F);
            mPath.cubicTo(108.628F, -9.532F, 148.31F, -3.674F, 148.56799F, 33.444F);
            mPath.cubicTo(148.71001F, 53.778999F, 129.76401F, 60.881001F, 117.535F, 69.510002F);
            mPath.cubicTo(106.35F, 77.403F, 96.772003F, 88.492996F, 95.617996F, 92.990997F);
            mPath.close();
            mPath.moveTo(0.992F, 3.386F);
            mPath.lineTo(23.604F, 3.386F);
            mPath.lineTo(23.604F, 90.344002F);
            mPath.lineTo(0.992F, 90.344002F);
            mPath.lineTo(0.992F, 3.386F);
            mPath.close();
        }
        return mPath;
    }
}

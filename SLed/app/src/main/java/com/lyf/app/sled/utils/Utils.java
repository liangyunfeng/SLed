package com.lyf.app.sled.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by yunfeng.l on 2018/1/29.
 */

public class Utils {

    /**
     * dip转换为px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static int getScreenWidth(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            return point.x;
        }
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= 17) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            return point.y;
        }
        return activity.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 设置全屏
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity, boolean isFull) {
        if (isFull) {
            activity.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            activity.getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}

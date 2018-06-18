package com.lyf.app.sled.utils;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by yunfeng.l on 2018/1/26.
 */

public class WakeLock {

    private static PowerManager.WakeLock sWakeLock;

    public static synchronized void acquire(Context context) {
        if (sWakeLock != null) {
            sWakeLock.release();
        }

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        sWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "==KeepScreenOn==");
        sWakeLock.acquire();
    }

    public static synchronized void release() {
        if (sWakeLock != null) {
            sWakeLock.release();
            sWakeLock = null;
        }
    }
}
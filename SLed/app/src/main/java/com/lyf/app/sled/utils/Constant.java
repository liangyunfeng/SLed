package com.lyf.app.sled.utils;

import android.graphics.Color;

/**
 * Created by yunfeng.l on 2018/2/11.
 */

public class Constant {
    public final static boolean DEBUG = false;

    public static class Color {
        public static int[] ColorIds = new int[]{
                android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.RED,
                android.graphics.Color.WHITE, android.graphics.Color.YELLOW, android.graphics.Color.MAGENTA
        };

        public static int[] LedColorIds = new int[]{
                android.graphics.Color.BLUE, android.graphics.Color.GREEN, android.graphics.Color.RED, android.graphics.Color.YELLOW, android.graphics.Color.WHITE
        };

        public static int[] LedRainBowColorIds = new int[]{
                android.graphics.Color.BLUE,
                android.graphics.Color.GREEN,
                android.graphics.Color.RED,
                android.graphics.Color.DKGRAY,
                android.graphics.Color.CYAN,
                android.graphics.Color.GRAY,
        };
    }
}

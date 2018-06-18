package com.lyf.app.sled.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yunfeng.l on 2018/1/26.
 */

public class Prefs {

    public static class Key {
        public final static String HAND_WRITING_BACKGROUND_COLOR = "HandWritingBackgroundColor";
        public final static String HAND_WRITING_PAINT_COLOR = "HandWritingPaintColor";
        public final static String HAND_WRITING_PAINT_SIZE = "HandWritingPaintSize";
        public final static String HAND_WRITING_FLASH_ONOFF = "HandWritingFlashOnOff";
        public final static String HAND_WRITING_FLASH_FREQUENCY_LEVEL = "HandWritingFlashFrequencyLevel";
        public final static String HAND_WRITING_FLASH_COLOR_ARRAY = "HandWritingFlashColorArray";

        public final static String GLOW_STICK_BACKGROUND_COLOR = "GlowStickBackgroundColor";
        public final static String GLOW_STICK_TEXT_COLOR_INDEX = "GlowStickTextColorIndex";
        public final static String GLOW_STICK_FLASH_SWITCH = "GlowStickFlashSwitch";
        public final static String GLOW_STICK_FLASH_COLOR1 = "GlowStickFlashColor1";
        public final static String GLOW_STICK_FLASH_COLOR2 = "GlowStickFlashColor2";
        public final static String GLOW_STICK_FLASH_COLOR3 = "GlowStickFlashColor3";
        public final static String GLOW_STICK_FLASH_COLOR4 = "GlowStickFlashColor4";
        public final static String GLOW_STICK_FLASH_COLOR5 = "GlowStickFlashColor5";
        public final static String GLOW_STICK_FLASH_SPEED = "GlowStickFlashSpeed";
        public final static String GLOW_STICK_NEON_PATH = "GlowStickNeonPath";
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return get(context).getBoolean(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        get(context).edit().putBoolean(key, value).apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return get(context).getInt(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        get(context).edit().putInt(key, value).apply();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        return get(context).getLong(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value) {
        get(context).edit().putLong(key, value).apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return get(context).getString(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        get(context).edit().putString(key, value).apply();
    }

    private static SharedPreferences get(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}

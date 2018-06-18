package com.lyf.app.sled.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ListView;

import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.Preference;

import com.lyf.app.sled.R;
import com.lyf.app.sled.utils.Constant;

import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LedSettingsActivity extends Activity {
    private final static String TAG = "LedSettingsActivity";

    View ledBackground, ledTextColor;
    public Context context;
    static String mTextContent;
    public static com.lyf.app.sled.widget.MarqueeSurfaceView marqueeView;
    public static EditTextPreference editTextPreference;
    public static ListPreference scrollSpeedPreference, sharkSpeedPreference;
    private com.lyf.app.sled.widget.ColorPickerDialog dialog;
    static boolean stopShark = false;

    static int message_shark = 0;
    static int message_stopShark = 1;
    static int message_setTextColor = 2;
    static int message_setBgColor = 3;
    public static Handler myHandler;

    private int mTextColor;
    private int mBackgroundColor;
    static int mDirection = 0;
    static int mSpeed = 4;
    static boolean mIsRainbowColor = false;
    static boolean mIsLedEffect = false;

    private SharedPreferences pref;

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_settings);

        getFragmentManager().beginTransaction().add(R.id.pref_container, new PrefsFragement()).commit();

        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == message_shark) {
                    color_cnt = color_cnt % Constant.Color.LedColorIds.length;
                    marqueeView.setTextColor(Constant.Color.LedColorIds[color_cnt]);
                    color_cnt++;

                    if (stopShark) {
                        myHandler.sendEmptyMessage(message_stopShark);
                    } else {
                        myHandler.sendEmptyMessageDelayed(message_shark, 1000);
                    }
                } else if (msg.what == message_stopShark) {
                    marqueeView.setTextColor(mTextColor);
                } else if (msg.what == message_setTextColor) {
                    if (ledTextColor != null) {
                        ledTextColor.setBackgroundColor(mTextColor);
                    }
                } else if (msg.what == message_setBgColor) {
                    if (ledBackground != null) {
                        ledBackground.setBackgroundColor(mBackgroundColor);
                    }
                }
            }
        };
    }

    public void showTextDialog(View v) {
        mTextColor = pref.getInt("textColor", getResources().getColor(R.color.colorMarqueeText));
        if (mIsRainbowColor) {
            Toast.makeText(getApplicationContext(), getString(R.string.text_color_not_allow), Toast.LENGTH_SHORT).show();
        } else {
            dialog = new com.lyf.app.sled.widget.ColorPickerDialog(context, mTextColor,
                    getResources().getString(R.string.led_string_color),
                    new com.lyf.app.sled.widget.ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int color) {
                            mTextColor = color;
                            myHandler.sendEmptyMessage(message_setTextColor);
                            PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("textColor", color);
                            PreferenceManager.getDefaultSharedPreferences(context).edit().commit();
                            marqueeView.setTextColor(color);
                            SharedPreferences preff = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preff.edit();
                            editor.putInt("textColor", color);
                            editor.apply();
                        }
                    });
            dialog.show();
        }
    }

    public int color_cnt = 0;

    public void shark() {
        timer = new Timer();
        TimerTask taskc = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //color_cnt=color_cnt%colors.length;
                        //marqueeView.setTextAlpha(0);
                        //marqueeView.setTextColor(colors[color_cnt]);
                        // color_cnt++;
                    }
                });
            }
        };
        timer.schedule(taskc, 1, 500);
    }

    public void showDialog(View v) {
        mBackgroundColor = pref.getInt("string_background", getResources().getColor(R.color.colorSurfaceBackgroud));
        if (mIsLedEffect) {
            Toast.makeText(getApplicationContext(), getString(R.string.bg_color_not_allow), Toast.LENGTH_SHORT).show();
        } else {
            dialog = new com.lyf.app.sled.widget.ColorPickerDialog(context, mBackgroundColor,
                    getResources().getString(R.string.led_string_background),
                    new com.lyf.app.sled.widget.ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int color) {
                            mBackgroundColor = color;
                            myHandler.sendEmptyMessage(message_setBgColor);
                            marqueeView.setBackGroundColor(color);
                            SharedPreferences preff = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preff.edit();
                            editor.putInt("string_background", color);
                            editor.apply();
                        }
                    });
            dialog.show();
        }
    }

    public void jumpToFullScreen(View v) {
        Intent intent = new Intent(LedSettingsActivity.this, FullScreenActivity.class);
        boolean directionB = pref.getBoolean("scroll_direction", false);
        if (directionB)
            mDirection = 1;
        else
            mDirection = 0;
        mIsRainbowColor = pref.getBoolean("rainbow_effect", false);
        mIsLedEffect = pref.getBoolean("led_effect", false);
        mTextContent = pref.getString("marqueeTextPreference", getResources().getString(R.string.edit_text_hint));
        stopShark = !(pref.getBoolean("shark", false));
        intent.putExtra("textContent", mTextContent);
        intent.putExtra("backGroundColor", mBackgroundColor);
        intent.putExtra("textColor", mTextColor);
        intent.putExtra("direction", mDirection);
        intent.putExtra("ledMode", mIsLedEffect);
        intent.putExtra("rainbow", mIsRainbowColor);
        intent.putExtra("speed", mSpeed);
        intent.putExtra("isStopShark", stopShark);
        if (Constant.DEBUG)
            Log.d(TAG, "jump" + mTextColor + mDirection + mSpeed + mIsRainbowColor + mBackgroundColor + mTextContent);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        marqueeView = (com.lyf.app.sled.widget.MarqueeSurfaceView) findViewById(R.id.marquee_surface);
        ledBackground = (View) findViewById(R.id.led_background);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ledTextColor = (View) findViewById(R.id.string_background);
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        mTextColor = getResources().getColor(R.color.colorMarqueeText);
        mBackgroundColor = getResources().getColor(R.color.colorSurfaceBackgroud);
        Log.v("lyf","onResume 1");
        if (pref != null) {
            mTextColor = pref.getInt("textColor", getResources().getColor(R.color.colorMarqueeText));

            mBackgroundColor = pref.getInt("string_background", getResources().getColor(R.color.colorSurfaceBackgroud));
            boolean directionB = pref.getBoolean("scroll_direction", false);
            if (directionB)
                mDirection = 1;
            else
                mDirection = 0;
            mIsRainbowColor = pref.getBoolean("rainbow_effect", false);
            mIsLedEffect = pref.getBoolean("led_effect", false);
            mTextContent = pref.getString("marqueeTextPreference", getResources().getString(R.string.edit_text_hint));
            stopShark = !(pref.getBoolean("shark", false));
            mSpeed = Integer.parseInt(pref.getString("scrollSpeed", "4"));
            Log.v("lyf","onResume 2 : mSpeed = " + mSpeed);
        }

        ledTextColor.setBackgroundColor(mTextColor);
        ledBackground.setBackgroundColor(mBackgroundColor);

        Log.v("lyf","onResume 3 : mSpeed = " + mSpeed);
        marqueeView.initMarquee(mTextColor, getResources().getDimensionPixelSize(R.dimen.led_text_size), mDirection, mSpeed, mIsRainbowColor, mBackgroundColor, mIsLedEffect);
        marqueeView.setText(mTextContent);
        marqueeView.setFocusable(true);
        marqueeView.requestFocus();
        marqueeView.startScroll();
        marqueeView.setShadowMode(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (stopShark) {
            myHandler.sendEmptyMessage(message_stopShark);
        } else {
            myHandler.sendEmptyMessage(message_shark);
        }
    }

    public static class PrefsFragement extends PreferenceFragment {
        private Callbacks mCallBack;
        private SwitchPreference mSwitchSharkPreference;
        private SwitchPreference mSwitchRainbowPreference;

        public interface Callbacks {
            public void onItemSelected(Integer id);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            if (Constant.DEBUG)
                Log.d(TAG, "preference");
            addPreferencesFromResource(R.xml.marquee_setting_preference);
            editTextPreference = (EditTextPreference) findPreference("marqueeTextPreference");
            editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    editTextPreference.setText((String) newValue);
                    marqueeView.setText(editTextPreference.getText());
                    mTextContent = editTextPreference.getText();
                    return true;
                }
            });
            scrollSpeedPreference = (ListPreference) findPreference("scrollSpeed");
            scrollSpeedPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    scrollSpeedPreference.setValue((String) newValue);
                    marqueeView.setSpeed(Integer.parseInt((String) newValue));
                    mSpeed = Integer.parseInt((String) newValue);
                    return true;
                }
            });
            mSwitchSharkPreference = (SwitchPreference) findPreference("shark");
            mSwitchSharkPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (Constant.DEBUG)
                        Log.v(TAG, "SwitchSharkPreferenc.onPreferenceChange : isChecked = " + newValue + ", stopShark = " + stopShark + ", mIsRainbowColor = " + mIsRainbowColor);
                    mSwitchSharkPreference.setChecked((Boolean) newValue);
                    stopShark = !mSwitchSharkPreference.isChecked();
                    if (mSwitchSharkPreference.isChecked()) {
                        myHandler.sendEmptyMessage(message_shark);
                        boolean isRBChecked = mSwitchRainbowPreference.isChecked();
                        if (isRBChecked) {
                            mSwitchRainbowPreference.setChecked(!isRBChecked);
                            mSwitchRainbowPreference.setSummary(R.string.off);
                            marqueeView.setIsRainbowColor(!isRBChecked);
                            mIsRainbowColor = !isRBChecked;
                        }
                    } else {
                        myHandler.sendEmptyMessage(message_stopShark);
                    }
                    return true;
                }
            });

            mSwitchRainbowPreference = (SwitchPreference) findPreference("rainbow_effect");
            mSwitchRainbowPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (Constant.DEBUG)
                        Log.v(TAG, "SwitchRainbowPreference.onPreferenceChange : isChecked = " + newValue + ", stopShark = " + stopShark + ", mIsRainbowColor = " + mIsRainbowColor);
                    mSwitchRainbowPreference.setChecked((Boolean) newValue);
                    mIsRainbowColor = mSwitchRainbowPreference.isChecked();
                    if (mSwitchRainbowPreference.isChecked()) {
                        boolean isSKChecked = mSwitchSharkPreference.isChecked();
                        if (isSKChecked) {
                            mSwitchSharkPreference.setChecked(!isSKChecked);
                            mSwitchSharkPreference.setSummary(R.string.off);
                            stopShark = true;
                            myHandler.sendEmptyMessage(message_stopShark);
                        }
                    }
                    return true;
                }
            });
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            ListView lv = (ListView) view.findViewById(android.R.id.list);
            lv.setDivider(null);
            return view;
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                             Preference preference) {
            if ("led_effect".equals(preference.getKey())) {
                SwitchPreference switchPreference = (SwitchPreference) findPreference("led_effect");
                mIsLedEffect = switchPreference.isChecked();
                if (switchPreference.isChecked())
                    switchPreference.setSummary(R.string.on);
                else
                    switchPreference.setSummary(R.string.off);
                marqueeView.setIsLedEffect(switchPreference.isChecked());
            } else if ("rainbow_effect".equals(preference.getKey())) {
                SwitchPreference switchPreference = (SwitchPreference) findPreference("rainbow_effect");
                mIsRainbowColor = switchPreference.isChecked();
                if (Constant.DEBUG)
                    Log.v(TAG, "onPreferenceTreeClick-rainbow_effect : isChecked = " + mIsRainbowColor + ", stopShark = " + stopShark + ", mIsRainbowColor = " + mIsRainbowColor);
                if (switchPreference.isChecked())
                    switchPreference.setSummary(R.string.on);
                else
                    switchPreference.setSummary(R.string.off);
                marqueeView.setIsRainbowColor(switchPreference.isChecked());
            } else if ("marqueeTextPreference".equals(preference.getKey())) {
                // marqueeView.setText(editTextPreference.getText());
                if (Constant.DEBUG)
                    Log.d(TAG, "getText" + editTextPreference.getText());
            } else if ("scroll_direction".equals(preference.getKey())) {
                SwitchPreference switchPreference = (SwitchPreference) findPreference("scroll_direction");
                if (switchPreference.isChecked()) {
                    mDirection = 1;
                    marqueeView.setDirection(1);
                    switchPreference.setSummary(R.string.scroll_direction_right);
                } else {
                    mDirection = 0;
                    marqueeView.setDirection(0);
                    switchPreference.setSummary(R.string.scroll_direction_left);
                }
            } else if ("scrollSpeed".equals(preference.getKey())) {
                ListPreference listPreference = (ListPreference) findPreference("scrollSpeed");
            } else if ("shark".equals(preference.getKey())) {
                SwitchPreference switchPreference = (SwitchPreference) findPreference("shark");
                if (Constant.DEBUG)
                    Log.v(TAG, "onPreferenceTreeClick-shark : isChecked = " + switchPreference.isChecked() + ", stopShark = " + stopShark + ", mIsRainbowColor = " + mIsRainbowColor);
                if (switchPreference.isChecked())
                    switchPreference.setSummary(R.string.on);
                else
                    switchPreference.setSummary(R.string.off);
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }
}
package com.lyf.app.sled.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lyf.app.sled.R;
import com.lyf.app.sled.neon.NeonColor;
import com.lyf.app.sled.utils.Constant;
import com.lyf.app.sled.utils.Prefs;
import com.lyf.app.sled.utils.WakeLock;
import com.lyf.app.sled.widget.ColorPickerDialog;
import com.lyf.app.sled.widget.HandWritingView;
import com.lyf.app.sled.widget.action.ActionManager;

/**
 * Created by yunfeng.l on 2018/1/25.
 */

public class HandWritingActivity extends Activity implements OnClickListener {
    private final static String TAG = "HandWritingActivity";
    private final static int MSG_UPDATE_UNDO_REDO = 1000;
    private final static int MSG_UPDATE_FOREGROUND_BUTTON = 1001;

    private Context mContext;
    private HandWritingView mHandWriting;
    private FrameLayout mPreview;
    private LinearLayout mBackGroundSetting;
    private LinearLayout mPaintSizeSetting;
    private LinearLayout mPaintColorSetting;
    private LinearLayout mFlashSetting;
    private ImageButton mBtnUndo;
    private ImageButton mBtnRedo;
    private boolean canUndo = false;
    private boolean canRedo = false;

    private FrameLayout mTabLayout;
    private LinearLayout mUndoRedoLayout;

    private ColorPickerDialog mColorPickDialog;
    private AlertDialog mDialog;
    private String mPaintArray[] = {"5", "10", "20", "30", "40"};
    private int mPaintInteger[] = {5, 10, 20, 30, 40};
    private int mSelectedSize = 1;
    private String mFlashArray[] = new String[2];
    private boolean canFlash = true;

    private boolean isFlashing = false;

    ActionManager.ForwardBackCallback mForwardBackCallback = new ActionManager.ForwardBackCallback() {
        @Override
        public void onForwardBackChanged(boolean canBack, boolean canForward) {
            if (Constant.DEBUG)
                Log.v(TAG, "onForwardBackChanged");
            canUndo = canBack;
            canRedo = canForward;
            mHandler.sendEmptyMessage(MSG_UPDATE_UNDO_REDO);
        }
    };

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE_UNDO_REDO:
                    if (canUndo) {
                        mBtnUndo.setImageResource(R.drawable.ic_undo);
                        mBtnUndo.setEnabled(true);
                    } else {
                        mBtnUndo.setImageResource(R.drawable.ic_undo_gray);
                        mBtnUndo.setEnabled(false);
                    }
                    if (canRedo) {
                        mBtnRedo.setImageResource(R.drawable.ic_redo);
                        mBtnRedo.setEnabled(true);
                    } else {
                        mBtnRedo.setImageResource(R.drawable.ic_redo_gray);
                        mBtnRedo.setEnabled(false);
                    }
                    break;
                case MSG_UPDATE_FOREGROUND_BUTTON:
                    if (isFlashing) {
                        mTabLayout.setVisibility(View.GONE);
                        mUndoRedoLayout.setVisibility(View.GONE);
                        mPreview.setVisibility(View.GONE);
                    } else {
                        mTabLayout.setVisibility(View.VISIBLE);
                        mUndoRedoLayout.setVisibility(View.VISIBLE);
                        mPreview.setVisibility(View.VISIBLE);
                    }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;
        setContentView(R.layout.activity_hand_writing);

        mHandWriting = (HandWritingView) findViewById(R.id.hand_surfaceview);
        mTabLayout = (FrameLayout) findViewById(R.id.tab_layout);
        mUndoRedoLayout = (LinearLayout) findViewById(R.id.layout_undo_redo);
        mPreview = (FrameLayout) findViewById(R.id.hand_writing_preview);
        mBackGroundSetting = (LinearLayout) findViewById(R.id.hand_writing_background_container);
        mPaintSizeSetting = (LinearLayout) findViewById(R.id.hand_writing_paint_size_container);
        mPaintColorSetting = (LinearLayout) findViewById(R.id.hand_writing_paint_color_container);
        mFlashSetting = (LinearLayout) findViewById(R.id.hand_writing_flash_container);
        mBtnUndo = (ImageButton) findViewById(R.id.btn_undo);
        mBtnRedo = (ImageButton) findViewById(R.id.btn_redo);
        mPreview.setOnClickListener(this);
        mBackGroundSetting.setOnClickListener(this);
        mPaintSizeSetting.setOnClickListener(this);
        mPaintColorSetting.setOnClickListener(this);
        mFlashSetting.setOnClickListener(this);
        mBtnUndo.setOnClickListener(this);
        mBtnRedo.setOnClickListener(this);
        mHandWriting.setForwardBackCallback(mForwardBackCallback);
        /*int backgroundColor = Prefs.getInt(mContext, Prefs.Key.HAND_WRITING_BACKGROUND_COLOR,
                getResources().getColor(R.color.hand_writing_background_default_color));
        int paintColor = Prefs.getInt(mContext, Prefs.Key.HAND_WRITING_PAINT_COLOR, NeonColor.get()[0][4]);
        mHandWriting.setBackgroundColor(backgroundColor);
        mHandWriting.setPaintColor(paintColor);*/
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isFlashing) {
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mHandWriting.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hand_writing_preview:
                if (Constant.DEBUG)
                    Log.v(TAG, "startShaking, isFlashing = " + isFlashing);
                WakeLock.acquire(mContext);
                mHandWriting.startShaking();
                isFlashing = true;
                mHandler.sendEmptyMessage(MSG_UPDATE_FOREGROUND_BUTTON);
                break;
            case R.id.hand_writing_background_container:
                showBGColorDialog();
                break;
            case R.id.hand_writing_paint_size_container:
                showPaintSizeDialog();
                break;
            case R.id.hand_writing_paint_color_container:
                showPaintColorDialog();
                break;
            case R.id.hand_writing_flash_container:
                showFlashDialog();
                break;
            case R.id.btn_undo:
                if (Constant.DEBUG)
                    Log.v(TAG, "undo");
                mHandWriting.undo();
                break;
            case R.id.btn_redo:
                if (Constant.DEBUG)
                    Log.v(TAG, "redo");
                mHandWriting.redo();
                break;
            default:
                break;
        }
    }

    public void showBGColorDialog() {
         //int backgroundColor = Prefs.getInt(mContext, Prefs.Key.HAND_WRITING_BACKGROUND_COLOR,
         //       getResources().getColor(R.color.hand_writing_background_default_color));
        int backgroundColor = getResources().getColor(R.color.hand_writing_background_default_color);
        if (mHandWriting != null) {
            backgroundColor = mHandWriting.getBGColor();
        }
        mColorPickDialog = new ColorPickerDialog(mContext, backgroundColor,
                getResources().getString(R.string.str_hw_background_color),
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        if (mHandWriting != null) {
                            mHandWriting.setBGColor(color);
                            Prefs.putInt(mContext, Prefs.Key.HAND_WRITING_BACKGROUND_COLOR, color);
                        }
                    }
                });
        mColorPickDialog.show();
    }

    public void showPaintColorDialog() {
        //int paintColor = Prefs.getInt(mContext, Prefs.Key.HAND_WRITING_PAINT_COLOR, NeonColor.get()[0][4]);
        int paintColor = NeonColor.get()[0][4];
        if (mHandWriting != null) {
            paintColor = mHandWriting.getPaintColor();
        }
        mColorPickDialog = new ColorPickerDialog(mContext, paintColor,
                getResources().getString(R.string.str_hw_paint_color),
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        if (mHandWriting != null) {
                            mHandWriting.setPaintColor(color);
                            Prefs.putInt(mContext, Prefs.Key.HAND_WRITING_PAINT_COLOR, color);
                        }
                    }
                });
        mColorPickDialog.show();
    }

    public void showPaintSizeDialog() {
        mDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.str_hw_paint_size)).setIcon(R.drawable.paint_size)
                .setSingleChoiceItems(mPaintArray, mSelectedSize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHandWriting.setPaintSize(mPaintInteger[which]);
                        mSelectedSize = which;
                        Prefs.putInt(mContext, Prefs.Key.HAND_WRITING_PAINT_SIZE, mPaintInteger[which]);
                        dialog.dismiss();
                    }
                }).create();
        mDialog.show();
    }

    public void showFlashDialog() {
        mFlashArray[0] = getResources().getString(R.string.str_hw_on);
        mFlashArray[1] = getResources().getString(R.string.str_hw_off);
        mDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.hand_writing_flash)).setIcon(R.drawable.flash)
                .setSingleChoiceItems(mFlashArray, canFlash ? 0 : 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        canFlash = (which == 0 ? true : false);
                        mHandWriting.setFlash(canFlash);
                        Prefs.putBoolean(mContext, Prefs.Key.HAND_WRITING_FLASH_ONOFF, canFlash);
                        dialog.dismiss();
                    }
                }).create();
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandWriting != null) {
            mHandWriting.stopShaking(true);
        }
        WakeLock.release();
    }

    @Override
    public void onBackPressed() {
        if (!isFlashing) {
            super.onBackPressed();
        } else {
            mHandWriting.stopShaking(false);
            WakeLock.release();
            isFlashing = false;
            mHandler.sendEmptyMessage(MSG_UPDATE_FOREGROUND_BUTTON);
        }
    }
}

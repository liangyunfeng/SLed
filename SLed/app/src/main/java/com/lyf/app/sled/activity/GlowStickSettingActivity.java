package com.lyf.app.sled.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import com.lyf.app.sled.R;
import com.lyf.app.sled.adpter.DotColorPickRecyclerViewAdapter;
import com.lyf.app.sled.adpter.HorizontalRecyclerViewAdapter;
import com.lyf.app.sled.adpter.IDotColorPickCallback;
import com.lyf.app.sled.adpter.OnItemClickListener;
import com.lyf.app.sled.neon.NeonColor;
import com.lyf.app.sled.neon.NeonStyle;
import com.lyf.app.sled.utils.Prefs;
import com.lyf.app.sled.widget.ColorPickerDialog;
import com.lyf.app.sled.widget.NeonStickView;

/**
 * Created by yunfeng.l on 2018/1/29.
 */
public class GlowStickSettingActivity extends Activity implements OnClickListener {
    private final static String TAG = "GlowStickSettingActivity";

    private Context mContext;

    NeonStickView mStickPreviewView;
    ImageView mBtnFullScreen;
    SeekBar mBarSpeed;
    Switch mSwitch;
    RecyclerView mNeonPathListView;
    HorizontalRecyclerViewAdapter mAdapter;
    NeonStyle mNeonStyle;
    View mBtnBGColor;
    View mBtnTextColor;
    View mBtnFlashColor[] = new View[5];
    ColorPickerDialog mColorPickDialog;
    Dialog mDialog;
    RecyclerView mDotColorPickRecyclerView;
    DotColorPickRecyclerViewAdapter mDotColorPickAdapter;
    int selectedColor = 0;
    int mTextColorIndex = 0;
    int mFlashColorIndex[] = new int[5];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        setContentView(R.layout.activity_glow_stick_setting);

        mNeonStyle = NeonStyle.getInstance();
        mStickPreviewView = (NeonStickView) findViewById(R.id.stick_preview_view);
        mStickPreviewView.setDrawMargin(100);
        mStickPreviewView.setNeonStyle(mNeonStyle);
        mBtnFullScreen = (ImageView) findViewById(R.id.btn_fullscreen);
        mBarSpeed = (SeekBar) findViewById(R.id.bar_speed);
        mSwitch = (Switch) findViewById(R.id.btn_flash);
        mNeonPathListView = (RecyclerView) findViewById(R.id.rv_neonpath);
        mBtnBGColor = findViewById(R.id.btn_background);
        mBtnTextColor = findViewById(R.id.btn_text);
        mBtnFlashColor[0] = findViewById(R.id.btn_flash_color_1);
        mBtnFlashColor[1] = findViewById(R.id.btn_flash_color_2);
        mBtnFlashColor[2] = findViewById(R.id.btn_flash_color_3);
        mBtnFlashColor[3] = findViewById(R.id.btn_flash_color_4);
        mBtnFlashColor[4] = findViewById(R.id.btn_flash_color_5);
        mBtnBGColor.setOnClickListener(this);
        mBtnTextColor.setOnClickListener(this);
        for (int i = 0; i < mBtnFlashColor.length; i++) {
            mBtnFlashColor[i].setOnClickListener(this);
        }
        mBtnFullScreen.setOnClickListener(this);
        mBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mStickPreviewView != null) {
                    mStickPreviewView.setSpeed(i);
                    if (mNeonStyle != null) {
                        mNeonStyle.setSpeed(i);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mStickPreviewView != null) {
                    mStickPreviewView.setFlash(isChecked);
                    if (mNeonStyle != null) {
                        mNeonStyle.setFlash(isChecked);
                    }
                }
            }
        });

        mNeonPathListView = (RecyclerView) findViewById(R.id.rv_neonpath);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mNeonPathListView.setLayoutManager(linearLayoutManager);
        mAdapter = new HorizontalRecyclerViewAdapter(mContext, mNeonStyle.getNeonPathList());
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (mStickPreviewView != null && mNeonStyle != null && mNeonStyle.getNeonPathList() != null) {
                    mStickPreviewView.setNeonPath(mNeonStyle.getNeonPathList().get(position));
                    if (mNeonStyle != null) {
                        mNeonStyle.setNeonPathIndex(position);
                    }
                }
            }

            @Override
            public void onLongClick(int position) {
                // todo
            }
        });
        mNeonPathListView.setAdapter(mAdapter);

        initViewFromNeonStyle();
    }

    public void initViewFromNeonStyle() {
        if (mNeonStyle != null) {
            mBarSpeed.setProgress(mNeonStyle.getSpeed());
            mSwitch.setChecked(mNeonStyle.isFlash());
            mBtnBGColor.setBackgroundColor(mNeonStyle.getBackgroundColor());
            mBtnTextColor.setBackgroundColor(NeonColor.get()[mNeonStyle.getTextColorIndex()][4]);
            for (int i = 0; i < mFlashColorIndex.length; i++) {
                mFlashColorIndex[i] = mNeonStyle.getFlashColorIndex(i);
                mBtnFlashColor[i].setBackgroundColor(NeonColor.get()[mFlashColorIndex[i]][0]);
            }
        }
    }

    public void resetNeonStyle() {
        if (mNeonStyle != null) {
            mNeonStyle.reset();
            initViewFromNeonStyle();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mStickPreviewView != null) {
            mStickPreviewView.startPreview();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mStickPreviewView != null) {
            mStickPreviewView.stopPreview();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen:
                Intent intent = new Intent(GlowStickSettingActivity.this, GlowStickActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_background:
                showBGColorDialog();
                break;
            case R.id.btn_text:
                showColorPickDialog(mTextColorIndex, new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mTextColorIndex = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setPatinColor(color);
                            mStickPreviewView.setPatinFillColor(NeonColor.get()[position][4]);
                            mBtnTextColor.setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setTextColorIndex(mTextColorIndex);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_TEXT_COLOR_INDEX, mTextColorIndex);
                    }
                });
                break;
            case R.id.btn_flash_color_1:
                showColorPickDialog(mFlashColorIndex[0], new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mFlashColorIndex[0] = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setFlashColor(0, color);
                            mBtnFlashColor[0].setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setFlashColorIndex(0, position);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_FLASH_COLOR1, mFlashColorIndex[0]);
                    }
                });
                break;
            case R.id.btn_flash_color_2:
                showColorPickDialog(mFlashColorIndex[1], new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mFlashColorIndex[1] = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setFlashColor(1, color);
                            mBtnFlashColor[1].setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setFlashColorIndex(1, position);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_FLASH_COLOR1, mFlashColorIndex[1]);
                    }
                });
                break;
            case R.id.btn_flash_color_3:
                showColorPickDialog(mFlashColorIndex[2], new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mFlashColorIndex[2] = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setFlashColor(2, color);
                            mBtnFlashColor[2].setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setFlashColorIndex(2, position);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_FLASH_COLOR2, mFlashColorIndex[2]);
                    }
                });
                break;
            case R.id.btn_flash_color_4:
                showColorPickDialog(mFlashColorIndex[3], new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mFlashColorIndex[3] = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setFlashColor(3, color);
                            mBtnFlashColor[3].setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setFlashColorIndex(3, position);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_FLASH_COLOR3, mFlashColorIndex[3]);
                    }
                });
                break;
            case R.id.btn_flash_color_5:
                showColorPickDialog(mFlashColorIndex[4], new IDotColorPickCallback() {
                    @Override
                    public void onSelected(int position) {
                        mFlashColorIndex[4] = position;
                        int color = NeonColor.get()[position][0];
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setFlashColor(4, color);
                            mBtnFlashColor[4].setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setFlashColorIndex(4, position);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_FLASH_COLOR4, mFlashColorIndex[4]);
                    }
                });
                break;
            default:
                break;
        }
    }

    public void showBGColorDialog() {
        int backgroundColor = Prefs.getInt(mContext, Prefs.Key.GLOW_STICK_BACKGROUND_COLOR, Color.BLACK);
        mColorPickDialog = new ColorPickerDialog(mContext, backgroundColor,
                getResources().getString(R.string.str_stick_background_color),
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        if (mStickPreviewView != null) {
                            mStickPreviewView.setBGColor(color);
                            mBtnBGColor.setBackgroundColor(color);
                            if (mNeonStyle != null) {
                                mNeonStyle.setBackgroundColor(color);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_BACKGROUND_COLOR, color);
                    }
                });
        mColorPickDialog.show();
    }

    public void showColorPickDialog(int selected, final IDotColorPickCallback callback) {
        mDotColorPickRecyclerView = (RecyclerView) LayoutInflater.from(mContext).inflate(R.layout.dot_color_view, null, false);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 8, GridLayoutManager.VERTICAL, false);
        mDotColorPickRecyclerView.setLayoutManager(layoutManager);
        mDotColorPickAdapter = new DotColorPickRecyclerViewAdapter(mContext, NeonColor.get());
        mDotColorPickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                selectedColor = position;
            }

            @Override
            public void onLongClick(int position) {
                selectedColor = position;
            }
        });
        mDotColorPickAdapter.setSelectedColor(selected);
        mDotColorPickRecyclerView.setAdapter(mDotColorPickAdapter);

        selectedColor = -1;

        mDialog = new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.str_stick_color_pick_dialog_title))
                .setView(mDotColorPickRecyclerView)
                .setPositiveButton(R.string.str_stick_color_pick_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        if (selectedColor != -1) {
                            if (callback != null) {
                                callback.onSelected(selectedColor);
                            }
                            mDialog.dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.str_stick_color_pick_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        selectedColor = -1;
                        mDialog.dismiss();
                    }
                })
                .create();
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
        if (mDotColorPickRecyclerView != null) {
            mDotColorPickRecyclerView = null;
        }
        if (mDotColorPickAdapter != null) {
            mDotColorPickAdapter = null;
        }
        if (mNeonPathListView != null) {
            mNeonPathListView = null;
        }
        if (mAdapter != null) {
            mAdapter = null;
        }

    }
}

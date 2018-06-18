package com.lyf.app.sled.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;

import com.lyf.app.sled.R;
import com.lyf.app.sled.adpter.HorizontalRecyclerViewAdapter;
import com.lyf.app.sled.adpter.OnItemClickListener;
import com.lyf.app.sled.fragment.callback.IFragmentCallback;
import com.lyf.app.sled.neon.NeonStyle;
import com.lyf.app.sled.utils.Constant;
import com.lyf.app.sled.utils.Prefs;
import com.lyf.app.sled.widget.ColorPickerDialog;
import com.lyf.app.sled.widget.NeonStickView;

/**
 * Created by yunfeng.l on 2018/1/31.
 */

public class GlowStickSettingFragment extends Fragment implements View.OnClickListener {
    private final static String TAG = "GlowStickSettingFragment";

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
    View mBtnFlashColor1, mBtnFlashColor2, mBtnFlashColor3, mBtnFlashColor4, mBtnFlashColor5;
    ColorPickerDialog mColorPickDialog;

    IFragmentCallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (Constant.DEBUG)
            Log.v("lyf", "onCreateView");
        LinearLayout root = (LinearLayout) inflater.inflate(R.layout.fragment_glow_stick_setting, container, false);
        mStickPreviewView = (NeonStickView) root.findViewById(R.id.stick_preview_view);
        mStickPreviewView.setDrawMargin(100);
        mStickPreviewView.setNeonStyle(mNeonStyle);
        mBtnFullScreen = (ImageView) root.findViewById(R.id.btn_fullscreen);
        mBarSpeed = (SeekBar) root.findViewById(R.id.bar_speed);
        mStickPreviewView.setSpeed(mBarSpeed.getProgress());
        if (mNeonStyle != null) {
            mNeonStyle.setSpeed(mBarSpeed.getProgress());
        }
        mSwitch = (Switch) root.findViewById(R.id.btn_flash);
        mNeonPathListView = (RecyclerView) root.findViewById(R.id.rv_neonpath);
        mBtnBGColor = root.findViewById(R.id.btn_background);
        mBtnTextColor = root.findViewById(R.id.btn_text);
        mBtnFlashColor1 = root.findViewById(R.id.btn_flash_color_1);
        mBtnFlashColor2 = root.findViewById(R.id.btn_flash_color_2);
        mBtnFlashColor3 = root.findViewById(R.id.btn_flash_color_3);
        mBtnFlashColor4 = root.findViewById(R.id.btn_flash_color_4);
        mBtnFlashColor5 = root.findViewById(R.id.btn_flash_color_5);
        mBtnBGColor.setOnClickListener(this);
        mBtnTextColor.setOnClickListener(this);
        mBtnFlashColor1.setOnClickListener(this);
        mBtnFlashColor2.setOnClickListener(this);
        mBtnFlashColor3.setOnClickListener(this);
        mBtnFlashColor4.setOnClickListener(this);
        mBtnFlashColor5.setOnClickListener(this);
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
        mSwitch.setChecked(true);

        mNeonPathListView = (RecyclerView) root.findViewById(R.id.rv_neonpath);
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


        return root;
    }

    public void setNeonStyle(NeonStyle neonStyle) {
        mNeonStyle = neonStyle;
        if (Constant.DEBUG)
            Log.v("lyf", "setNeonStyle");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStickPreviewView != null) {
            mStickPreviewView.startPreview();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fullscreen:
                if (mCallback != null) {
                    mCallback.onCallback();
                }
                break;
            case R.id.btn_background:
                showBGColorDialog();
                break;
            case R.id.btn_text:

                break;
            case R.id.btn_flash_color_1:

                break;
            case R.id.btn_flash_color_2:

                break;
            case R.id.btn_flash_color_3:

                break;
            case R.id.btn_flash_color_4:

                break;
            case R.id.btn_flash_color_5:

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
                            if (mNeonStyle != null) {
                                mNeonStyle.setBackgroundColor(color);
                            }
                        }
                        Prefs.putInt(mContext, Prefs.Key.GLOW_STICK_BACKGROUND_COLOR, color);
                    }
                });
        mColorPickDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mStickPreviewView != null) {
            mStickPreviewView.stopPreview();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setFragmentCallback(IFragmentCallback callback) {
        mCallback = callback;
    }
}

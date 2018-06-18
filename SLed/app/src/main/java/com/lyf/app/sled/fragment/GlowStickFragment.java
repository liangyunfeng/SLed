package com.lyf.app.sled.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lyf.app.sled.R;
import com.lyf.app.sled.fragment.callback.IFragmentCallback;
import com.lyf.app.sled.neon.NeonStyle;
import com.lyf.app.sled.widget.NeonStickView;

/**
 * Created by yunfeng.l on 2018/1/31.
 */

public class GlowStickFragment extends Fragment {

    private final static String TAG = "GlowStickFragment";

    private Context mContext;

    NeonStickView mStickView;

    NeonStyle mNeonStyle;
    IFragmentCallback mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout root = (FrameLayout) inflater.inflate(R.layout.fragment_glow_stick, container, false);
        mStickView = (NeonStickView) root.findViewById(R.id.stick_view);
        mStickView.setDrawMargin(200);
        mStickView.setNeonStyle(mNeonStyle);

        return root;
    }

    public void setNeonStyle(NeonStyle neonStyle) {
        mNeonStyle = neonStyle;
    }

    public void setFragmentCallback(IFragmentCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStickView != null) {
            mStickView.startPreview();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mStickView != null) {
            mStickView.stopPreview();
        }
    }
}

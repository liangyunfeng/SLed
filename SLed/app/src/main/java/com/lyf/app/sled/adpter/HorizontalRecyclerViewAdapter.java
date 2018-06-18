package com.lyf.app.sled.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyf.app.sled.R;
import com.lyf.app.sled.neon.NeonPath;
import com.lyf.app.sled.neon.NeonPathView;

import java.util.List;

/**
 * Created by yunfeng.l on 2018/1/30.
 */

public class HorizontalRecyclerViewAdapter extends
        RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<NeonPath> mNeonPathList;
    private OnItemClickListener mOnItemClickListener;

    public HorizontalRecyclerViewAdapter(Context context, List<NeonPath> list) {
        mInflater = LayoutInflater.from(context);
        mNeonPathList = list;
    }

    @Override
    public int getItemCount() {
        return mNeonPathList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.neon_path_view_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.neonPathView = (NeonPathView) view.findViewById(R.id.neon_path_view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.neonPathView.setNeonPath(mNeonPathList.get(position));
        if (mOnItemClickListener != null) {
            viewHolder.neonPathView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(position);
                }
            });
            viewHolder.neonPathView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        NeonPathView neonPathView;
    }
}


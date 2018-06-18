package com.lyf.app.sled.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyf.app.sled.R;
import com.lyf.app.sled.neon.DotColorView;

/**
 * Created by yunfeng.l on 2018/1/30.
 */

public class DotColorPickRecyclerViewAdapter extends
        RecyclerView.Adapter<DotColorPickRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private int colorArrays[][];
    private OnItemClickListener mOnItemClickListener;
    private View oldSelectedView;
    private int orinSelected = 0;

    public DotColorPickRecyclerViewAdapter(Context context, int colors[][]) {
        mInflater = LayoutInflater.from(context);
        colorArrays = colors;
    }

    @Override
    public int getItemCount() {
        return colorArrays.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.dot_color_view_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.dotColorView = (DotColorView) view;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.dotColorView.setPatternColors(colorArrays[position]);
        if (orinSelected == position) {
            viewHolder.dotColorView.setSelected(true);
            oldSelectedView = viewHolder.dotColorView;
        } else {
            viewHolder.dotColorView.setSelected(false);
        }
        if (mOnItemClickListener != null) {
            viewHolder.dotColorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSelectedState(view);
                    mOnItemClickListener.onClick(position);
                }
            });
            viewHolder.dotColorView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    updateSelectedState(view);
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void updateSelectedState(View selectedView) {
        if (oldSelectedView != null) {
            oldSelectedView.setSelected(false);
        }
        selectedView.setSelected(true);
        oldSelectedView = selectedView;
    }

    public void setSelectedColor(int pos) {
        //oldSelectedView = null;
        orinSelected = pos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        DotColorView dotColorView;
    }
}


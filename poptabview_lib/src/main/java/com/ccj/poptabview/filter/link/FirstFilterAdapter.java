package com.ccj.poptabview.filter.link;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnHolderClickListener;

import java.util.List;

/**
 * 左侧一级筛选adapter
 * @author Aidi on 17/3/23.
 */
public class FirstFilterAdapter extends RecyclerView.Adapter implements OnHolderClickListener {

    private OnFirstItemClickListener mListener;

    private List<FilterTabBean> mData;
    private int checkedPosition;//选中的项的id

    public FirstFilterAdapter(OnFirstItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_primary, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(mData.get(position).getTab_name());
            if (position==checkedPosition) {
                viewHolder.tv_filter.setChecked(true);
                viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_left, 0);
            } else {
                viewHolder.tv_filter.setChecked(false);
                viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void setData(List<FilterTabBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            checkedPosition = -1;
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            checkedPosition = -1;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置选中的id
     * @param checkedPosition
     */
    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

 /*   public String getCheckedName() {
        if (!TextUtils.isEmpty(checkedPosition) && mData != null && mData.size() > 0) {
            for (FilterTabBean data : mData) {
                if (checkedPosition.equals(data.getTab_id())) {
                    return data.getTab_name();
                }
            }
        }
        return "无";
    }*/

    @Override
    public void onItemClick(int position, int viewType) {
        if (position < mData.size()) {
            FilterTabBean data = mData.get(position);
            if (position!=checkedPosition) {
                mListener.onFirstItemClick(position, data.getTab_id(), data.getTab_name());
                checkedPosition = position;
                notifyDataSetChanged();
            }
        }
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckedTextView tv_filter;
        OnHolderClickListener mListener;

        public FilterViewHolder(View itemView, OnHolderClickListener listener) {
            super(itemView);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
            tv_filter.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (v instanceof CheckedTextView) {
                    mListener.onItemClick(getAdapterPosition(), 0);
                }
            }
        }
    }

    public interface OnFirstItemClickListener {
        void onFirstItemClick(int position, String id, String title);
    }
}

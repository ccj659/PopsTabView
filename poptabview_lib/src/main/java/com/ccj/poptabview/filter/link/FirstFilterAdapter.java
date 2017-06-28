package com.ccj.poptabview.filter.link;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.listener.OnHolderClickListener;
import com.ccj.poptabview.R;
import com.ccj.poptabview.bean.SingleFilterBean;

import java.util.List;

/**
 * 左侧一级筛选adapter
 * @author Aidi on 17/3/23.
 */
public class FirstFilterAdapter extends RecyclerView.Adapter implements OnHolderClickListener {

    private OnFirstItemClickListener mListener;

    private List<SingleFilterBean> mData;
    private String checkedId;//选中的项的id

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
            viewHolder.tv_filter.setText(mData.get(position).title);
            if (mData.get(position).id != null && mData.get(position).id.equals(checkedId)) {
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

    public void setData(List<SingleFilterBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            checkedId = null;
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            checkedId = null;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置选中的id
     * @param checkedId
     */
    public void setCheckedId(String checkedId) {
        this.checkedId = checkedId;
        notifyDataSetChanged();
    }

    public String getCheckedName() {
        if (!TextUtils.isEmpty(checkedId) && mData != null && mData.size() > 0) {
            for (SingleFilterBean data : mData) {
                if (checkedId.equals(data.id)) {
                    return data.title;
                }
            }
        }
        return "无";
    }

    @Override
    public void onItemClick(int position, int viewType) {
        if (position < mData.size()) {
            SingleFilterBean data = mData.get(position);
            if (!data.getId().equals(checkedId)) {
                mListener.onFirstItemClick(position, data.getId(), data.getTitle());
                checkedId = data.getId();
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

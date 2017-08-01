package com.ccj.poptabview.filter.sort;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSortItemClickListener;

/**
 * 筛选器adapter
 *
 * @author  ccj sj
 */
public class SortFilterAdapter extends SuperAdapter {

    public static int INITIAL_COUNT = 6;//初始状态显示6个项目
    private boolean isExpand = false;//是否已展开


    public SortFilterAdapter(OnSortItemClickListener listener, int singleOrMultiply) {
        super(null, listener, singleOrMultiply);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(mData.get(position).getTab_name());

            if (checkedLists.contains(position)) {
                viewHolder.tv_filter.setChecked(true);
            } else {
                viewHolder.tv_filter.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else if (mData.size() > 6 && !isExpand) {
            return INITIAL_COUNT;
        }
        return mData.size();
    }


    public void clearDataAndExpand() {
        if (mData != null) {
            mData.clear();
            isExpand = false;
            notifyDataSetChanged();
        }
    }


    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int pos) {
        //商城筛选需要记住当前选中的项目
        onItemClickEvent(pos);
        ((OnSortItemClickListener) mListener).onSortItemClick(pos, checkedLists);
    }


    public static class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckedTextView tv_filter;
        OnHolderClickedListener mListener;

        public FilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
            tv_filter.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof CheckedTextView) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}

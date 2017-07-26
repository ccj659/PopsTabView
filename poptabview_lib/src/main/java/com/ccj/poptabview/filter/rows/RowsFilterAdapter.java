package com.ccj.poptabview.filter.rows;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSortItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选器adapter
 *
 * @update ccj sj
 */
public class RowsFilterAdapter extends RecyclerView.Adapter implements OnHolderClickedListener {

    public static  int INITIAL_COUNT = 6;//初始状态显示6个项目

    private OnSortItemClickListener mListener;

    private List<BaseFilterTabBean> mData;
    private int singleOrMultiply;
    private boolean isExpand = false;//是否已展开
    private List<Integer> checkedList = new ArrayList<>();//选中的项的id


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(v, this);
    }

    public RowsFilterAdapter(OnSortItemClickListener listener, int singleOrMultiply) {
        mListener = listener;
        this.singleOrMultiply = singleOrMultiply;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(mData.get(position).getTab_name());

            if (checkedList.contains(position)) {
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

    public void setData(List<BaseFilterTabBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            isExpand = false;
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        notifyDataSetChanged();
    }

    public void setCheckedList(List checkedIndex) {


        if (checkedIndex == null||checkedIndex.isEmpty()) {
            checkedList.clear();
        } else {
            this.checkedList.addAll(checkedIndex);
        }

        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos < mData.size()) {
            //商城筛选需要记住当前选中的项目
            Integer position= Integer.valueOf(pos);
            if (checkedList.contains(position)) {
                checkedList.remove(position);
            } else if (singleOrMultiply == FilterConfig.FILTER_TYPE_SINGLE) {//单选
                checkedList.clear();
                checkedList.add(position);
                notifyDataSetChanged();
            } else if (singleOrMultiply == FilterConfig.FILTER_TYPE_MUTIFY) {//多选
                checkedList.add(position);
                notifyDataSetChanged();
            } else {
                notifyDataSetChanged();
            }
            notifyDataSetChanged();

            mListener.onSortItemClick(position, checkedList);//此处的singleOrMultiply无用~
        }
    }

    public void clearChecked() {
        checkedList.clear();
        notifyDataSetChanged();
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

package com.ccj.poptabview.filter.link;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSecondItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧二级筛选adapter
 *
 * @author ccj on 17/3/23.
 */
public class SecondFilterAdapter extends SuperAdapter {

    private int firstPosition; //一级菜单 选中

    public SecondFilterAdapter(OnSecondItemClickListener listener, int singleOrMultiply) {
        super(null, listener, singleOrMultiply);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_second_filter, parent, false);
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
        }
        return mData.size();
    }

    public void setData(int firstPosition, List<BaseFilterTabBean> data) {

        this.firstPosition = firstPosition;
        mData = new ArrayList<>();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int pos) {
        onItemClickEvent(pos);
        ((OnSecondItemClickListener) mListener).onSecondItemClick(firstPosition, mData.get(pos), (ArrayList<Integer>) checkedLists);
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

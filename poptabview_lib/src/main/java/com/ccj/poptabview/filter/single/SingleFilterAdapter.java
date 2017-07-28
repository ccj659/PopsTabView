package com.ccj.poptabview.filter.single;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSingleItemClickListener;

import java.util.List;

/**
 * 单栏筛选adapter
 *
 * @author ccj on 17/3/23.
 */
public class SingleFilterAdapter extends SuperAdapter{


    public SingleFilterAdapter(List<BaseFilterTabBean> beanList, OnSingleItemClickListener listener, int single2mutiple) {
        super(beanList,listener,single2mutiple);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_single, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FilterViewHolder viewHolder = (FilterViewHolder) holder;
        viewHolder.tv_filter.setText(mData.get(position).getTab_name());
        if (checkedLists.contains(position)) {
            viewHolder.tv_filter.setChecked(true);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filter_result_menu_selected, 0);
        } else {
            viewHolder.tv_filter.setChecked(false);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public void onItemClick(int position) {
        onItemClickEvent(position);
        ((OnSingleItemClickListener) mListener).onSingleItemClickListener(checkedLists);//强转
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
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                if (v instanceof CheckedTextView) {
                    mListener.onItemClick(getAdapterPosition());
                }
            }
        }
    }
}

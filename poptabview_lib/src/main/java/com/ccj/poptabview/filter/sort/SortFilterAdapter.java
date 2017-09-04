package com.ccj.poptabview.filter.sort;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.base.SuperListener;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSortItemClickListener;

import static com.ccj.poptabview.FilterConfig.ROWS_INITIAL_COUNT;

/**
 * 筛选器adapter
 *
 * @author  ccj sj
 */
public class SortFilterAdapter extends SuperAdapter {

    private boolean isExpand = false;//是否已展开


    public SortFilterAdapter(SuperListener listener, int singleOrMultiply) {
        super(null, listener, singleOrMultiply);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() != null && position < getData().size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(getData().get(position).getTab_name());

            if (getCheckedLists().contains(position)) {
                viewHolder.tv_filter.setChecked(true);
            } else {
                viewHolder.tv_filter.setChecked(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getData() == null) {
            return 0;
        } else if (getData().size() > ROWS_INITIAL_COUNT && !isExpand) {
            return ROWS_INITIAL_COUNT;
        }
        return getData().size();
    }

    @Override
    public void onFilterItemClick(int position) {
        ((OnSortItemClickListener) getListener()).onSortItemClick(position, getCheckedLists());

    }


    public void clearDataAndExpand() {
        if (getData() != null) {
            getData().clear();
            isExpand = false;
            notifyDataSetChanged();
        }
    }


    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        notifyDataSetChanged();
    }


    public static class FilterViewHolder extends SuperFilterViewHolder {

        CheckedTextView tv_filter;

        public FilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView,listener);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
        }

    }
}

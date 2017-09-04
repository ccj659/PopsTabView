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
        }
        return getData().size();
    }

    @Override
    public void onFilterItemClick(int position) {
        ((OnSecondItemClickListener) getListener()).onSecondItemClick(firstPosition, getData().get(position), (ArrayList<Integer>) getCheckedLists());
    }

    public void setData(int firstPosition, List<BaseFilterTabBean> data) {

        this.firstPosition = firstPosition;
        setData(new ArrayList<BaseFilterTabBean>());
        if (data != null) {
            getData().addAll(data);
        }
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

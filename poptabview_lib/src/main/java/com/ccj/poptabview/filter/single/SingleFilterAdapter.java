package com.ccj.poptabview.filter.single;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.base.SuperListener;
import com.ccj.poptabview.listener.OnHolderClickedListener;
import com.ccj.poptabview.listener.OnSingleItemClickListener;

import java.util.List;

/**
 * 单栏筛选adapter
 *
 * @author ccj on 17/3/23.
 */
public class SingleFilterAdapter extends SuperAdapter {


    public SingleFilterAdapter(List<BaseFilterTabBean> beanList, SuperListener listener, int single2mutiple) {
        super(beanList, listener, single2mutiple);
    }


    @Override
    public void onFilterItemClick() {
        ((OnSingleItemClickListener) getListener()).onSingleItemClickListener(getCheckedLists());//强转
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_single, parent, false);//可以根据自己的布局,进行修改
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FilterViewHolder viewHolder = (FilterViewHolder) holder;
        viewHolder.tv_filter.setText(getData().get(position).getTab_name());
        //根据自己的样式修改
        if (getCheckedLists().contains(position)) {
            viewHolder.tv_filter.setChecked(true);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filter_result_menu_selected, 0);
        } else {
            viewHolder.tv_filter.setChecked(false);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }


    //根据自己的样式修改
    public static class FilterViewHolder extends SuperFilterViewHolder {

        private CheckedTextView tv_filter;

        public FilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView, listener);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
        }

    }
}

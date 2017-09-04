package com.ccj.tabview.mypoptabview.myfilter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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


public class MyFilterAdapter extends SuperAdapter {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_filter, parent, false);//可以根据自己的布局,进行修改
        return new MyFilterViewHolder(v, this);
    }



    public MyFilterAdapter(List<BaseFilterTabBean> beanList, SuperListener listener, int single2mutiple) {
        super(beanList, listener, single2mutiple);
    }


    @Override
    public void onFilterItemClick(int position) {
        ((OnSingleItemClickListener) getListener()).onSingleItemClickListener(getCheckedLists());//强转
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyFilterViewHolder viewHolder = (MyFilterViewHolder) holder;
        viewHolder.tv_filter.setText(getData().get(position).getTab_name());
        //根据自己的样式修改
        if (getCheckedLists().contains(position)) {
            viewHolder.tv_filter.setSelected(true);
        } else {
            viewHolder.tv_filter.setSelected(false);
        }
    }


    //根据自己的样式修改
    public static class MyFilterViewHolder extends SuperFilterViewHolder {

        private TextView tv_filter;

        public MyFilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView, listener);
            tv_filter = (TextView) itemView.findViewById(R.id.textView);
        }

    }
}

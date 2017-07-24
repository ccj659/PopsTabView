package com.ccj.poptabview.filter.single;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.R;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnHolderClickListener;
import com.ccj.poptabview.listener.OnSingleItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选adapter
 *
 * @author ccj on 17/3/23.
 */
public class SingleFilterAdapter extends RecyclerView.Adapter implements OnHolderClickListener {

    private OnSingleItemClickListener mListener;

    private List<FilterTabBean> beanList;


    private List<Integer> checkedLists = new ArrayList();//选中的项的id

    int single2mutiple;

    public SingleFilterAdapter(List<FilterTabBean> beanList, OnSingleItemClickListener listener, int single2mutiple) {
        this.beanList = beanList;
        mListener = listener;
        this.single2mutiple=single2mutiple;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filter_single, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FilterViewHolder viewHolder = (FilterViewHolder) holder;
        viewHolder.tv_filter.setText(beanList.get(position).getTab_name());
        if (checkedLists.contains(position)) {
            viewHolder.tv_filter.setChecked(true);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filter_result_menu_selected, 0);
        } else {
            viewHolder.tv_filter.setChecked(false);
            viewHolder.tv_filter.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }

    @Override
    public void onItemClick(int pos, int viewType) {
        Integer position=Integer.valueOf(pos);//这里的position是value,不是index,又被坑了一波
        if (checkedLists.contains(position)) {
            checkedLists.remove(position);
        } else if (single2mutiple == FilterConfig.FILTER_TYPE_SINGLE) { //单选
            checkedLists.clear();
            checkedLists.add(position);
        } else {
            checkedLists.add(position); //多选
        }
        notifyDataSetChanged();
        mListener.onSingleItemClickListener(checkedLists);
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
}

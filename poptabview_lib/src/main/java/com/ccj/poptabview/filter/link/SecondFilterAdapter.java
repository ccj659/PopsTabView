package com.ccj.poptabview.filter.link;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.listener.OnHolderClickListener;
import com.ccj.poptabview.R;
import com.ccj.poptabview.bean.SingleFilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧二级筛选adapter
 *
 * @author Aidi on 17/3/23.
 */
public class SecondFilterAdapter extends RecyclerView.Adapter implements OnHolderClickListener {

    private OnSecondItemClickListener listener;
    private List<SingleFilterBean.SecondFilterBean> mData;


    private SingleFilterBean.SecondFilterBean checkedItem;// 选中的 项目
    private int firstPosition; //一级菜单 选中

    public SecondFilterAdapter(OnSecondItemClickListener listener) {
        this.listener = listener;
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
            //if (position == 0) {
                //viewHolder.tv_filter.setText("全部");
            //} else {
                viewHolder.tv_filter.setText(mData.get(position).getTitle());
            //}
            if (checkedItem != null && checkedItem.id == mData.get(position).getId()) {
                viewHolder.tv_filter.setChecked(true);
            } else {
                viewHolder.tv_filter.setChecked(false);
            }

           /* if (tListFlag.get(position)) {
                holder.textView.setTextColor(Color.parseColor("#f04848"));
                holder.textView.setBackground(context.getResources().getDrawable(R.drawable.tag_group_bg_shape_on));
            } else {
                holder.textView.setTextColor(Color.parseColor("#333333"));

                holder.textView.setBackground(context.getResources().getDrawable(R.drawable.tag_group_bg_shape_off));
            }*/

        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void setData(int firstPosition, List<SingleFilterBean.SecondFilterBean> data) {

        this.firstPosition = firstPosition;
        mData = new ArrayList<>();
        if (data != null) {
            mData.addAll(data);
        }
        //增加"全部"
        //SingleFilterBean.SecondFilterBean all = new SingleFilterBean.SecondFilterBean();
        //all.title = "全部";
        //mData.add(0, all);
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            checkedItem = null;
            notifyDataSetChanged();
        }
    }


    public void setCheckedItem(SingleFilterBean.SecondFilterBean secondFilterBean) {
        this.checkedItem = secondFilterBean;
        notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int position, int viewType) {
        if (position >= 0 && position < mData.size()) {
            SingleFilterBean.SecondFilterBean data = mData.get(position);
            checkedItem = data;
            listener.onSecondItemClick(position, checkedItem);
        }

        //dismiss();
        notifyDataSetChanged();
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
        if (v instanceof CheckedTextView) {
            mListener.onItemClick(getAdapterPosition(), 0);
        }
    }
}


public interface OnSecondItemClickListener {
    void onSecondItemClick(int secondPos, SingleFilterBean.SecondFilterBean secondFilterBean);
}

}

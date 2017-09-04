package com.ccj.poptabview.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.listener.OnHolderClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter 父类
 * Created by chenchangjun on 17/7/11.
 */

public abstract class SuperAdapter extends RecyclerView.Adapter implements OnHolderClickedListener {

    private SuperListener mListener;//adapter和window之间的监听
    private List<BaseFilterTabBean> mData; //筛选数据
    private List<Integer> checkedLists = new ArrayList();//选中的项的id
    private int singleOrMultiply;//是否多选

    /**
     * @param beanList       数据
     * @param listener       监听
     * @param single2mutiple 是否是多选
     */
    public SuperAdapter(List<BaseFilterTabBean> beanList, SuperListener listener, int single2mutiple) {
        this.mData = beanList;
        mListener = listener;
        this.singleOrMultiply = single2mutiple;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(List<BaseFilterTabBean> data) {
        mData = data;
        notifyDataSetChanged();
    }


    /**
     * 清除数据和选中数据
     */
    public void clearDataAndChecked() {
        if (mData != null) {
            mData.clear();
            checkedLists.clear();
            notifyDataSetChanged();
        }
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 点击时逻辑处理
     *
     * @param pos
     */
    protected void onItemClickEvent(int pos) {
        Integer position = Integer.valueOf(pos);//这里的position是value,不是index,又被坑了一波
        if (checkedLists.contains(position)) {
            if (FilterConfig.FILTER_TYPE_CAN_CANCEL) { //当点击了已经选中的项目时, 是否取消选中
                checkedLists.remove(position);
            }
        } else if (singleOrMultiply == FilterConfig.FILTER_TYPE_SINGLE) { //单选
            checkedLists.clear();
            checkedLists.add(position);
        } else {
            checkedLists.add(position); //多选
        }
        notifyDataSetChanged();
    }


    /**
     * 清楚筛选
     */
    public void clearChecked() {
        checkedLists.clear();
        notifyDataSetChanged();
    }

    /**
     * 设置选中 集合
     *
     * @param checkedIndex
     */
    public void setCheckedList(List checkedIndex) {
        checkedLists.clear();
        if (checkedIndex == null || checkedIndex.isEmpty()) {
            checkedLists.clear();
        } else {
            this.checkedLists.addAll(checkedIndex);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置选中并且触发点击的 集合
     *
     * @param checkedIndex
     */
    public void setClickedList(List checkedIndex) {
        if (checkedIndex == null || checkedIndex.isEmpty()) {
            return;
        }
        for (int i = 0; i < checkedIndex.size(); i++) {
            onItemClick((Integer) checkedIndex.get(i));

        }
        notifyDataSetChanged();

    }

    /**
     * 子类处理点击事件
     */
    public abstract void onFilterItemClick(int position);


    @Override
    public void onItemClick(int position) {
        onItemClickEvent(position);
        onFilterItemClick(position);
    }


    public static class SuperFilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private OnHolderClickedListener mListener;

        public SuperFilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView);
            this.itemView = itemView;
            this.itemView.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }


    public SuperListener getListener() {
        return mListener;
    }

    public void setListener(SuperListener mListener) {
        this.mListener = mListener;
    }

    public List<BaseFilterTabBean> getData() {
        return mData;
    }


    public List<Integer> getCheckedLists() {
        return checkedLists;
    }

    public void setCheckedLists(List<Integer> checkedLists) {
        this.checkedLists = checkedLists;
    }

    public int getSingleOrMultiply() {
        return singleOrMultiply;
    }

    public void setSingleOrMultiply(int singleOrMultiply) {
        this.singleOrMultiply = singleOrMultiply;
    }
}

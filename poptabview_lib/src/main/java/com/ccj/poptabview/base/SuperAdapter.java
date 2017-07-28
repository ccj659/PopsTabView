package com.ccj.poptabview.base;

import android.support.v7.widget.RecyclerView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.listener.OnHolderClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * adapter 父类
 * Created by chenchangjun on 17/7/11.
 */

public abstract class SuperAdapter extends RecyclerView.Adapter implements OnHolderClickedListener {

    protected SuperListener mListener;//adapter和window之间的监听
    protected List<BaseFilterTabBean> mData; //筛选数据
    protected List<Integer> checkedLists = new ArrayList();//选中的项的id
    protected int singleOrMultiply;//是否多选


    public SuperAdapter(List<BaseFilterTabBean> beanList, SuperListener listener, int single2mutiple) {
        this.mData = beanList;
        mListener = listener;
        this.singleOrMultiply = single2mutiple;
    }

    /**
     * 设置数据
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
     * @param pos
     */
    protected void onItemClickEvent(int pos) {
        Integer position = Integer.valueOf(pos);//这里的position是value,不是index,又被坑了一波
        if (checkedLists.contains(position)) {
            checkedLists.remove(position);
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
     * @param checkedIndex
     */
    public void setCheckedList(List checkedIndex) {


        if (checkedIndex == null || checkedIndex.isEmpty()) {
            checkedLists.clear();
        } else {
            this.checkedLists.addAll(checkedIndex);
        }

        notifyDataSetChanged();
    }


}

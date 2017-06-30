package com.ccj.poptabview.listener;

import com.ccj.poptabview.bean.SingleFilterBean;

/**
 * 各种筛选扩展
 * Created by chenchangjun on 17/6/20.
 */

public interface OnFilterSetListener {
    /**
     * 单项筛选结果
     * @param selectionBean 选中的bean
     */
    void onFilterSet(SingleFilterBean selectionBean);

    /**
     * 二级选中 筛选结果
     * @param firstBean 一级选中
     * @param selectionBean 二级选中
     */
    void onSecondFilterSet(SingleFilterBean firstBean, SingleFilterBean.SecondFilterBean selectionBean);

    /**
     * 复合选中
     *
     * @param params
     */
    void onSortFilterSet(String params);//// TODO: 17/6/28  修改参数

    void OnFilterCanceled();
}
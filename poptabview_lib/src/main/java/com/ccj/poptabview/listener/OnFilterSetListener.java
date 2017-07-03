package com.ccj.poptabview.listener;


import com.ccj.poptabview.bean.FilterTabBean;

/**
 * 各种筛选扩展
 * Created by chenchangjun on 17/6/20.
 */

public interface OnFilterSetListener {

    void onFilterSet(FilterTabBean selectionBean);

    void onSecondFilterSet(FilterTabBean filterTabBean, FilterTabBean.TabsBean selectionBean);

    void onSortFilterSet(String params, String values);

    void OnFilterCanceled();
}
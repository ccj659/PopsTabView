package com.ccj.poptabview;

import com.ccj.poptabview.single.SingleFilterBean;

/**
 * 各种筛选扩展
 * Created by chenchangjun on 17/6/20.
 */

public interface OnFilterSetListener {

    void onFilterSet(SingleFilterBean selectionBean);

    void onSecondFilterSet(SingleFilterBean firstBean, SingleFilterBean.SecondFilterBean selectionBean);

    void onSortFilterSet(String params);

    void OnFilterCanceled();
}
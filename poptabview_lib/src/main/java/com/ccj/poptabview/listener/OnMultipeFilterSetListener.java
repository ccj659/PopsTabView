package com.ccj.poptabview.listener;

import com.ccj.poptabview.bean.FilterTabBean;

import java.util.List;

/**
 * Created by chenchangjun on 17/7/6.
 */

public interface OnMultipeFilterSetListener {


    void onMultipeFilterSet(List<FilterTabBean> selectedList);

    void onMultipeSecondFilterSet(int firstPos, List<FilterTabBean> selectedSecondList);

    void onMultipeSortFilterSet(List<FilterTabBean> selectedSecondList);

    void OnMultipeFilterCanceled();

}

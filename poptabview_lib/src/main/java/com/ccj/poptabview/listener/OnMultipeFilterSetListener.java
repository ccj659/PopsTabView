package com.ccj.poptabview.listener;


import com.ccj.poptabview.base.BaseFilterTabBean;

import java.util.List;

/**
 * 各个filter和popstabview的交互接口
 * Created by chenchangjun on 17/7/6.
 */

public interface OnMultipeFilterSetListener {


    void onMultipeFilterSet(List<BaseFilterTabBean> selectedList);

    void onMultipeSecondFilterSet(int firstPos, List<BaseFilterTabBean> selectedSecondList);

    void onMultipeSortFilterSet(List<BaseFilterTabBean> selectedSecondList);

    void OnMultipeFilterCanceled();

}

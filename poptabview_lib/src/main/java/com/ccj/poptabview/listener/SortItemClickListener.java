package com.ccj.poptabview.listener;

import java.util.List;

/**
 * Created by chenchangjun on 17/7/7.
 */

public interface SortItemClickListener {
    /**
     * 筛选分类、商城点击
     */
    void onSortItemClick(int position, List<Integer> filterTabBeen);
}

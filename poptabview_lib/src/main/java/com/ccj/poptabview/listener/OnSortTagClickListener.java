package com.ccj.poptabview.listener;

import com.ccj.poptabview.base.SuperListener;

import java.util.ArrayList;

/**
 * sortfilter中的单项view的回调
 * Created by chenchangjun on 17/6/22.
 */

public interface OnSortTagClickListener extends SuperListener {


    /**
     *  sortfilter中的,当tag点击的回调
     * @param firstIndex 一级 下标
     * @param secondPos 二级 下标
     * @param filterTabBeen 选中的集合
     * @param filterYpe 筛选类型 一级筛选名字tab_name
     */
    void onComFilterTagClick(int firstIndex, int secondPos, ArrayList<Integer> filterTabBeen, String filterYpe);

}

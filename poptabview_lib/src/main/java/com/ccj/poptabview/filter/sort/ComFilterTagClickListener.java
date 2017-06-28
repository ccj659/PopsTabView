package com.ccj.poptabview.filter.sort;

/**
 * Created by chenchangjun on 17/6/22.
 */

public interface ComFilterTagClickListener {


    /**
     * 筛选分类、商城点击
     * @param id
     * @param name
     * @param type 1分类 2商城
     */
    void onComFilterTagClick(int position, String id, String name, String type);

}

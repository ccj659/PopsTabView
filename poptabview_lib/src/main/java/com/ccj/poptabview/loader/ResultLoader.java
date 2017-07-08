package com.ccj.poptabview.loader;

import com.ccj.poptabview.bean.FilterTabBean;

import java.util.List;

/**
 * 自定义扩展 结果 处理
 * Created by chenchangjun on 17/7/6.
 */

public interface ResultLoader<T> {


    /**
     * 自定义 filter筛选结果的传参类型
     * @param selectedList
     * @return
     */
   T getResultParamsIds(List<FilterTabBean> selectedList);

    /**
     * 自定义 filter筛选结果的tab值类型
     * @param selectedList
     * @return
     */
    T getResultShowValues(List<FilterTabBean> selectedList);

}

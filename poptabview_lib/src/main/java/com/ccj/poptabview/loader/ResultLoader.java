package com.ccj.poptabview.loader;


import com.ccj.poptabview.base.BaseFilterTabBean;

import java.util.List;

/**
 * 自定义扩展 结果 处理
 * Created by chenchangjun on 17/7/6.
 */

public interface ResultLoader<T> {


    /**
     * 自定义 filter筛选结果的传参类型
     * @param selectedList
     * @param filterType
     * @return
     */
   T getResultParamsIds(List<BaseFilterTabBean> selectedList, int filterType);

    /**
     * 自定义 filter筛选结果的tab值类型
     * @param selectedList
     * @param filterType
     * @return
     */
    String getResultShowValues(List<BaseFilterTabBean> selectedList, int filterType);





    /**
     * 自定义 二级 filter筛选结果的传参类型
     * @param selectedList
     * @param filterType
     * @return
     */
    T getSecondResultParamsIds(List<BaseFilterTabBean> selectedList, int filterType);

    /**
     * 自定义 二级 filter筛选结果的tab值类型
     * @param selectedList
     * @param filterType
     * @return
     */
    String getSecondResultShowValues(List<BaseFilterTabBean> selectedList, int filterType);



}

package com.ccj.poptabview.loader;

/**
 *  popwindow 分类 的加载器
 * Created by chenchangjun on 17/6/26.
 */

public interface PopTypeLoader {

    /**
     * 由每个window的业务类型 区分,判断.然后得到相应的类型
     * @param tag 业务号
     * @return
     */
    int getPopType(int tag);

}

package com.ccj.poptabview.listener;

/**
 *  * popstaview和外部调用的接口
 * Created by chenchangjun on 17/7/24.
 */
public interface OnPopTabSetListener<T> {
    /**
     *
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable 操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    void onPopTabSet(int index, String lable, T params, String value);
}
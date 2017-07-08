package com.ccj.poptabview.loader;

import android.content.Context;
import android.widget.PopupWindow;

import com.ccj.poptabview.listener.OnMultipeFilterSetListener;

import java.util.List;

/**
 *  * 由业务tag --->建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public interface PopTypeLoader {

    /**
     * 由 getPopType 得到不同的类型的filter实体
     * @param context
     * @param data
     * @param filterSetListener 监听
     * @param tag 筛选品类
     * @param type 筛选方式--单选 or  多选
     * @return
     */
    PopupWindow getPopEntity(Context context, List data, OnMultipeFilterSetListener filterSetListener, int tag, int type);
}

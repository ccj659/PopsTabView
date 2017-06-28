package com.ccj.poptabview.loader;

import android.content.Context;
import android.widget.PopupWindow;

import com.ccj.poptabview.listener.OnFilterSetListener;

import java.util.List;

/**
 *  * 由业务tag --->建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public interface PopEntityLoader {

    /**
     * 由 getPopType 得到不同的类型的filter实体
     * @param context
     * @param data
     * @param filterSetListener
     * @param tag
     * @return
     */
    PopupWindow getPopEntity(Context context, List data, OnFilterSetListener filterSetListener, int tag);
}

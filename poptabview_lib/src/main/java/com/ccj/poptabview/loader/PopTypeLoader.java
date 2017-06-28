package com.ccj.poptabview.loader;

import android.content.Context;
import android.widget.PopupWindow;

import com.ccj.poptabview.listener.OnFilterSetListener;

import java.util.List;

/**
 *  popwindow 分类 的加载器
 * Created by chenchangjun on 17/6/26.
 */

public interface PopTypeLoader {


    /**
     * 由poptype得到不同的类型的filter实体
     * @param context
     * @param data
     * @param filterSetListener
     * @param tag
     * @return
     */
    PopupWindow getPopEntity(Context context, List data, OnFilterSetListener filterSetListener, int tag);


    /**
     * 由每个window的业务类型 区分,判断.然后得到相应的类型
     * @param tag 业务号
     * @return
     */
    int getPopType(int tag);

}

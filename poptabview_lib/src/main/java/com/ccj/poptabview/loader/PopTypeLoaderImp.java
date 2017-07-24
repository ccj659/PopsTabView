package com.ccj.poptabview.loader;

import android.content.Context;
import android.widget.PopupWindow;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.filter.link.LinkFilterPopupWindow;
import com.ccj.poptabview.filter.single.MSingleFilterWindow;
import com.ccj.poptabview.filter.sort.SortPopupWindow;
import com.ccj.poptabview.filter.rows.RowsFilterWindow;

import java.util.List;

/**
 * 由筛选器类型 --->建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public class PopTypeLoaderImp implements PopEntityLoader {

    /**
     * 由 getPopType 得到不同的类型的filter实体
     * @param context
     * @param data
     * @param filterSetListener 监听
     * @param tag 筛选品类
     * @param type 筛选方式--单选 or  多选
     * @return
     */
    @Override
    public PopupWindow getPopEntity(Context context, List data, OnMultipeFilterSetListener filterSetListener, int tag, int type) {
        PopupWindow popupWindow = null;
        switch (tag) {
            case FilterConfig.TYPE_POPWINDOW_LINKED:
                popupWindow = new LinkFilterPopupWindow(context, data, filterSetListener,type);
                break;
            case FilterConfig.TYPE_POPWINDOW_SORT:
                popupWindow = new SortPopupWindow(context, data, filterSetListener, tag,type);
                break;
            case FilterConfig.TYPE_POPWINDOW_ROWS:
                popupWindow = new RowsFilterWindow(context, data, filterSetListener,type);
                break;

            default:
                popupWindow = new MSingleFilterWindow(context, data, filterSetListener,type);
                break;
        }
        return popupWindow;
    }
}

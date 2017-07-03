package com.ccj.poptabview.loader;

import android.content.Context;
import android.widget.PopupWindow;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.filter.link.LinkFilterPopupWindow;
import com.ccj.poptabview.listener.OnFilterSetListener;
import com.ccj.poptabview.filter.single.SingleFilterWindow;
import com.ccj.poptabview.filter.sort.SortPopupWindow;

import java.util.List;

/**
 * 由业务tag --->建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public class PopEntityLoaderImp implements PopEntityLoader {


    @Override
    public PopupWindow getPopEntity(Context context,  List data, OnFilterSetListener filterSetListener, int tag) {


        PopupWindow popupWindow = null;
        switch (tag) {
            case FilterConfig.TYPE_POPWINDOW_LINKED:
                popupWindow = new LinkFilterPopupWindow(context, data, filterSetListener, tag);
                break;
            case FilterConfig.TYPE_POPWINDOW_SORT:
                popupWindow = new SortPopupWindow(context, data, filterSetListener, tag);
                break;
            default:
                popupWindow = new SingleFilterWindow(context, data, filterSetListener, tag);
                break;
        }
        return popupWindow;
    }
}

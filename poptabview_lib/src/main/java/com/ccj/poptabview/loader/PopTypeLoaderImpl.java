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
 * Created by chenchangjun on 17/6/26.
 */

public class PopTypeLoaderImpl implements PopTypeLoader {



    @Override
    public PopupWindow getPopEntity(Context context, List data, OnFilterSetListener filterSetListener, int tag) {

        PopupWindow popupWindow = null;
        switch (tag) {
            case FilterConfig.TYPE_POPWINDOW_SINGLE:
                popupWindow = new SingleFilterWindow(context, data, filterSetListener, tag);
                break;
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

    @Override
    public int getPopType(int tag) {
        //旅游全部筛选位置顺序
        int type = 0;
        switch (tag) {
            case FilterConfig.LVYOU_FILTER_POSITION_TO:
                type=FilterConfig.TYPE_POPWINDOW_LINKED;
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_FILTER:
                type=FilterConfig.TYPE_POPWINDOW_SORT;
                break;
            default: //大部分都是单选
                type=FilterConfig.TYPE_POPWINDOW_SINGLE;
                break;
        }

        return type;
    }
}

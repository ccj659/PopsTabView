package com.ccj.poptabview.loader;

import com.ccj.poptabview.FilterConfig;

/**
 * Created by chenchangjun on 17/6/26.
 */

public class PopTypeLoaderImpl implements PopTypeLoader {

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

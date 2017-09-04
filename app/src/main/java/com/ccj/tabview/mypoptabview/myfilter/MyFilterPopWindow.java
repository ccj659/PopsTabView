package com.ccj.tabview.mypoptabview.myfilter;

import android.content.Context;

import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.filter.single.SingleFilterWindow;
import com.ccj.poptabview.listener.OnFilterSetListener;

import java.util.List;

/**
 * Created by chenchangjun on 17/8/25.
 */

public class MyFilterPopWindow extends SingleFilterWindow {


    /**
     * 重写父类构造方法,如果需要其他参数请在本类中定义
     * @param context
     * @param data
     * @param listener
     * @param filterType
     * @param singleOrMutiply
     */
    public MyFilterPopWindow(Context context, List data, OnFilterSetListener listener, int filterType, int singleOrMutiply) {
        super(context,data,listener,filterType,singleOrMutiply);
    }

    @Override
    public SuperAdapter getAdapter() {
        return new MyFilterAdapter(getData(), this, getSingleOrMultiply());
    }

}

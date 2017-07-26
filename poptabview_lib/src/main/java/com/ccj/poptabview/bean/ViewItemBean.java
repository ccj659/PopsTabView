package com.ccj.poptabview.bean;

import android.widget.TextView;

import com.ccj.poptabview.base.SuperPopWindow;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenchangjun on 17/7/26.
 */

public class ViewItemBean implements Serializable{

    private ArrayList<SuperPopWindow> mViewPopWondows = new ArrayList<>();//popwindow缓存集合
    private ArrayList<TextView> mLables = new ArrayList<TextView>(); //筛选标签textiew集合,用于字段展示和点击事件
    private ArrayList<String> mDefoutTitles = new ArrayList<>();//初始筛选展示字符串缓存,用于默认展示


    public ArrayList<SuperPopWindow> getmViewPopWondows() {
        return mViewPopWondows;
    }

    public void setmViewPopWondows(ArrayList<SuperPopWindow> mViewPopWondows) {
        this.mViewPopWondows = mViewPopWondows;
    }

    public ArrayList<TextView> getmLables() {
        return mLables;
    }

    public void setmLables(ArrayList<TextView> mLables) {
        this.mLables = mLables;
    }

    public ArrayList<String> getmDefoutTitles() {
        return mDefoutTitles;
    }

    public void setmDefoutTitles(ArrayList<String> mDefoutTitles) {
        this.mDefoutTitles = mDefoutTitles;
    }
}

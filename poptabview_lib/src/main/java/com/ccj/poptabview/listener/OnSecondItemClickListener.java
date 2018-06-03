package com.ccj.poptabview.listener;

import com.ccj.poptabview.base.SuperListener;

import java.util.ArrayList;

/**
 * Created by chenchangjun on 17/7/28.
 */


public interface OnSecondItemClickListener extends SuperListener{
    void onSecondItemClick(int secondPos, ArrayList<Integer> secondFilterBean);
}
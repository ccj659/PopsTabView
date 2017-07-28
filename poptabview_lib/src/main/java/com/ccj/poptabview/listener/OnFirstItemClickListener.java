package com.ccj.poptabview.listener;

import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperListener;

/**
 * Created by chenchangjun on 17/7/28.
 */

public interface OnFirstItemClickListener extends SuperListener {
    void onFirstItemClick(int position, BaseFilterTabBean filterTabBeen);
}

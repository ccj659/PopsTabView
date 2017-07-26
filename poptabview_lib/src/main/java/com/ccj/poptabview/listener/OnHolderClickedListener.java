package com.ccj.poptabview.listener;

public interface OnHolderClickedListener {

    /**
     * adapter中viewholder的选中回调
     * @param position view的下标
     */
    void onItemClick(int position);
}
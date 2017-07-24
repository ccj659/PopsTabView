package com.ccj.poptabview.listener;

public interface OnHolderClickListener {

    /**
     * adapter中viewholder的选中回调
     * @param position
     */
    void onItemClick(int position, int viewType);
}
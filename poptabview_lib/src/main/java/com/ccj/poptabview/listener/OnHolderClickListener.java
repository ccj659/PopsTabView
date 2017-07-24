package com.ccj.poptabview.listener;

public interface OnHolderClickListener {

    /**
     * adapter中viewholder的选中回调
     * @param position
     *
     */
    /**
     *   * adapter中viewholder的选中回调
     * @param position view的下标
     * @param viewType view的类型(单选,多选)
     */
    void onItemClick(int position, int viewType);
}
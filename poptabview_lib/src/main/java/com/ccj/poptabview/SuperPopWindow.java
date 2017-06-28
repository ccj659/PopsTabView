package com.ccj.poptabview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by chenchangjun on 17/5/4.
 */

public  class SuperPopWindow extends PopupWindow {

    protected View mParentView;

    public View getmParentView() {
        return mParentView;
    }

    public void setmParentView(View mParentView) {
        this.mParentView = mParentView;
    }


    public  void show(View anchor, int paddingTop){
        showAsDropDown(anchor);
    };

    /**
     * 适配Android7.0
     * @param anchor
     */
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    public SuperPopWindow() {
    }

    public SuperPopWindow(View contentView) {
        super(contentView);
    }

    public SuperPopWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public SuperPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public SuperPopWindow(Context context) {
        super(context);
    }

    public SuperPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SuperPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SuperPopWindow(int width, int height) {
        super(width, height);
    }
}

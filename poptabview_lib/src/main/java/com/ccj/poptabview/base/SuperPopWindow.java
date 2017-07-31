package com.ccj.poptabview.base;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ccj.poptabview.R;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 17/5/4.
 */

public abstract class SuperPopWindow extends PopupWindow implements View.OnClickListener {

    protected View mParentView;
    protected View mRootView;//根布局
    protected Context mContext;
    protected List<BaseFilterTabBean> mData = new ArrayList<>();
    protected OnMultipeFilterSetListener onFilterSetListener;
    protected int filterType; //一筛选类型,
    protected int singleOrMultiply;//单选多选
    protected SuperAdapter adapter;

    public SuperPopWindow(Context context, List data, OnMultipeFilterSetListener onFilterSetListener, int filterType, int singleOrMultiply) {
        this.mContext = context;
        this.mData = data;
        this.onFilterSetListener = onFilterSetListener;
        this.filterType = filterType;
        this.singleOrMultiply = singleOrMultiply;
        initView();
        initCommonContentView();
        initData();
    }



    protected void initCommonContentView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.setBackgroundDrawable(new ColorDrawable());

    }

    public abstract void initView();

    public abstract void initData();



    public void setCheckedItems(List items){
        if (adapter==null){
            return;
        }
        adapter.setCheckedList(items);
    }

    /**
     * 如果有需要,子类会重写该方法,
     * @param anchor
     * @param paddingTop
     */
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




















    public View getmParentView() {
        return mParentView;
    }

    public void setmParentView(View mParentView) {
        this.mParentView = mParentView;
    }


    public View getmRootView() {
        return mRootView;
    }

    public void setmRootView(View mRootView) {
        this.mRootView = mRootView;
    }

    public List<BaseFilterTabBean> getmData() {
        return mData;
    }

    public void setmData(List<BaseFilterTabBean> mData) {
        this.mData = mData;
    }

    public OnMultipeFilterSetListener getOnFilterSetListener() {
        return onFilterSetListener;
    }

    public void setOnFilterSetListener(OnMultipeFilterSetListener onFilterSetListener) {
        this.onFilterSetListener = onFilterSetListener;
    }

    public int getFilterType() {
        return filterType;
    }

    public void setFilterType(int filterType) {
        this.filterType = filterType;
    }

    public int getSingleOrMultiply() {
        return singleOrMultiply;
    }

    public void setSingleOrMultiply(int singleOrMultiply) {
        this.singleOrMultiply = singleOrMultiply;
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

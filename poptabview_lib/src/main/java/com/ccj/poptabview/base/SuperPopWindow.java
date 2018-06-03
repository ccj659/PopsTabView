package com.ccj.poptabview.base;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.ccj.poptabview.listener.OnFilterSetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 17/5/4.
 */

public abstract class SuperPopWindow extends PopupWindow implements View.OnClickListener {

    private View mParentView;
    private View mRootView;//根布局



    private Context mContext;
    private List<BaseFilterTabBean> mData = new ArrayList<>();
    private OnFilterSetListener onFilterSetListener;
    private int filterType; //一筛选类型,
    private int singleOrMultiply;//单选多选
    private SuperAdapter adapter;


    /**
     *
     * @param context
     * @param data
     * @param onFilterSetListener
     * @param filterType 筛选样式
     * @param singleOrMultiply 是否多选
     */
    public SuperPopWindow(Context context, List data, OnFilterSetListener onFilterSetListener, int filterType, int singleOrMultiply) {
        this.mContext = context;
        this.mData = data;
        this.onFilterSetListener = onFilterSetListener;
        this.filterType = filterType;
        this.singleOrMultiply = singleOrMultiply;
        mRootView=initView();
        adapter=setAdapter();
        initAdapter(adapter);
        setContentView(mRootView);
        initCommonContentView();
        initSelectData();

    }



    protected void initCommonContentView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
       // this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.setBackgroundDrawable(new ColorDrawable());
        mRootView.setOnClickListener(this);



    }




    /**
     * 得到子类的adapter
     * @return
     */
    public abstract SuperAdapter setAdapter() ;

    /**
     * 初始化adapter处理
     * 需要子类实现
     */
    public abstract void initAdapter(SuperAdapter adapter);


    /**
     * 布局绑定,id绑定
     * 需要子类实现
     */
    public abstract View initView();

    /**
     * 初始化SelectData
     * 需要子类实现
     */
    public abstract void initSelectData();


    /**
     * 设置触发点击事件的项
     * @param items
     */
    public void setClickedItems(List items){
        if (adapter==null){
            throw new NullPointerException("请实例化adapter");
        }
        adapter.setClickedList(items);
    }



    /**
     * 设置默认选中的项
     * @param items
     */
    public void setDefaultCheckedItems(List items){
        if (adapter==null){
            throw new NullPointerException("请实例化adapter");
        }
        adapter.setCheckedLists(items);
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


    @Override
    public void dismiss() {
        super.dismiss();
    }


    /**
     * 1.5 多选 不消失弹窗.点击外层 消失
     * @param callData
     */
    public   void onSuperPopWindowDismiss(boolean callData){
        if (callData){
            getAdapter().onFilterItemClick();
            dismiss();
        }else {
            dismiss();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public SuperAdapter getAdapter() {
        return adapter;
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

    public void setRootView(View mRootView) {
        this.mRootView = mRootView;
    }

    public List<BaseFilterTabBean> getData() {
        return mData;
    }

    public void setmData(List<BaseFilterTabBean> mData) {
        this.mData = mData;
    }

    public OnFilterSetListener getOnFilterSetListener() {
        return onFilterSetListener;
    }

    public void setOnFilterSetListener(OnFilterSetListener onFilterSetListener) {
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

package com.ccj.poptabview.filter.link;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.ccj.poptabview.listener.OnFilterSetListener;
import com.ccj.poptabview.R;
import com.ccj.poptabview.SuperPopWindow;
import com.ccj.poptabview.bean.SingleFilterBean;

import java.util.List;

/**
 * 左右双栏筛选PopupWindow
 *
 * @author Aidi on 17/3/23.
 */
public class LinkFilterPopupWindow extends SuperPopWindow implements View.OnClickListener, FirstFilterAdapter.OnFirstItemClickListener, SecondFilterAdapter.OnSecondItemClickListener {

    private static final int SPAN_COUNT = 2;
    public static final String TYPE_MALL = "mall";
    public static final String TYPE_CAT = "category";

    private Context mContext;

    private View mParentView;
    private View mRootView;//根布局，底部收起按钮

    private LinearLayoutManager mLayoutManagerPrimary;
    private GridLayoutManager mLayoutManagerSecondary;
    private View mLoadingView;
    private ViewStub mErrorView;
    private View mInflatedErrorView;

    private List<SingleFilterBean> mSelectionData;
    private int type;

    private RecyclerView rv_primary, rv_secondary;
    private FirstFilterAdapter mFirstAdapter;
    private SecondFilterAdapter mSecondAdapter;


    private OnFilterSetListener mListener;
    private int firstPosition; //一级菜单下标
    private int secondPosition;//二级菜单下标
    private String firstCheckedId;
    private SingleFilterBean.SecondFilterBean checkedSecondItem;

    public LinkFilterPopupWindow(Context context, List<SingleFilterBean> filterBeanList, OnFilterSetListener listener, int type) {
        mContext = context;
        this.mSelectionData = filterBeanList;
        mListener = listener;
        this.type = type;
        initView();
    }


    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_link, null);
        rv_primary = (RecyclerView) mRootView.findViewById(R.id.rv_primary);
        rv_secondary = (RecyclerView) mRootView.findViewById(R.id.rv_secondary);
        mLoadingView = mRootView.findViewById(R.id.view_loading);
        mErrorView = (ViewStub) mRootView.findViewById(R.id.error);
        mInflatedErrorView = null;

        mLayoutManagerPrimary = new LinearLayoutManager(mContext);
        mFirstAdapter = new FirstFilterAdapter(this);
        rv_primary.setLayoutManager(mLayoutManagerPrimary);
        rv_primary.setAdapter(mFirstAdapter);

        mLayoutManagerSecondary = new GridLayoutManager(mContext, SPAN_COUNT);
        mSecondAdapter = new SecondFilterAdapter(this);

        rv_secondary.setLayoutManager(mLayoutManagerSecondary);
        rv_secondary.setAdapter(mSecondAdapter);

        mRootView.setOnClickListener(this);
        setContentView(mRootView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        //this.setAnimationStyle(R.style.anim_popwindow);
        this.setAnimationStyle(R.style.PopupWindowAnimation);

        this.setBackgroundDrawable(new ColorDrawable());
    }

    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
        setDataAndSelection();
    }


    /**
     * 设置默认选中状态,每次pop都要设置一次
     */
    private void setDataAndSelection() {
        mFirstAdapter.setData(mSelectionData);

        if (mSelectionData != null && mSelectionData.size() > firstPosition) {

            mFirstAdapter.setCheckedId(mSelectionData.get(firstPosition).id);//一级默认选择

            mSecondAdapter.setData(firstPosition, mSelectionData.get(firstPosition).childFilterList);

            if (mSelectionData.get(firstPosition) != null && mSelectionData.get(firstPosition).childFilterList.size() > 0) {//二级默认选中
                if (checkedSecondItem != null) {
                    mSecondAdapter.setCheckedItem(checkedSecondItem);
                } else {
                    mSecondAdapter.setCheckedItem(mSelectionData.get(firstPosition).childFilterList.get(0));
                }
            }
        }
        rv_primary.scrollToPosition(0);


    }


    /**
     * 一级菜单点击事件 回调,刷新二级菜单列表,以及默认
     *
     * @param position
     * @param id
     * @param title
     */
    @Override
    public void onFirstItemClick(int position, String id, String title) {
        firstPosition = position;
        if (mSelectionData != null && mSelectionData.size() > firstPosition) {

            // mFirstAdapter.setCheckedId(mSelectionData.get(firstPosition).id);//一级默认选择

            mSecondAdapter.setData(firstPosition, mSelectionData.get(firstPosition).childFilterList);

            if (mSelectionData.get(firstPosition).childFilterList != null && mSelectionData.get(firstPosition).childFilterList.size() > 0) {
                if (checkedSecondItem != null) {
                    mSecondAdapter.setCheckedItem(checkedSecondItem);
                } else {
                    mSecondAdapter.setCheckedItem(mSelectionData.get(firstPosition).childFilterList.get(0));
                }
            }
        }

    }

    /**
     * 二级菜单 点击事件回调
     *
     * @param secondPos
     * @param secondFilterBean
     */
    @Override
    public void onSecondItemClick(int secondPos, SingleFilterBean.SecondFilterBean secondFilterBean) {
        secondPosition = secondPos;
        checkedSecondItem = secondFilterBean;

        if (mSelectionData.get(firstPosition).childFilterList != null && mSelectionData.get(firstPosition).childFilterList.size() > 0) {
            mListener.onSecondFilterSet(mSelectionData.get(firstPosition), secondFilterBean);
        }
        dismiss();

    }

    public View getmParentView() {
        return mParentView;
    }

    public void setmParentView(View mParentView) {
        this.mParentView = mParentView;
    }


    @Override
    public void onClick(View v) {
        mListener.OnFilterCanceled();
        this.dismiss();

    }
}

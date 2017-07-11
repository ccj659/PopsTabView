package com.ccj.poptabview.filter.rows;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.ccj.poptabview.R;
import com.ccj.poptabview.SuperPopWindow;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnSortItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class RowsFilterWindow extends SuperPopWindow implements View.OnClickListener, OnSortItemClickListener {

    private int SPAN_COUNT = 3;

    private Context mContext;
    private View mRootView,iv_expand_border;//根布局
    private RecyclerView recyclerView;
    private RowsFilterAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private List<FilterTabBean> mData = new ArrayList<>();
    private List<FilterTabBean> mSelectedData = new ArrayList<>();

    private int tag;//单栏筛选的 tag,来标记对象的类型

    private OnMultipeFilterSetListener mListener;
    private boolean isMallInlandExpand;

    /**
     * @param context
     * @param data     要筛选的数据
     * @param listener 监听
     * @param tag      标记对象
     */
    public RowsFilterWindow(Context context, List data, OnMultipeFilterSetListener listener, int tag) {
        mContext = context;
        mListener = listener;
        mData = data;
        this.tag = tag;
        initView();
        initData();
    }

    private void initData() {
        if (mData==null){
            iv_expand_border.setVisibility(View.GONE);
            return;
        }
        if (mData.size() > 6) {
            iv_expand_border.setVisibility(View.VISIBLE);
        } else {
            iv_expand_border.setVisibility(View.GONE);
        }
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_rows, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        iv_expand_border=mRootView.findViewById(R.id.iv_expand_border);
        mLayoutManager = new GridLayoutManager(mContext,SPAN_COUNT);
        mAdapter = new RowsFilterAdapter( this, tag);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mData);
        mRootView.setOnClickListener(this);
        iv_expand_border.setOnClickListener(this);
        setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.setBackgroundDrawable(new ColorDrawable());




    }

    public void show(View anchor, int paddingTop) {
        isMallInlandExpand = false;

        showAsDropDown(anchor);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_expand_border) {
            onClickEvent();
        } else {
            mListener.OnMultipeFilterCanceled();
            this.dismiss();

        }
    }



    @Override
    public void onSortItemClick(int position, List<Integer> integerList) {
        if (mSelectedData != null) {
            mSelectedData.clear();
            if (integerList.isEmpty()) {
                mListener.onMultipeFilterSet(null);
            } else {
                for (int i = 0; i < integerList.size(); i++) {
                    mSelectedData.add(mData.get(integerList.get(i)));
                }
                mListener.onMultipeFilterSet(mSelectedData);
            }
        }
        dismiss();
    }



    public void onClickEvent() {
        iv_expand_border.setClickable(false);
        isMallInlandExpand = !isMallInlandExpand;
        mAdapter.setExpand(isMallInlandExpand);
        iv_expand_border.animate().rotation(180 - iv_expand_border.getRotation()).setDuration(200).setInterpolator(new LinearInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                iv_expand_border.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                iv_expand_border.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }
}

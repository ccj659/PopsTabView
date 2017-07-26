package com.ccj.poptabview.filter.rows;


import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnSortItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class RowsFilterWindow extends SuperPopWindow implements OnSortItemClickListener {




    private ImageView iv_expand_border;
    private RecyclerView recyclerView;
    private RowsFilterAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private List<BaseFilterTabBean> mSelectedData;


    private boolean isMallInlandExpand;

    /**
     * @param context
     * @param data     要筛选的数据
     * @param listener 监听
     * @param filterType
     * @param singleOrMutiply      标记对象
     */
    public RowsFilterWindow(Context context, List data, OnMultipeFilterSetListener listener, int filterType, int singleOrMutiply) {
        super(context,data,listener,filterType,singleOrMutiply);
    }


    @Override
    public void initData() {
        mSelectedData = new ArrayList<>();

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

    @Override
    public void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_rows, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        iv_expand_border= (ImageView) mRootView.findViewById(R.id.iv_expand_border);
        mLayoutManager = new GridLayoutManager(mContext, FilterConfig.ROWS_SPAN_COUNT);
        mAdapter = new RowsFilterAdapter( this, singleOrMultiply);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mData);
        mRootView.setOnClickListener(this);
        iv_expand_border.setOnClickListener(this);
        setContentView(mRootView);





    }

/*
    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
    }
*/

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_expand_border) {
            onClickEvent();
        } else {
            onFilterSetListener.OnMultipeFilterCanceled();
            this.dismiss();

        }
    }



    @Override
    public void onSortItemClick(int position, List<Integer> integerList) {
        if (mSelectedData != null) {
            mSelectedData.clear();
            if (integerList.isEmpty()) {
                onFilterSetListener.onMultipeFilterSet(null);
            } else {
                for (int i = 0; i < integerList.size(); i++) {
                    mSelectedData.add(mData.get(integerList.get(i)));
                }
                onFilterSetListener.onMultipeFilterSet(mSelectedData);
            }
        }
        dismiss();
    }



    public void onClickEvent() {
        iv_expand_border.setClickable(true);
        isMallInlandExpand = !isMallInlandExpand;
        mAdapter.setExpand(isMallInlandExpand);
        iv_expand_border.animate().rotation(180 - iv_expand_border.getRotation()).setDuration(200)
                .setInterpolator(new LinearInterpolator()).setListener(new Animator.AnimatorListener() {
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

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
import com.ccj.poptabview.base.SuperAdapter;
import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.listener.OnFilterSetListener;
import com.ccj.poptabview.listener.OnSingleItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class RowsFilterWindow extends SuperPopWindow implements OnSingleItemClickListener {


    private ImageView iv_expand_border;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;

    private List<BaseFilterTabBean> mSelectedData;


    private boolean isMallInlandExpand;
    private RowsFilterAdapter mAdapter;

    /**
     * @param context
     * @param data            要筛选的数据
     * @param listener        监听
     * @param filterType
     * @param singleOrMutiply 标记对象
     */
    public RowsFilterWindow(Context context, List data, OnFilterSetListener listener, int filterType, int singleOrMutiply) {
        super(context, data, listener, filterType, singleOrMutiply);
    }


    @Override
    public void initSelectData() {
        mSelectedData = new ArrayList<>();

        if (getData() == null) {
            iv_expand_border.setVisibility(View.GONE);
            return;
        }
        if (getData().size() > 6) {
            iv_expand_border.setVisibility(View.VISIBLE);
        } else {
            iv_expand_border.setVisibility(View.GONE);
        }
    }


    @Override
    public View initView() {
        View mRootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_filter_rows, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        iv_expand_border = (ImageView) mRootView.findViewById(R.id.iv_expand_border);
        iv_expand_border.setOnClickListener(this);
        return mRootView;

    }

    @Override
    public SuperAdapter setAdapter() {
        mAdapter = new RowsFilterAdapter(this, getSingleOrMultiply());
        return mAdapter;
    }


    @Override
    public void initAdapter(SuperAdapter adapter) {

        mLayoutManager = new GridLayoutManager(getContext(), FilterConfig.ROWS_SPAN_COUNT);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setData(getData());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_expand_border) {
            onClickEvent();
        } else {
            getOnFilterSetListener().OnFilterCanceled();
            this.dismiss();

        }
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

    @Override
    public void onSingleItemClickListener(List<Integer> integerList) {

        if (mSelectedData == null) {
            dismiss();
            return;
        }
        mSelectedData.clear();

        if (integerList.isEmpty()) {
            //getOnFilterSetListener().onSingleFilterSet(null);
        } else {
            for (int i = 0; i < integerList.size(); i++) {
                mSelectedData.add(getData().get(integerList.get(i)));
            }


            if (getSingleOrMultiply() == FilterConfig.FILTER_TYPE_SINGLE) {
                dismiss();
            }
        }
    }


    @Override
    public void dismiss() {
        if (isShowing()) {
            if (mSelectedData.isEmpty()) {
                getOnFilterSetListener().onSingleFilterSet(null);
            } else {
                getOnFilterSetListener().onSingleFilterSet(mSelectedData);
            }
        }

        super.dismiss();


    }
}

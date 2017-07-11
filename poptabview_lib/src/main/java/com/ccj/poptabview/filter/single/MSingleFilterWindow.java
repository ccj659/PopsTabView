package com.ccj.poptabview.filter.single;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccj.poptabview.R;
import com.ccj.poptabview.SuperPopWindow;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnMultipleItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class MSingleFilterWindow extends SuperPopWindow implements View.OnClickListener, OnMultipleItemClickListener {


    private Context mContext;

    private List<FilterTabBean> mData = new ArrayList<>();
    private List<FilterTabBean> mSelectedData = new ArrayList<>();

    private int tag;//单栏筛选的 tag,来标记对象的类型

    private OnMultipeFilterSetListener mListener;

    /**
     * @param context
     * @param data     要筛选的数据
     * @param listener 监听
     * @param tag      标记对象
     */
    public MSingleFilterWindow(Context context, List data, OnMultipeFilterSetListener listener, int tag) {
        mContext = context;
        mListener = listener;
        mData = data;
        this.tag = tag;
        initView();
    }

    private void initView() {
        View mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_single, null);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(mContext);
        MSingleFilterAdapter mAdapter = new MSingleFilterAdapter(mData, this, tag);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        mRootView.setOnClickListener(this);
        setContentView(mRootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setTouchable(true);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                mListener.OnMultipeFilterCanceled();
                this.dismiss();
                break;
        }
    }


    @Override
    public void onMultipleItemClickListener(List<Integer> integerList) {
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
}

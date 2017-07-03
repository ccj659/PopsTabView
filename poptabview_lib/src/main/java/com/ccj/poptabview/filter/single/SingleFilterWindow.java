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
import com.ccj.poptabview.listener.OnFilterSetListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class SingleFilterWindow extends SuperPopWindow implements View.OnClickListener, SingleFilterAdapter.OnSingleItemClickListener {

    public static final int TYPE_FILTER = 0;
    public static final int TYPE_SORT = 1;

    private Context mContext;
    private View mParentView;
    private View mRootView;//根布局
    private RecyclerView recyclerView;
    private SingleFilterAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private List<FilterTabBean> mSelectionData = new ArrayList<>();

    private int tag;//单栏筛选的 tag,来标记对象的类型

    private OnFilterSetListener mListener;

    /**
     *
     * @param context
     * @param data 要筛选的数据
     * @param listener 监听
     * @param tag 标记对象
     */
    public SingleFilterWindow(Context context, List data, OnFilterSetListener listener, int tag) {
        mContext = context;
        mListener = listener;
        mSelectionData=data;
        this.tag = tag;
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_single, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);

        mLayoutManager = new LinearLayoutManager(mContext);

        mAdapter = new SingleFilterAdapter(mSelectionData, this);
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
                mListener.OnFilterCanceled();
                this.dismiss();
                break;
        }
    }

    @Override
    public void onSingleItemClick(int position) {
        if (position<0){
            mListener.onFilterSet(null);

        }else {
            mListener.onFilterSet(mSelectionData.get(position));
        }
        dismiss();
    }

}

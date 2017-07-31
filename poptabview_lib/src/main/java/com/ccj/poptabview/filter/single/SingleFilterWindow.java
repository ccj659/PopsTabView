package com.ccj.poptabview.filter.single;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnSingleItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 单栏筛选PopupWindow
 *
 * @author ccj on 17/6/23.
 */
public class SingleFilterWindow extends SuperPopWindow implements OnSingleItemClickListener {

    private List<BaseFilterTabBean> mSelectedData;


    /**
     * @param context
     * @param data             要筛选的数据
     * @param listener         监听
     * @param filterType
     * @param singleOrMultiply 标记对象
     */
    public SingleFilterWindow(Context context, List data, OnMultipeFilterSetListener listener, int filterType, int singleOrMultiply) {
        super(context, data, listener, filterType, singleOrMultiply);

    }

    @Override
    public void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_single, null);
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        adapter = new SingleFilterAdapter(mData, this, singleOrMultiply);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        mRootView.setOnClickListener(this);
        setContentView(mRootView);
    }

    @Override
    public void initData() {
        mSelectedData = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                onFilterSetListener.OnMultipeFilterCanceled();
                this.dismiss();
                break;
        }
    }


    @Override
    public void onSingleItemClickListener(List<Integer> integerList) {
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
}

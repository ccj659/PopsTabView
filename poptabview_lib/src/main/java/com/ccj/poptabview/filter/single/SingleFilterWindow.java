package com.ccj.poptabview.filter.single;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

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
public class SingleFilterWindow extends SuperPopWindow implements OnSingleItemClickListener {

    private List<BaseFilterTabBean> mSelectedData;
    private RecyclerView recyclerView;

    /**
     * @param context
     * @param data             要筛选的数据
     * @param listener         监听
     * @param filterType
     * @param singleOrMultiply 标记对象
     */
    public SingleFilterWindow(Context context, List data, OnFilterSetListener listener, int filterType, int singleOrMultiply) {
        super(context, data, listener, filterType, singleOrMultiply);
    }


    @Override
    public View initView() {
        View mRootView = LayoutInflater.from(getContext()).inflate(R.layout.popup_filter_single, null);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerview);
        return mRootView;
    }


    @Override
    public SuperAdapter setAdapter() {
        SingleFilterAdapter adapter = new SingleFilterAdapter(getData(), this, getSingleOrMultiply());
        return adapter;
    }


    @Override
    public void initAdapter(SuperAdapter adapter) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initSelectData() {
        mSelectedData = new ArrayList<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                getOnFilterSetListener().OnFilterCanceled();
                this.dismiss();
                break;
        }
    }


    @Override
    public void onSingleItemClickListener(List<Integer> integerList) {

        if (mSelectedData == null) {
            dismiss();
            return;
        }

        mSelectedData.clear();

        if (integerList.isEmpty()) {
            getOnFilterSetListener().onSingleFilterSet(null);
        } else {
            for (int i = 0; i < integerList.size(); i++) {
                mSelectedData.add(getData().get(integerList.get(i)));
            }
            getOnFilterSetListener().onSingleFilterSet(mSelectedData);
        }
        dismiss();
    }
}

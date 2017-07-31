package com.ccj.poptabview.filter.link;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnSecondItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 左右双栏筛选PopupWindow
 *
 * @author ccj on 17/3/23.
 */
public class LinkFilterPopupWindow extends SuperPopWindow implements FirstFilterAdapter.OnFirstItemClickListener, OnSecondItemClickListener {


    private LinearLayoutManager mLayoutManagerPrimary;
    private GridLayoutManager mLayoutManagerSecondary;

    private LinearLayout ll_bottom;

    private TextView tv_reset, tv_confirm;
    private ImageView iv_collapse;


    private HashMap<Integer, List<Integer>> mSecondSelectedMap ;

    private RecyclerView rv_primary, rv_secondary;
    private FirstFilterAdapter mFirstAdapter;


    private int firstPosition = 0;

    public LinkFilterPopupWindow(Context context, List<BaseFilterTabBean> data, OnMultipeFilterSetListener listener, int filterType, int singleOrMultiply) {
        super(context,data,listener,filterType,singleOrMultiply);
    }

    @Override
    public void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.popup_filter_link, null);
        rv_primary = (RecyclerView) mRootView.findViewById(R.id.rv_primary);
        rv_secondary = (RecyclerView) mRootView.findViewById(R.id.rv_secondary);


        mLayoutManagerPrimary = new LinearLayoutManager(mContext);
        mFirstAdapter = new FirstFilterAdapter(this);
        rv_primary.setLayoutManager(mLayoutManagerPrimary);
        rv_primary.setAdapter(mFirstAdapter);

        mLayoutManagerSecondary = new GridLayoutManager(mContext, FilterConfig.LINKED_SPAN_COUNT);
        adapter = new SecondFilterAdapter(this, singleOrMultiply);

        rv_secondary.setLayoutManager(mLayoutManagerSecondary);
        rv_secondary.setAdapter(adapter);

        mRootView.setOnClickListener(this);

        if (singleOrMultiply == FilterConfig.FILTER_TYPE_MUTIFY) {
            ll_bottom = (LinearLayout) mRootView.findViewById(R.id.ll_bottom);
            iv_collapse = (ImageView) mRootView.findViewById(R.id.iv_collapse);
            tv_reset = (TextView) mRootView.findViewById(R.id.tv_reset);
            tv_confirm = (TextView) mRootView.findViewById(R.id.tv_confirm);
            ll_bottom.setVisibility(View.VISIBLE);
            iv_collapse.setOnClickListener(this);
            tv_confirm.setOnClickListener(this);
            tv_reset.setOnClickListener(this);
        }
        setContentView(mRootView);
    }

    @Override
    public void initData() {
        mSecondSelectedMap = new HashMap<>();

    }


    @Override
    public void show(View anchor, int paddingTop) {
        showAsDropDown(anchor);
        setDataAndSelection();
    }


    /**
     * 设置默认选中状态,每次pop都要设置一次
     */
    private void setDataAndSelection() {
        mFirstAdapter.setData(mData);
        if (mData != null && mData.size() > firstPosition) {
            mFirstAdapter.setCheckedPosition(firstPosition);//一级默认选择
            onFirstItemClick(firstPosition,mData.get(firstPosition));
        }
        rv_primary.scrollToPosition(0);


    }


    /**
     * 一级菜单点击事件 回调,刷新二级菜单列表,以及默认
     *
     * @param position
     */
    @Override
    public void onFirstItemClick(int position, BaseFilterTabBean mFirstSelectedData) {
        firstPosition = position;
        if (mData != null && mData.size() > firstPosition) {
            if (mData.get(position) != null && mData.get(position).getTabs().size() > 0) {//二级默认选中

                ((SecondFilterAdapter) adapter).setData(position, mData.get(position).getTabs());

                List cheked=mSecondSelectedMap.get(position);
                if (cheked != null&&!cheked.isEmpty()) {
                    adapter.setCheckedList(cheked);
                } else {
                    adapter.setCheckedList(null);
                }
            }
        }
        setConfirmButtonEnabled();

    }

    /**
     * 二级菜单 点击事件回调
     *
     * @param firstPos
     * @param secondFilterBean
     */
    @Override
    public void onSecondItemClick(int firstPos, BaseFilterTabBean filterTabBean, ArrayList<Integer> secondFilterBean) {


        if (singleOrMultiply == FilterConfig.FILTER_TYPE_SINGLE) {
            mSecondSelectedMap.clear();
            mSecondSelectedMap.put(firstPos, (List<Integer>) secondFilterBean.clone());

            List list =new ArrayList();
            list.add(filterTabBean);
            onFilterSetListener.onMultipeSecondFilterSet(firstPosition, list);
            dismiss();
        }else {
            mSecondSelectedMap.put(firstPos, (List<Integer>) secondFilterBean.clone());
            setConfirmButtonEnabled();
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_collapse) {//
            onFilterSetListener.OnMultipeFilterCanceled();
            this.dismiss();
        } else if (i == R.id.tv_confirm) {//

            List<BaseFilterTabBean> filterTabBeen= handleMutipleData();
            onFilterSetListener.onMultipeSecondFilterSet(firstPosition, filterTabBeen);
            this.dismiss();
            setConfirmButtonEnabled();

        } else if (i == R.id.tv_reset) {//
            mSecondSelectedMap.clear();
            setDataAndSelection();
            setConfirmButtonEnabled();

        } else {
            onFilterSetListener.OnMultipeFilterCanceled();
            this.dismiss();
            setConfirmButtonEnabled();


        }
    }

    private void setConfirmButtonEnabled() {

        if (singleOrMultiply == FilterConfig.FILTER_TYPE_SINGLE) {
            return;
        }

        boolean enabled=!mSecondSelectedMap.isEmpty();

        tv_reset.setEnabled(enabled);
        if (enabled) {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(mContext, R.color.product_color));
            tv_confirm.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
        } else {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(mContext, R.color.coloreee));
            tv_confirm.setTextColor(ContextCompat.getColor(mContext, R.color.color666));
        }
    }
    /**
     * @return
     */
    public List<BaseFilterTabBean> handleMutipleData() {
        List<BaseFilterTabBean> filterTabBeen = new ArrayList<>();

        for (Map.Entry<Integer, List<Integer>> entry : mSecondSelectedMap.entrySet()) {

            if (entry.getValue() != null && entry.getValue().size() > 0) {
                for (int j = 0; j < entry.getValue().size(); j++) {
                    int pos = entry.getValue().get(j);
                    filterTabBeen.add(mData.get(firstPosition).getTabs().get(pos));
                }
            }
        }

        return filterTabBeen;
    }



}
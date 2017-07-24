package com.ccj.poptabview.filter.sort;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.ComFilterTagClickListener;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用复合单选PopupWindow
 *
 * @created by ccj on 17/6/22
 */
public class SortPopupWindow extends SuperPopWindow implements View.OnClickListener, ComFilterTagClickListener {

    private static final String TYPE = "全站筛选";

    private Context mContext;
    private View mRootView;//根布局，底部收起按钮,分类选中区域
    private LinearLayout ll_content;
    private ViewStub mErrorView;
    private View mInflatedErrorView, iv_collapse;
    private TextView tv_reset, tv_confirm;
    private OnMultipeFilterSetListener onFilterSetListener;
    private int tag; //一级目录下标
    private int type;//筛选类型,单选多选

    private List<FilterTabBean> data = new ArrayList<>();
    private List<SortItemView> sortItemViewLists = new ArrayList<>();

    private HashMap<Integer, ArrayList<Integer>> checkedIndex = new HashMap<>();

    public SortPopupWindow(Context context, List data, OnMultipeFilterSetListener onFilterSetListener, int tag, int type) {
        mContext = context;
        this.data = data;
        this.onFilterSetListener = onFilterSetListener;
        this.tag = tag;
        this.type = type;
        initView();
        initData(data, type);

    }

    private void initData(List data, int type) {
        for (int i = 0; i < data.size(); i++) {
            FilterTabBean filterTabBean = (FilterTabBean) data.get(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            SortItemView sortItemView = new SortItemView(mContext);
            sortItemView.setLayoutParams(layoutParams);
            sortItemView.setLabTitle(filterTabBean.getTab_name());
            sortItemView.setAdapter(filterTabBean.getTab_name(), type);//将getTab_name 作为 唯一标示
            sortItemView.setFilterTagClick(this);
            sortItemView.setIndex(i);
            sortItemViewLists.add(sortItemView);
            ll_content.addView(sortItemView);
        }
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.common_popup_filter_sort, null);

        ll_content = (LinearLayout) mRootView.findViewById(R.id.ll_content);
        tv_reset = (TextView) mRootView.findViewById(R.id.tv_reset);
        tv_confirm = (TextView) mRootView.findViewById(R.id.tv_confirm);
        iv_collapse = mRootView.findViewById(R.id.iv_collapse);

        mInflatedErrorView = null;

        mRootView.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        iv_collapse.setOnClickListener(this);


        setContentView(mRootView);

        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        this.setBackgroundDrawable(new ColorDrawable());
    }


    public void show(View anchor, int paddingTop) {
        resetView();
        showAsDropDown(anchor);
        setButtonEnabled(true);
        loadSortItem();
    }

    private void resetView() {


        for (int i = 0; i < data.size(); i++) {
            sortItemViewLists.get(i).resetView();
        }
        setButtonEnabled(false);
    }


    private void loadSortItem() {

        for (int i = 0; i < data.size(); i++) {
            sortItemViewLists.get(i).setData(data.get(i).getTabs(), checkedIndex.get(i));
        }

        ll_content.setVisibility(View.VISIBLE);
        if (checkedIndex != null && checkedIndex.size() > 0) {
            setButtonEnabled(true);

        } else {
            setButtonEnabled(false);
        }
    }

    private void showErrorView() {
        if (mInflatedErrorView == null) {
            mInflatedErrorView = mErrorView.inflate();
            Button btn_reload = (Button) mInflatedErrorView.findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(this);
        }
        mInflatedErrorView.setVisibility(View.VISIBLE);
    }

    private void hideErrorView() {
        if (mInflatedErrorView != null) {
            mInflatedErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_reload) {//loadData();

        } else if (i == R.id.tv_confirm) {
            List list = getSortList();
            onFilterSetListener.onMultipeSortFilterSet(list);
            this.dismiss();

        } else if (i == R.id.tv_reset) {
            if (v.isEnabled()) {
                resetView();
                checkedIndex.clear();
                loadSortItem();
            }

        } else if (i == R.id.iv_collapse) {
            this.dismiss();

        } else {
            this.dismiss();

        }
    }

    private List getSortList() {
        List list = new ArrayList();
        for (Map.Entry<Integer, ArrayList<Integer>> entry : checkedIndex.entrySet()) {
            List list1 = data.get(entry.getKey()).getTabs();
            if (list1 != null) {
                for (int j = 0; j < list1.size(); j++) {
                    if (entry.getValue().contains(j)) {
                        list.add(list1.get(j));
                    }
                }
            }
        }
        return list;
    }


    private void setButtonEnabled(boolean enabled) {
        enabled = !checkedIndex.isEmpty();
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
     * @param firstPos
     * @param type     1分类 2商城  这里选tab_name
     */
    @Override
    public void onComFilterTagClick(int firstPos, int secondPos, ArrayList<Integer> filterTabBeen, String type) {
        if (filterTabBeen == null) {
            return;
        }

        checkedIndex.put(firstPos, (ArrayList<Integer>) (filterTabBeen).clone()); //需要克隆之前的集合,避免item.clean 造成数据消失
        setButtonEnabled(true);

    }

}

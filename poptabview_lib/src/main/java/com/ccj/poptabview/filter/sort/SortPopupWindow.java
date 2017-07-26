package com.ccj.poptabview.filter.sort;

import android.content.Context;
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
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnSortTagClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用复合单选PopupWindow
 *
 * @created by ccj on 17/6/22
 */
public class SortPopupWindow extends SuperPopWindow implements OnSortTagClickListener {


    private LinearLayout ll_content;
    private ViewStub mErrorView;
    private View mInflatedErrorView, iv_collapse;
    private TextView tv_reset, tv_confirm;

    private List<SortItemView> sortItemViewLists;

    private HashMap<Integer, ArrayList<Integer>> checkedIndex;

    public SortPopupWindow(Context context, List data, OnMultipeFilterSetListener listener, int filterType, int singleOrMutiply) {
        super(context, data, listener, filterType, singleOrMutiply);

    }

    @Override
    public void initData() {
        //在一个存在继承的类中：初始化父类static成员变量,运行父类static初始化块-->初始化子类static成员变量,
        // 运行子类static初始化块-->初始化父类实例成员变量(如果有赋值语句),执行父类普通初始化块-->父类构造方法-->初始化子类实例成员变量(如果有赋值语句)及普通初始化块-->子类构造方法。
        sortItemViewLists = new ArrayList<>();
        checkedIndex = new HashMap<>();
        for (int i = 0; i < mData.size(); i++) {
            FilterTabBean filterTabBean = mData.get(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            SortItemView sortItemView = new SortItemView(mContext);
            sortItemView.setLayoutParams(layoutParams);
            sortItemView.setLabTitle(filterTabBean.getTab_name());
            sortItemView.setAdapter(filterTabBean.getTab_name(), singleOrMutiply);//将getTab_name 作为 唯一标示
            sortItemView.setFilterTagClick(this);
            sortItemView.setIndex(i);
            sortItemViewLists.add(sortItemView);
            ll_content.addView(sortItemView);
        }
    }

    @Override
    public void initView() {
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
        this.setContentView(mRootView);
    }

    @Override
    public void show(View anchor, int paddingTop) {
        resetView();
        showAsDropDown(anchor);
        setButtonEnabled(true);
        loadSortItem();
    }

    private void resetView() {
        for (int i = 0; i < mData.size(); i++) {
            sortItemViewLists.get(i).resetView();
        }
        setButtonEnabled(false);
    }


    private void loadSortItem() {

        for (int i = 0; i < mData.size(); i++) {
            sortItemViewLists.get(i).setData(mData.get(i).getTabs(), checkedIndex.get(i));
        }
        ll_content.setVisibility(View.VISIBLE);
        if (checkedIndex != null && checkedIndex.size() > 0) {
            setButtonEnabled(true);

        } else {
            setButtonEnabled(false);
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
            List list1 = mData.get(entry.getKey()).getTabs();
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

}

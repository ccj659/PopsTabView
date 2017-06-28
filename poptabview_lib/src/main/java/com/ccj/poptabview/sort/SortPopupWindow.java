package com.ccj.poptabview.sort;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ccj.poptabview.FilterBean;
import com.ccj.poptabview.OnFilterSetListener;
import com.ccj.poptabview.R;
import com.ccj.poptabview.SuperPopWindow;

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


    private View mParentView;
    private View mRootView;//根布局，底部收起按钮,分类选中区域
    private View mLoadingView, ll_content;
    private ViewStub mErrorView;
    private View mInflatedErrorView, iv_collapse;
    private TextView tv_reset, tv_confirm;
    OnFilterSetListener onFilterSetListener;
    int tag;

    private SortItemView sort_channel, sort_date, sort_time, sort_theme, sort_mall;
    private List<FilterBean.CategoryMall> inlandMallList;
    private HashMap<String, String> paramsMap = new HashMap<>();

    public SortPopupWindow(Context context, List data, OnFilterSetListener onFilterSetListener, int tag) {
        mContext = context;
        this.onFilterSetListener = onFilterSetListener;
        this.tag = tag;
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.common_popup_filter_sort, null);

        mLoadingView = mRootView.findViewById(R.id.view_loading);
        ll_content = mRootView.findViewById(R.id.ll_content);
        mErrorView = (ViewStub) mRootView.findViewById(R.id.error);
        tv_reset = (TextView) mRootView.findViewById(R.id.tv_reset);
        tv_confirm = (TextView) mRootView.findViewById(R.id.tv_confirm);
        iv_collapse = mRootView.findViewById(R.id.iv_collapse);

        mInflatedErrorView = null;
        sort_channel = (SortItemView) mRootView.findViewById(R.id.sort_channel);
        sort_date = (SortItemView) mRootView.findViewById(R.id.sort_date);
        sort_time = (SortItemView) mRootView.findViewById(R.id.sort_time);
        sort_theme = (SortItemView) mRootView.findViewById(R.id.sort_theme);
        sort_mall = (SortItemView) mRootView.findViewById(R.id.sort_mall);

        sort_channel.setAdapter(SortItemView.SORT_TYPE_CHANNEL);
        sort_date.setAdapter(SortItemView.SORT_TYPE_DATE);
        sort_time.setAdapter(SortItemView.SORT_TYPE_TIME);
        sort_theme.setAdapter(SortItemView.SORT_TYPE_THEME);
        sort_mall.setAdapter(SortItemView.SORT_TYPE_MALL);

        mRootView.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        iv_collapse.setOnClickListener(this);

        sort_channel.setFilterTagClick(this);
        sort_date.setFilterTagClick(this);
        sort_time.setFilterTagClick(this);
        sort_theme.setFilterTagClick(this);
        sort_mall.setFilterTagClick(this);


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
        if (inlandMallList == null || inlandMallList.size() == 0) {
            loadData();
        } else {
            loadSortItem();
        }
    }

    private void resetView() {
        sort_channel.resetView();
        sort_date.resetView();
        sort_time.resetView();
        sort_theme.resetView();
        sort_mall.resetView();

        setButtonEnabled(false);

    }

    private void loadData() {

        mLoadingView.setVisibility(View.VISIBLE);

    }

    private void loadSortItem() {
        sort_channel.setData(inlandMallList, paramsMap.get("channel"));
        sort_date.setData(inlandMallList, paramsMap.get("date"));
        sort_time.setData(inlandMallList, paramsMap.get("time"));
        sort_theme.setData(inlandMallList, paramsMap.get("theme"));
        sort_mall.setData(inlandMallList, paramsMap.get("mall"));
        ll_content.setVisibility(View.VISIBLE);
        if (paramsMap != null && paramsMap.size() > 0) {
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
        int id = v.getId();
        if (id == R.id.btn_reload) {
            loadData();

        } else if (id == R.id.tv_confirm) {
            if (tv_reset.isEnabled()) {
                onFilterSetListener.onSortFilterSet(getParams());
                this.dismiss();
            } else {
                Toast.makeText(mContext, "请选择筛选条件", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.tv_reset) {
            if (v.isEnabled()) {
                resetView();
                paramsMap.clear();
                loadData();
            }
        } else if (id == R.id.iv_collapse) {
            this.dismiss();
        } else {
            this.dismiss();

        }
    }


    private void setButtonEnabled(boolean enabled) {
        tv_reset.setEnabled(enabled);
        if (enabled) {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(mContext, R.color.product_color));
            tv_confirm.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
        } else {
            tv_confirm.setBackgroundColor(ContextCompat.getColor(mContext, R.color.coloreee));
            tv_confirm.setTextColor(ContextCompat.getColor(mContext, R.color.color666));
        }
    }

    @Override
    public void onComFilterTagClick(int position, String id, String name, String type) {
        // ToastUtil.show(mContext, "onComFilterTagClick-->" + name);
        Toast.makeText(mContext, "onComFilterTagClick-->" + type, Toast.LENGTH_SHORT).show();

        setButtonEnabled(true);
        paramsMap.put(type, id);

    }


    /**
     * 拼参数
     *
     * @return
     */
    public String getParams() {
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.append("&" + entry.getKey() + "=" + entry.getValue());

        }
        return params.toString();


    }


    public View getmParentView() {
        return mParentView;
    }

    public void setmParentView(View mParentView) {
        this.mParentView = mParentView;
    }
}

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
import android.widget.Toast;

import com.ccj.poptabview.R;
import com.ccj.poptabview.SuperPopWindow;
import com.ccj.poptabview.filter.single.FilterTabBean;
import com.ccj.poptabview.listener.OnFilterSetListener;

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
    private View mParentView;
    private View mRootView;//根布局，底部收起按钮,分类选中区域
    private View mLoadingView;
    private LinearLayout ll_content;
    private ViewStub mErrorView;
    private View mInflatedErrorView, iv_collapse;
    private TextView tv_reset, tv_confirm;
    OnFilterSetListener onFilterSetListener;
    int tag;

    private List<FilterTabBean> data = new ArrayList<>();
    private List<SortItemView> sortItemViewLists = new ArrayList<>();

    private HashMap<String, String> paramsMap = new HashMap<>();
    private HashMap<String, String> valueMap = new HashMap<>();

    public SortPopupWindow(Context context, List data, View parentView, OnFilterSetListener onFilterSetListener, int tag) {
        mContext = context;
        this.data = data;
        mParentView = parentView;
        this.onFilterSetListener = onFilterSetListener;
        this.tag = tag;
        initView();

        for (int i = 0; i < data.size(); i++) {
            FilterTabBean filterTabBean = (FilterTabBean) data.get(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            SortItemView sortItemView = new SortItemView(mContext);
            sortItemView.setLayoutParams(layoutParams);
            sortItemView.setLabTitle(filterTabBean.getTab_name());
            sortItemView.setAdapter(filterTabBean.getTab_name());//将getTab_name 作为 唯一标示
            sortItemView.setFilterTagClick(this);
            sortItemViewLists.add(sortItemView);
            ll_content.addView(sortItemView);
        }

    }

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.common_popup_filter_sort, null);

        mLoadingView = mRootView.findViewById(R.id.view_loading);
        ll_content = (LinearLayout) mRootView.findViewById(R.id.ll_content);
        mErrorView = (ViewStub) mRootView.findViewById(R.id.error);
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
       /* if (inlandMallList==null||inlandMallList.size()==0){
            loadData();
        }else {*/
        loadSortItem();
        //    }
    }

    private void resetView() {

        for (int i = 0; i < data.size(); i++) {

            sortItemViewLists.get(i).resetView();

        }

        setButtonEnabled(false);

    }

/*    private void loadData() {

        mLoadingView.setVisibility(View.VISIBLE);
        RequestManager.addRequest(new GsonRequest<>(
                Request.Method.GET,//// "home","","",0
                UrlCat.getFilterListUrl("", "home", "", "", "", 0),
                FilterBean.class, null, null,
                new Response.Listener<FilterBean>() {
                    @Override
                    public void onResponse(FilterBean response) {
                        mLoadingView.setVisibility(View.GONE);
                        if (response != null && response.getData() != null && response.getError_code() == 0) {
                            inlandMallList = response.getData().getMall().getGuonei();
                            hideErrorView();
                            loadSortItem();
                        } else {
                            showErrorView();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mLoadingView.setVisibility(View.GONE);
                        showErrorView();
                        LogUtil.logWrite("filter", error.getMessage());
                    }
                }
        ), this);
    }*/

    private void loadSortItem() {


        for (int i = 0; i < data.size(); i++) {

            sortItemViewLists.get(i).setData(data.get(i).getTabs(), paramsMap.get(data.get(i).getTab_name()));

        }


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
        int i = v.getId();
        if (i == R.id.btn_reload) {//loadData();

        } else if (i == R.id.tv_confirm) {
            if (tv_reset.isEnabled()) {
                onFilterSetListener.onSortFilterSet(getParams(), getValues());
               this.dismiss();
            } else {
                Toast.makeText(mContext,"请选择筛选条件",Toast.LENGTH_SHORT).show();

            }

        } else if (i == R.id.tv_reset) {
            if (v.isEnabled()) {
                resetView();
                paramsMap.clear();
                loadSortItem();
            }

        } else if (i == R.id.iv_collapse) {
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

    /**
     *
     * @param position
     * @param id
     * @param name
     * @param type 1分类 2商城  这里选tab_name
     */
    @Override
    public void onComFilterTagClick(int position, String id, String name, String type) {
        setButtonEnabled(true);
        paramsMap.put(type, id);
        valueMap.put(type, name);


    }


    /**
     * 拼参数
     *
     * @return
     */
    public String getParams() {
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.append(entry.getValue()+",");

        }
        String paramString=params.toString();

        if (paramString.endsWith(",")){
            paramString = paramString.substring(0,paramString.length() - 1);
        }

        return paramString;
    }
    /**
     * 拼参数
     *
     * @return
     */
    public String getValues() {
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {

            params.append(entry.getKey()+"/");
            params.append(entry.getValue()+"_");
        }
        String paramString=params.toString();

        if (paramString.endsWith("_")){
            paramString = paramString.substring(0,paramString.length() - 1);
        }
        return paramString;
    }

}

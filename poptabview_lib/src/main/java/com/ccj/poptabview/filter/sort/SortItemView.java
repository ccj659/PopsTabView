package com.ccj.poptabview.filter.sort;


import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.listener.OnSortItemClickListener;
import com.ccj.poptabview.listener.OnSortTagClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 17/6/22.
 */

public class SortItemView extends LinearLayout {

    private Context context;


    public String SORT_TYPE_NOW = "";//静态变量无论有几个对象,值只有一个~~,被坑了,麻蛋

    private TextView tv_title, tv_empty_border;
    private RecyclerView rv_cat;
    private ImageView iv_expand_border;
    private String title_lable = "";
    private GridLayoutManager mLayoutManager;
    private int SPAN_COUNT = 3;
    private SortFilterAdapter mAdapterInland;

    private boolean isCatExpand = false;///展开状态
    private boolean isMallInlandExpand;
    private OnSortTagClickListener filterTagClick;
    private int index;


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_title_recycle_view, this, true);//通用布局
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_empty_border = (TextView) findViewById(R.id.tv_empty_border);
        rv_cat = (RecyclerView) findViewById(R.id.rv_list_border);
        iv_expand_border = (ImageView) findViewById(R.id.iv_expand_border);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SortItemView);
            SPAN_COUNT = typedArray.getInteger(R.styleable.SortItemView_row_count, 3);
            title_lable = typedArray.getString(R.styleable.SortItemView_lable);

        }

        tv_title.setText(title_lable+"");

        iv_expand_border.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickEvent();
            }
        });

    }

    public void setTAG(String arg){
        SORT_TYPE_NOW=arg;
    }


    public void onClickEvent() {
        iv_expand_border.setClickable(false);
        isMallInlandExpand = !isMallInlandExpand;
        mAdapterInland.setExpand(isMallInlandExpand);
        iv_expand_border.animate().rotation(180 - iv_expand_border.getRotation()).setDuration(200).setInterpolator(new LinearInterpolator()).setListener(new Animator.AnimatorListener() {
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

    public void setFilterTagClick(OnSortTagClickListener filterTagClick) {
        this.filterTagClick = filterTagClick;
    }

    /**
     * filterType 作为唯一标识,区分view类型
     * @param tag
     */
    public void setAdapter(String tag, int singleOrMultiply) {
        setTAG(tag);
        mLayoutManager = new GridLayoutManager(context, SPAN_COUNT);
        mAdapterInland = new SortFilterAdapter(new OnSortItemClickListener() {
            @Override
            public void onSortItemClick(int position, List<Integer> filterTabBeen) {
                filterTagClick.onComFilterTagClick(index,position, (ArrayList<Integer>) filterTabBeen,SORT_TYPE_NOW);
            }
        }, singleOrMultiply);//cat 则为单选

        rv_cat.setLayoutManager(mLayoutManager);
        rv_cat.setAdapter(mAdapterInland);

    }



    public void setData(List data, List index) {

        List<BaseFilterTabBean> inlandMallList = data;
        if (inlandMallList != null && inlandMallList.size() > 0) {
            tv_empty_border.setVisibility(View.GONE);
            mAdapterInland.setData(inlandMallList);
            mAdapterInland.setCheckedList(index);
            rv_cat.setVisibility(View.VISIBLE);
            if (inlandMallList.size() > 6) {
                iv_expand_border.setVisibility(View.VISIBLE);
            } else {
                iv_expand_border.setVisibility(View.GONE);
            }
        } else {
            tv_empty_border.setVisibility(View.VISIBLE);
            rv_cat.setVisibility(View.GONE);
            iv_expand_border.setVisibility(View.GONE);
        }


    }


    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }


    public void resetView() {
        iv_expand_border.animate().rotation(0).start();
        isMallInlandExpand = false;
        rv_cat.setVisibility(View.VISIBLE);
        mAdapterInland.clearChecked();

    }

    public void setCheckedState() {

    }


    public void setContext(Context context) {
        this.context = context;
    }

    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }
    public void setLabTitle(String tv_title) {
        this.tv_title.setText(tv_title);
    }
    public TextView getTv_empty_border() {
        return tv_empty_border;
    }

    public void setTv_empty_border(TextView tv_empty_border) {
        this.tv_empty_border = tv_empty_border;
    }

    public RecyclerView getRv_list_border() {
        return rv_cat;
    }

    public void setRv_list_border(RecyclerView rv_list_border) {
        this.rv_cat = rv_list_border;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    public SortItemView(Context context) {
        super(context);
        init(context, null);
    }

    public SortItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }


    public SortItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SortItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }

    public void setIndex(int index) {
        this.index=index;
    }
}

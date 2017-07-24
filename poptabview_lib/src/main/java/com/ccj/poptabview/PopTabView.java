package com.ccj.poptabview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ccj.poptabview.base.SuperPopWindow;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.listener.OnPopTabSetListener;
import com.ccj.poptabview.loader.PopEntityLoader;
import com.ccj.poptabview.loader.PopTypeLoaderImp;
import com.ccj.poptabview.loader.ResultLoader;
import com.ccj.poptabview.loader.ResultLoaderImp;

import java.util.ArrayList;
import java.util.List;

/**
 * popwindow的容器tab
 * Created by chenchangjun on 17/6/20.
 */
public class PopTabView extends LinearLayout implements OnMultipeFilterSetListener, OnDismissListener {

    //自定义属性,待扩展
    private int mToggleBtnBackground;
    private int mToggleTextColor;
    private float mToggleTextSize;

    private ArrayList<SuperPopWindow> mViewLists = new ArrayList<>();//popwindow缓存集合
    private ArrayList<TextView> mTextViewLists = new ArrayList<TextView>(); //筛选标签textiew集合,用于字段展示和点击事件
    private ArrayList<String> mLableLists = new ArrayList<>();//初始筛选展示字符串缓存,用于默认展示

    private OnPopTabSetListener onPopTabSetListener;//PopTabView和activity的,回调


    private PopEntityLoader popEntityLoader;//筛选类型实体加载器
    private ResultLoader resultLoader;//可自定义处理的筛选结果集 处理器


    private int mTabPostion = -1; //记录TAB页号,
    private int currentIndex;//当前点击的下标
    private Context mContext;


    private void init(Context context, AttributeSet attrs) {
   /*     TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.PopsTabView);
            mToggleBtnBackground = a.getResourceId(R.styleable.PopsTabView_itemBackground, -1);
            mToggleTextColor = a.getColor(R.styleable.PopsTabView_itemTextColor, -1);
            mToggleTextSize = a.getDimension(R.styleable.PopsTabView_itemTextSize, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (a != null) {
                a.recycle();
            }
        }*/
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);


    }



    /**
     * @param title 筛选标题
     * @param data  筛选数据
     * @param tag   筛选类别- 一级筛选,二级筛选,复杂筛选
     * @param type  筛选方式-单选or多选
     * @return view 本身
     */
    public PopTabView addFilterItem(String title, List data, int tag, int type) {

        ////默认筛选项的布局,如果想修改筛选项样式,也可以在此布局修改
        View labView = inflate(getContext(), R.layout.item_expand_pop_window, null);
        TextView labButton = (TextView) labView.findViewById(R.id.tv_travel_type);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        labView.setLayoutParams(params);

        //筛选类型实体加载器
        if (popEntityLoader == null) {
            popEntityLoader = new PopTypeLoaderImp();
        }
        SuperPopWindow mPopupWindow = (SuperPopWindow) popEntityLoader.getPopEntity(getContext(), data, this, tag, type);//得到相应的筛选类型实体
        mPopupWindow.setOnDismissListener(this);

        //将筛选项布局加入view
        addView(labView);

        //对筛选项控件进行设置,并且缓存位置信息
        labButton.setText(title);
        labView.setTag(++mTabPostion);
        labView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击时,设置当前选中状态
                currentIndex = (int) v.getTag();
                setMenuDrawble(mTextViewLists.get(currentIndex), true);
                //弹出当前页pop,或者回收pop
                showPopView(currentIndex);
            }
        });
        //进行缓存
        mTextViewLists.add(labButton);
        mLableLists.add(title);
        mViewLists.add(mPopupWindow);
        return this;
    }


    /**
     * 设置箭头方向及文字颜色
     *
     * @param isUp true 设置为向上箭头，文字红色
     */
    private void setMenuDrawble(TextView tv_checked, boolean isUp) {

        if (isUp) {
            tv_checked.setTextColor(ContextCompat.getColor(getContext(), R.color.product_color));
            tv_checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filter_up, 0);
        } else {
            tv_checked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.filter_down, 0);
            tv_checked.setTextColor(ContextCompat.getColor(getContext(), R.color.color666));

        }
    }

    /**
     * 遍历,选取,显示
     *
     * @param position
     */
    public void showPopView(int position) {

        if (mViewLists.size() > position && mViewLists.get(position) != null) {
            //遍历, 将 不是该位置的window消失
            for (int i = 0; i < mViewLists.size(); i++) {
                if (i != position) {
                    mViewLists.get(i).dismiss();
               }
            }
            //如果该位置正在展示,就让他消失.如果没有,就展示
            if (mViewLists.get(position).isShowing()) {
                mViewLists.get(position).dismiss();
            }else {
                mViewLists.get(position).show(this, 0);
            }

        }
    }

/*****************************筛选成功,回调~************************************/

    /**
     * popwindow的ondisms
     */
    @Override
    public void onDismiss() {
        for (int i = 0; i < mTextViewLists.size(); i++) {
            setMenuDrawble(mTextViewLists.get(i), false);
        }
    }


    /**
     * 处理由每个筛选项item到popwindow的数据处理
     *
     * @param selectedList
     */
    private void handleFilterSetData(List<FilterTabBean> selectedList) {
        //如果没有选择,onPopTabSet为null,筛选标签设为初始值;
        if (selectedList == null || selectedList.isEmpty()) {
            mTextViewLists.get(currentIndex).setText(mLableLists.get(currentIndex));
            onPopTabSetListener.onPopTabSet(currentIndex, mLableLists.get(currentIndex), null, null);

        } else { //如果有选择,onPopTabSet 取值,并展示;
            if (resultLoader == null) {
                resultLoader = new ResultLoaderImp();
            }
            //使用 自定义的结果加载器,得到自己想要的字符串结果
            String showValues = (String) resultLoader.getResultShowValues(selectedList);
            String paramsIds = resultLoader.getResultParamsIds(selectedList).toString();
            //展示取值
            mTextViewLists.get(currentIndex).setText(showValues);
            //进行回调
            onPopTabSetListener.onPopTabSet(currentIndex, mLableLists.get(currentIndex), paramsIds, showValues);

        }
    }

    /*****************************筛选成功,回调~************************************/
    /**
     * 一级筛选,连接singlepopwindow,rowspopwindow的筛选回调
     * @param selectedList
     */
    @Override
    public void onMultipeFilterSet(List<FilterTabBean> selectedList) {
        handleFilterSetData(selectedList);
    }
    /**
     * 二级筛选,连接linkedfilterPopwindow的筛选回调
     * @param selectedSecondList 有效筛选结果 是  二级筛选
     */
    @Override
    public void onMultipeSecondFilterSet(int firstPos, List<FilterTabBean> selectedSecondList) {
        handleFilterSetData(selectedSecondList);
    }

    /**
     * sortPopWndow的筛选回调
     * @param selectedList
     */
    @Override
    public void onMultipeSortFilterSet(List<FilterTabBean> selectedList) {
        handleFilterSetData(selectedList);
    }


    @Override
    public void OnMultipeFilterCanceled() {

    }

    /*****************************筛选成功,end~************************************/


    /**
     * 多次加载 注意清空
     */
    public void removeItem() {
        mTextViewLists.clear();
        mLableLists.clear();
        mViewLists.clear();
    }



    public OnPopTabSetListener getOnPopTabSetListener() {
        return onPopTabSetListener;
    }

    public PopTabView setOnPopTabSetListener(OnPopTabSetListener onPopTabSetListener) {
        this.onPopTabSetListener = onPopTabSetListener;
        return this;
    }

    public PopTabView(Context context) {
        super(context);
        init(context, null);
    }

    public PopTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PopTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }


    public ResultLoader getResultLoader() {
        return resultLoader;
    }

    public void setResultLoader(ResultLoader resultLoader) {
        this.resultLoader = resultLoader;
    }


    public PopEntityLoader getPopEntityLoader() {
        return popEntityLoader;
    }

    public PopTabView setPopEntityLoader(PopEntityLoader popEntityLoader) {
        this.popEntityLoader = popEntityLoader;
        return this;
    }

}

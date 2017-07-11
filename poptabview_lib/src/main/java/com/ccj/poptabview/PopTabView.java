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

import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnMultipeFilterSetListener;
import com.ccj.poptabview.loader.PopTypeLoader;
import com.ccj.poptabview.loader.PopTypeLoaderImp;
import com.ccj.poptabview.loader.ResultLoader;
import com.ccj.poptabview.loader.ResultLoaderImp;

import java.util.ArrayList;
import java.util.List;

/**
 * popwindow的容器tab
 * Created by chenchangjun on 17/6/20.
 */
public class PopTabView extends LinearLayout implements OnMultipeFilterSetListener,OnDismissListener {
    private ArrayList<SuperPopWindow> mViewLists = new ArrayList<>();
    private ArrayList<TextView> mTextViewLists = new ArrayList<TextView>();
    private ArrayList<String> mLableLists = new ArrayList<>();

    private ArrayList<Integer> mTagLists = new ArrayList<>();

    private OnPopTabSetListener onPopTabSetListener;


    private PopTypeLoader popEntityLoader;
    private  ResultLoader resultLoader;

    public ResultLoader getResultLoader() {
        return resultLoader;
    }

    public void setResultLoader(ResultLoader resultLoader) {
        this.resultLoader = resultLoader;
    }




    private Context mContext;
    private int mTabPostion = -1; //记录TAB页号
    private int index;



    private void init(Context context, AttributeSet attrs) {
    /*    TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.PopTabView);
            mToggleBtnBackground = a.getResourceId(R.styleable.PopTabView_tab_toggle_btn_bg, -1);
            mToggleBtnBackgroundColor = a.getColor(R.styleable.PopTabView_tab_toggle_btn_color, getResources().getColor(R.color.white));
            mToggleTextColor = a.getColor(R.styleable.PopTabView_tab_toggle_btn_font_color, -1);
            mPopViewBackgroundColor = a.getColor(R.styleable.PopTabView_tab_pop_bg_color, getResources().getColor(R.color.white));
            mToggleTextSize = a.getDimension(R.styleable.PopTabView_tab_toggle_btn_font_size, -1);
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
     * 多次加载 注意清空
     */
    public void removeItem() {
        mTextViewLists.clear();
        mLableLists.clear();
        mViewLists.clear();
        mTagLists.clear();
    }


    /**
     *
     * @param title 筛选标题
     * @param data 筛选数据
     * @param tag 筛选类别- 一级筛选,二级筛选,复杂筛选
     * @param type 筛选方式-单选or多选
     * @return view 本身
     */
    public PopTabView addFilterItem(String title, List data, int tag,int type ) {

        View labView = inflate(getContext(), R.layout.item_expand_pop_window, null);
        TextView labButton = (TextView) labView.findViewById(R.id.tv_travel_type);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        labView.setLayoutParams(params);

        if (popEntityLoader==null){
            popEntityLoader=new PopTypeLoaderImp();
        }
        SuperPopWindow mPopupWindow = (SuperPopWindow) popEntityLoader.getPopEntity(getContext(), data, this, tag,type);

        mPopupWindow.setOnDismissListener(this);
        addView(labView);
        labButton.setText(title);
        labView.setTag(++mTabPostion);
        labView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (int) v.getTag();
                setMenuDrawble(mTextViewLists.get(index), true);
                showPopView(index);
            }
        });

        mTextViewLists.add(labButton);
        mLableLists.add(title);
        mViewLists.add(mPopupWindow);
        mTagLists.add(tag);
        return this;
    }


    /**
     * 设置箭头方向及文字颜色
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
     * @param position
     */
    public void showPopView(int position) {


        if (mViewLists.size() > position && mViewLists.get(position) != null) {

            for (int i = 0; i < mViewLists.size(); i++) {
                if (i != position) {
                    mViewLists.get(position).dismiss();
                }
            }

            if (mViewLists.get(position).isShowing()) {
                mViewLists.get(position).dismiss();
            } else {
                mViewLists.get(position).show(this,0);
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



    private void handleFilterSetData(List<FilterTabBean> selectedList) {
        if (selectedList==null||selectedList.isEmpty()){
            mTextViewLists.get(index).setText(mLableLists.get(index));
            onPopTabSetListener.onPopTabSet(index,mLableLists.get(index), null,null);

        }else {
            if (resultLoader==null){
                resultLoader=new ResultLoaderImp();
            }
            String showValues= (String) resultLoader.getResultShowValues(selectedList);
            String paramsIds=resultLoader.getResultParamsIds(selectedList).toString();
            mTextViewLists.get(index).setText(showValues);
            onPopTabSetListener.onPopTabSet(index,mLableLists.get(index), paramsIds,showValues);

        }
    }

    /*****************************筛选成功,回调~************************************/

    @Override
    public void onMultipeFilterSet(List<FilterTabBean> selectedList) {
        handleFilterSetData(selectedList);
    }


    @Override
    public void onMultipeSecondFilterSet(int firstPos, List<FilterTabBean> selectedSecondList) {
        handleFilterSetData(selectedSecondList);
    }

    @Override
    public void onMultipeSortFilterSet(List<FilterTabBean> selectedList) {

        handleFilterSetData(selectedList);

    }

    @Override
    public void OnMultipeFilterCanceled() {

    }


    /**
     * ExpandPopTabView筛选器外部,回调
     */
    public interface OnPopTabSetListener {
        /**
         *
         * @param index  操作的 filter的下标号 0.1.2.3
         * @param lable 操作的 filter的对应的标签title
         * @param params 选中的 参数(需要传参)
         * @param value  选中的 值
         */
        void onPopTabSet(int index, String lable,String params, String value);
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

    public PopTabView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PopTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }




    public PopTypeLoader getPopEntityLoader() {
        return popEntityLoader;
    }

    public PopTabView setPopEntityLoader(PopTypeLoader popEntityLoader) {
        this.popEntityLoader = popEntityLoader;
        return this;
    }

}



## 自定义样式


### 1.筛选tab样式

目前可以自定义的样式,包括所有LinearLayout所有的自定义以及以下自定义.

```
        可自定义属性
        app:tab_text_color_normal="@color/colorAccent" <!--tab字体正常颜色-->
        app:tab_text_color_focus="@color/colorPrimary" <!--tab字体选中颜色-->
        app:tab_background_normal="@color/cardview_dark_background"<!--tab正常背景-->
        app:tab_background_focus="@color/color48"<!--tab选中背景-->
        app:tab_textsize="6dp" <!--tab字体大小-->
        app:tab_pop_anim="@anim/pop_anim"  <!--tab字体大小-->

```

### 2.筛选列表样式

首先,
如果仅仅是修改样式,直接选择分别新建`MyFilterPopWindow`继承 `SuperPopWindow的子类`(例如SingleFilterWindow),   `MyFilterAdapter`继承`SuperAdapter`,并实现关键方法即可.


#### 已知父类

在筛选列的样式中.

1.`SuperPopWindow` 负责逻辑和交互

2.`SuperAdapter` 负责样式和数据绑定

3.`SuperListener` 负责自定义扩展监听



#### 1.进行自定义继承

1.首先继承`superListener` 构建自己的筛选监听,但也可以用listener包下的`listener`.我在这里用`OnSingleItemClickListener`.



2.构建`MyFilterPopWindow`.

```

/**
	自定义 继承关系
 * Created by chenchangjun on 17/8/25.
 */

public class MyFilterPopWindow extends SingleFilterWindow {


    /**
     * 重写父类构造方法,如果需要其他参数请在本类中定义
     * @param context
     * @param data
     * @param listener
     * @param filterType
     * @param singleOrMutiply
     */
    public MyFilterPopWindow(Context context, List data, OnFilterSetListener listener, int filterType, int singleOrMutiply) {
        super(context,data,listener,filterType,singleOrMutiply);
    }

    /**
     * 重写setAdapter 方法,返回自己的adapter
     * @return
     */
    @Override
    public SuperAdapter setAdapter() {
        return new MyFilterAdapter(getData(), this, getSingleOrMultiply());

    }
}


```


3.构建`adapter`.

```

/**
 * 单栏筛选adapter
 *
 * @author ccj on 17/3/23.
 */


public class MyFilterAdapter extends SuperAdapter {

    /**
     * view和holder构建入口
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_filter, parent, false);//可以根据自己的布局,进行修改
        return new MyFilterViewHolder(v, this);
    }


    public MyFilterAdapter(List<BaseFilterTabBean> beanList, SuperListener listener, int single2mutiple) {
        super(beanList, listener, single2mutiple);
    }

    /**
     * 这里~由于需求可能变更,这里开放监听, listener 需要进行强转,转成自己的listener, 当然也可以用listener包下的监听
     * @param position
     */
    @Override
    public void onFilterItemClick(int position) {
        ((OnSingleItemClickListener) getListener()).onSingleItemClickListener(getCheckedLists());//强转
    }


    /**
     * 样式选择入口
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyFilterViewHolder viewHolder = (MyFilterViewHolder) holder;
        viewHolder.tv_filter.setText(getData().get(position).getTab_name());
        //根据自己的样式修改
        if (getCheckedLists().contains(position)) {
            viewHolder.tv_filter.setSelected(true);
        } else {
            viewHolder.tv_filter.setSelected(false);
        }
    }

    /**
     * 自己的holder样式
     */
    //根据自己的样式修改
    public static class MyFilterViewHolder extends SuperFilterViewHolder {

        private TextView tv_filter;

        public MyFilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView, listener);
            tv_filter = (TextView) itemView.findViewById(R.id.textView);
        }

    }
}


```

#### 2.新建 `MyFilterConfig` 增加样式

```

public class MyFilterConfig extends FilterConfig {


    //自定义popwindow类型
    public final static int TYPE_POPWINDOW_MY = 4; //自定义的类型


}

```

#### 3.新建`MyPopEntityLoaderImp` 重新构建映射关系

把自定义的`MyFilterPopWindow`添加进去即可


```

/**
 * 由筛选器类型 建立实体 的loader
 * Created by chenchangjun on 17/6/28.
 */

public class MyPopEntityLoaderImp implements PopEntityLoader {

    /**
     * 由 getPopType 得到不同的类型的filter实体
     * @param context
     * @param data
     * @param filterSetListener 监听
     * @param filterType 筛选品类
     * @param singleOrMultiply 筛选方式--单选 or  多选
     * @return
     */
    @Override
    public PopupWindow getPopEntity(Context context, List data, OnFilterSetListener filterSetListener, int filterType, int singleOrMultiply) {
        PopupWindow popupWindow = null;
        switch (filterType) {
           //省略其他样式
           case MyFilterConfig.TYPE_POPWINDOW_MY:
                popupWindow = new MyFilterPopWindow(context, data, filterSetListener,filterType,singleOrMultiply);//自定义
                break;

            default:
                popupWindow = new SingleFilterWindow(context, data, filterSetListener,filterType,singleOrMultiply);
                break;
        }
        return popupWindow;
    }
}

```


#### 4.直接构建调用

```
   private void addMyMethod() {

        FilterGroup filterGroup1 = getMyData("筛选1", MyFilterConfig.TYPE_POPWINDOW_ROWS,MyFilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup2 = getMyData("筛选2", MyFilterConfig.TYPE_POPWINDOW_LINKED,MyFilterConfig.FILTER_TYPE_MUTIFY);
        FilterGroup filterGroup3 = getMyData("筛选3", MyFilterConfig.TYPE_POPWINDOW_SINGLE,MyFilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup4 = getMyData("筛选4", MyFilterConfig.TYPE_POPWINDOW_SORT,MyFilterConfig.FILTER_TYPE_MUTIFY);
        FilterGroup filterGroup5 = getMyData("自定义", MyFilterConfig.TYPE_POPWINDOW_MY,MyFilterConfig.FILTER_TYPE_MUTIFY);//自定义


        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new MyPopEntityLoaderImp()).setResultLoader(new MyResultLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem(filterGroup1.getTab_group_name(), filterGroup1.getFilter_tab(), filterGroup1.getTab_group_type(), filterGroup1.getSingle_or_mutiply())
                .addFilterItem(filterGroup2.getTab_group_name(), filterGroup2.getFilter_tab(), filterGroup2.getTab_group_type(), filterGroup2.getSingle_or_mutiply())
                .addFilterItem(filterGroup3.getTab_group_name(), filterGroup3.getFilter_tab(), filterGroup3.getTab_group_type(), filterGroup3.getSingle_or_mutiply())
                .addFilterItem(filterGroup4.getTab_group_name(), filterGroup4.getFilter_tab(), filterGroup4.getTab_group_type(), filterGroup4.getSingle_or_mutiply())
                .addFilterItem(filterGroup5.getTab_group_name(), filterGroup5.getFilter_tab(), filterGroup5.getTab_group_type(), filterGroup5.getSingle_or_mutiply());

    }

```



## 总结

由于每个筛选器,都可能有自己的样式,用户可以根据我的源码自己继承,进行样式扩展即可.
也可以,直接下载源码,在源码的基础上直接修改.逻辑不用修改,只需要修改,adapter中的 不同样式下的状态即可


以上

PopsTabView
====

**PopsTabView是个filter容器,他可以快速,构建不同筛选样式,自由组合成一组tab.**

目前版本,支持 **单列单选**,**双列单选**,**复杂筛选**.后续筛选会不断完善补充.

Show
-------

![两个筛选菜单](https://github.com/ccj659/PopsTabView/blob/master/popsTabview_gif_0.gif)

![四个筛选菜单](https://github.com/ccj659/PopsTabView/blob/master/popsTabview_gif_1.gif)


Introduction
-------

用户只需要,知道自己 的filter 需要哪种filter,然后将数据进行转化,最后自己在`onPopTabSet()`回调,即可使用.


**优点**:


- 支持快速,构建不同筛选样式,自由组合成一组filter的tab.

- 支持自定义filter的顺序,选择样式.
- 解决Android版本兼容(解决popwindow显示位置偏差).
- 用接口抽象出 可配置的 的配置器loader,和功能代码解耦.
- 可以自由扩展,其他类型的Filter类型.

**待完善**:
- 增加其他类型的筛选样式
- 回调参数,需待调整
- view的样式可配置为可自定义
- 代码冗余还需优化.


TO USE
-------

**1.用业务类型 区分,筛选器类型. 将`PopTypeLoader`暴露,用于用户 自定义业务线,以及对应的筛选器类型. 需要自己按照该模式进行扩展.**

```java

public class PopTypeLoaderImpl implements PopTypeLoader {

    @Override
    public int getPopType(int tag) {
        //旅游全部筛选位置顺序
        int type = 0;
        switch (tag) {
            case FilterConfig.LVYOU_FILTER_POSITION_TO:
                type=FilterConfig.TYPE_POPWINDOW_LINKED;
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_FILTER:
                type=FilterConfig.TYPE_POPWINDOW_SORT;
                break;
            default: //大部分都是单选
                type=FilterConfig.TYPE_POPWINDOW_SINGLE;
                break;
        }
        return type;
    }
}

```



**2.使用方式 Builder模式,完成筛选器的创建.**

```java

       popTabView = (PopTabView) findViewById(R.id.expandpop);

       popTabView.setOnPopTabSetListener(this)
                .setPopTypeLoader(new PopTypeLoaderImpl())
                .addFilterItem( "筛选1", getData(), FilterConfig.LVYOU_FILTER_POSITION_FROME)
                .addFilterItem( "筛选2", getData(), FilterConfig.LVYOU_FILTER_POSITION_TO)
                .addFilterItem( "筛选3", getData(), FilterConfig.LVYOU_FILTER_POSITION_TYPE)
                .addFilterItem( "筛选4", getSortData(), FilterConfig.LVYOU_FILTER_POSITION_FILTER);


```
**3.成功的回调,可配置为借口传参.此处回调,可以自主修改,扩展.**

```java
  /**
     * filter根据回调,得到的参数集合,以及选中名称
     * @param tag 业务类型
     * @param params 需要给接口的传参
     * @param value //要显示的值
     */
    @Override
    public void onPopTabSet(int tag, String params, String value) {
        Toast.makeText(this,"tag=" + tag + "&params=" + params,Toast.LENGTH_SHORT).show();
        switch (tag) {
            case FilterConfig.LVYOU_FILTER_POSITION_FROME:
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_TO:
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_TYPE:
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_FILTER:
                break;
            //出行必备
            case FilterConfig.LVYOU_FILTER_POSITION_TRAVEL_COUNTRY:
                break;
            case FilterConfig.LVYOU_FILTER_POSITION_TRAVEL_TYPE:
                break;
        }

```


**4.用`PopEntityLoader` 创建 具体 popwindow 实体对象. 创建对象和 功能代码解耦和,细节在`PopTabView.addItem()`中.若有需要,需要自由扩展,配置.**

```java
public class PopEntityLoaderImp implements PopEntityLoader {


    @Override
    public PopupWindow getPopEntity(Context context, List data, OnFilterSetListener filterSetListener, int tag) {

        PopupWindow popupWindow = null;
        switch (tag) {
            case FilterConfig.TYPE_POPWINDOW_SINGLE:
                popupWindow = new SingleFilterWindow(context, data, filterSetListener, tag);
                break;
            case FilterConfig.TYPE_POPWINDOW_LINKED:
                popupWindow = new LinkFilterPopupWindow(context, data, filterSetListener, tag);
                break;
            case FilterConfig.TYPE_POPWINDOW_SORT:
                popupWindow = new SortPopupWindow(context, data, filterSetListener, tag);
                break;
            default:
                popupWindow = new SingleFilterWindow(context, data, filterSetListener, tag);
                break;
        }
        return popupWindow;
    }

}

```





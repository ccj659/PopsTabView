# PopsTabView
----


**PopsTabView是个filter容器,他可以自动,快速,构建不同筛选样式,自由组合成一组tab.**

目前版本,支持 **单列单选**,**双列单选**,**复杂筛选**.后续筛选会不断完善补充.

[项目地址传送门 https://github.com/ccj659/PopsTabView](https://github.com/ccj659/PopsTabView)

# Show
-----
图片如果显示不了,请到 GitHub 查看


![两个筛选菜单](https://github.com/ccj659/PopsTabView/blob/master/popsTabview_gif_0.gif)

![四个筛选菜单](https://github.com/ccj659/PopsTabView/blob/master/popsTabview_gif_1.gif)



# Introduction
----

用户只需要,知道自己 的filter 需要哪种filter,然后将数据进行转化,最后自己在`onPopTabSet()`回调,即可使用,简单粗暴.


## 优点:

- 支持用`for()循环`全自动配置,自动记住位置,并在点击时,返回位置以及选取值.
- 支持快速,构建不同筛选样式,自由组合成一组filter的tab.
- 支持自定义filter的顺序,选择样式.
- 解决Android版本兼容(解决popwindow显示位置偏差).
- 用接口抽象出 可配置的 的配置器loader,和功能代码解耦.
- 可以自由扩展,其他类型的Filter类型.

## 待完善:
- 增加其他类型的筛选样式
- 回调参数,需待调整
- view的样式可配置为可自定义
- 代码冗余还需优化.


# TO USE
----

## 1.设定,筛选器类型. 将`PopTypeLoader`暴露,用于用户 筛选器类型.
---

**需要自己按照该模式进行扩展.创建 具体 popwindow 实体对象. 创建对象和 功能代码解耦和,细节在`PopTabView.addItem()`中.若有需要,需要自由扩展,配置.**


```java

public class PopTypeLoaderImp implements PopTypeLoader {

    @Override
    public PopupWindow getPopEntity(Context context,  List data, OnFilterSetListener filterSetListener, int tag) {
        PopupWindow popupWindow = null;
        switch (tag) {
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



## 2.使用方式 
---

### 2.1 Builder模式,完成筛选器的创建.
```java

  popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopTypeLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem("筛选1", singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type())
                .addFilterItem("筛选2", linkFilterList.getFilter_tab(), linkFilterList.getTab_group_type())
                .addFilterItem("筛选3", singleFilterList2.getFilter_tab(), singleFilterList2.getTab_group_type())
                .addFilterItem("筛选4", sortFilterList.getFilter_tab(), sortFilterList.getTab_group_type());

```
### 2.2 `for()循环`全自动配置模式,完成筛选器的创建.

```java
    for (int i = 0; i < 5; i++) {
            //popTabView.addFilterItem()
            popTabView.addFilterItem("筛选"+i, singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type());
        }

```


## 3.成功的回调,可配置为借口传参.此处回调,可以自主修改,扩展.
---
```java
   /**
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable  操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    @Override
    public void onPopTabSet(int index, String lable, String params, String value) {
        // Toast.makeText(this, "tag=" + index + "&params=" + params, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "lable=" + index + "&value=" + value, Toast.LENGTH_SHORT).show();


    }
```



# about me
---

CSDN : http://blog.csdn.net/ccj659/article/

简书 :http://www.jianshu.com/u/94423b4ef5cf

github: https://github.com/ccj659/



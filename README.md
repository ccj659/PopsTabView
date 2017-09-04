# PopsTabView

[![Download](https://img.shields.io/badge/DownloadApp-1.9M-ff69b4.svg) ](https://github.com/ccj659/PopsTabView/blob/master/app-debug.apk)
![](https://img.shields.io/travis/rust-lang/rust/master.svg)
 [![Download](https://api.bintray.com/packages/ccj659/maven/PopsTabView/images/download.svg) ](https://bintray.com/ccj659/maven/PopsTabView/_latestVersion)
[![Author](https://img.shields.io/badge/autor-ccj659-brightgreen.svg)](https://github.com/ccj659)
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)


**PopsTabView是个filter容器,他可以自动,快速,构建不同筛选样式,自由组合成一组tab.**







筛选样式 | 筛选种类 |可自定义属性
--------|------|--------------|
单列 | 单选,多选  | 初始数据bean,筛选结果bean,tab样式,筛选样式|
多排 | 单选,多选  | 初始数据bean,筛选结果beantab样式,筛选样式|
双列 | 单项单选,单项多选  | 初始数据bean,筛选结果bean,tab样式,筛选样式|
复杂 | 单项单选,单项多选  | 初始数据bean,筛选结果bean,tab样式,筛选样式|
自定义 | 单项单选,单项多选  | 初始数据bean,筛选结果bean,tab样式,筛选样式|

详情请参考代码,以及实例
后续筛选会不断完善补充.

**`PopTabView.java` API**

方法名| 功能 |是否必须
--------|------|--------------|
`setOnPopTabSetListener()` | 外部回调的监听  | 是|
`setPopEntityLoader()` | 筛选样式加载类,新增类型可在此扩展  | 否|
`setResultLoader()` | 结果集加载器,可自定义进行配置  | 是|
`addFilterItem()` | 增加筛选项  | 是|
`setClickedItem()` | 设置默认选中的状态,并主动回调  | 否|
`removeItem()` | 清空容器  | 否|



[项目地址传送门 https://github.com/ccj659/PopsTabView](https://github.com/ccj659/PopsTabView)

## 进阶

- [支持自定义参数传送门](https://github.com/ccj659/PopsTabView/README_RESULT.md)

- [支持自定义样式传送门](https://github.com/ccj659/PopsTabView/README_STYLE.md)


## Show


<h1 align="center">
<img src="http://upload-images.jianshu.io/upload_images/1848340-88046dae08e39b02.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="两个筛选菜单"/>
<img src="http://upload-images.jianshu.io/upload_images/1848340-fabaade294df8d25.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="四个筛选菜单"/>
<br/>
</h1>

<h1 align="center">
<img src="http://upload-images.jianshu.io/upload_images/1848340-833c54f734a4baab.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="两个筛选菜单"/>
<img src="http://upload-images.jianshu.io/upload_images/1848340-ab654092afc2d7ed.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="四个筛选菜单"/>
<br/>
</h1>



## Introduction

用户只需要,知道自己需要哪种filter,将数据转化`FilterTabBean`,然后`addFilterItem()`,最后自己在`onPopTabSet()`回调,即可使用,简单粗暴.


### 优点:

- 支持快速,构建不同筛选样式,顺序,自由组合成一组filter的tab.
- 用接口抽象出filter样式配置器loader,与功能代码解耦.
- 支持自定义配置 筛选结果`ResultLoader<T>`
- 支持自定义数据bean.
- 可以自由扩展,其他类型的Filter类型.

### 待完善:
- 逐渐增加其他类型的筛选样式
- view的样式可配置为可自定义


## TO USE

### 添加依赖

**In Gradle**

```
compile 'me.ccj.PopsTabView:poptabview_lib:1.3.1'

```

**In Maven**

```
<dependency>
  <groupId>me.ccj.PopsTabView</groupId>
  <artifactId>poptabview_lib</artifactId>
  <version>1.3.1</version>
  <type>pom</type>
</dependency>

```






### 简单方式

如果业务需求很简单,用lib自带的筛选即可.

#### 1.设定,筛选器类型. 将`PopTypeLoader`暴露,用于用户 筛选器类型.

**需要自己按照该模式进行扩展.创建 具体 popwindow 实体对象. 创建对象和 功能代码解耦和,细节在`PopTabView.addItem()`中.若有需要,需要自由扩展,配置.**


```java

public class PopTypeLoaderImp implements PopTypeLoader {
    @Override
    public PopupWindow getPopEntity(Context context, List data, OnMultipeFilterSetListener filterSetListener, int tag, int type) {
        PopupWindow popupWindow = null;
        switch (tag) {
            case FilterConfig.TYPE_POPWINDOW_LINKED:
                popupWindow = new LinkFilterPopupWindow(context, data, filterSetListener,type);
                break;
            case FilterConfig.TYPE_POPWINDOW_SORT:
                popupWindow = new SortPopupWindow(context, data, filterSetListener, tag,type);
                break;
            default:
                popupWindow = new MSingleFilterWindow(context, data, filterSetListener,type);
                break;
        }
        return popupWindow;
    }
}

```



#### 2.使用方式 

####2.1 Builder模式,完成筛选器的创建.
```java


    private void addMyMethod() {

        FilterGroup filterGroup1 = getMyData("筛选1", FilterConfig.TYPE_POPWINDOW_ROWS,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup2 = getMyData("筛选2", FilterConfig.TYPE_POPWINDOW_LINKED,FilterConfig.FILTER_TYPE_MUTIFY);
        FilterGroup filterGroup3 = getMyData("筛选3", FilterConfig.TYPE_POPWINDOW_SINGLE,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup4 = getMyData("筛选4", FilterConfig.TYPE_POPWINDOW_SORT,FilterConfig.FILTER_TYPE_MUTIFY);


        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopEntityLoaderImp()).setResultLoader(new ResultLoaderImp()) //配置 {筛选类型}  方式
                  
             /***
             * @param title 筛选标题
             * @param data 筛选数据
             * @param tag 筛选类别- 一级筛选,二级筛选,复杂筛选
             * @param type 筛选方式-单选or多选
             * @return
             */
                .addFilterItem(filterGroup1.getTab_group_name(), filterGroup1.getFilter_tab(), filterGroup1.getTab_group_type(), filterGroup1.getSingle_or_mutiply())
                .addFilterItem(filterGroup2.getTab_group_name(), filterGroup2.getFilter_tab(), filterGroup2.getTab_group_type(), filterGroup2.getSingle_or_mutiply())
                .addFilterItem(filterGroup3.getTab_group_name(), filterGroup3.getFilter_tab(), filterGroup3.getTab_group_type(), filterGroup3.getSingle_or_mutiply())
                .addFilterItem(filterGroup4.getTab_group_name(), filterGroup4.getFilter_tab(), filterGroup4.getTab_group_type(), filterGroup4.getSingle_or_mutiply());

    }

```


#### 3.配置筛选后的返回值样式`ResultLoader<T>`


```java


/**
 * 如果 遇到复杂的 业务需求, 只需要在这里,对筛选结果进行构建即可.
 * Created by chenchangjun on 17/7/25.
 */

public class ResultLoaderImp implements ResultLoader<String> {


    @Override
    public String getResultParamsIds(List<BaseFilterTabBean> selectedList, int filterType) {
        StringBuilder stringValues = new StringBuilder();
        for (int i = 0; i < selectedList.size(); i++) {
            FilterTabBean filterTabBean= (FilterTabBean) selectedList.get(i);
            stringValues.append( filterTabBean.getTab_id()+ ",");
        }
        return PopsTabUtils.builderToString(stringValues);
    }

    @Override
    public String getResultShowValues(List<BaseFilterTabBean> selectedList, int filterType) {

        StringBuilder stringValues = new StringBuilder();
        for (int i = 0; i < selectedList.size(); i++) {
            stringValues.append(selectedList.get(i).getTab_name() + ",");
        }
        return PopsTabUtils.builderToString(stringValues);
    }

```


#### 4.成功的回调,可配置为借口传参.此处回调,可以自主修改,扩展.

```java
 /**
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable  操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    @Override
    public void onPopTabSet(int index, String lable, String params, String value) {

        Toast.makeText(this, "lable=" + index + "\n&value=" + value, Toast.LENGTH_SHORT).show();
        tv_content.setText("&筛选项=" + index + "\n&筛选传参=" + params + "\n&筛选值=" + value);

    }
```


## 进阶用法

- [自定义参数传送门](https://github.com/ccj659/PopsTabView/README_RESULT.md)

- [自定义样式传送门](https://github.com/ccj659/PopsTabView/README_STYLE.md)


## 样式调整--待优化

1.可在各级`Adapter.ViewHolder` 中自己定义.

2.可在`xml`文件中自定义修改

3.O__O "….....好吧,还是我太懒了....以后会补充的~~



## About Me
===
[CSDN：http://blog.csdn.net/ccj659/article/](http://blog.csdn.net/ccj659/article/)

[简书：http://www.jianshu.com/u/94423b4ef5cf](http://www.jianshu.com/u/94423b4ef5cf)

[github:  https//github.com/ccj659/](https//github.com/ccj659/)




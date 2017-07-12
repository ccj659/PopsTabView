#PopsTabView
===

**PopsTabView是个filter容器,他可以自动,快速,构建不同筛选样式,自由组合成一组tab.**
---
**目前版本`v1.2`**

筛选样式 | 筛选种类 
--------|------|
单列 | 单选,多选  | 
多排 | 单选,多选  | 
双列 | 单项单选,单项多选  | 
复杂 | 单项单选,单项多选  | 


后续筛选会不断完善补充.

[项目地址传送门 https://github.com/ccj659/PopsTabView](https://github.com/ccj659/PopsTabView)

#Show
===


<h1 align="center">


<img src="http://upload-images.jianshu.io/upload_images/1848340-fabaade294df8d25.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="两栏"/>
</h1>




<h1 align="center">
<img src="http://upload-images.jianshu.io/upload_images/1848340-c032ce82532df91a.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="四栏"/>

<img src="http://upload-images.jianshu.io/upload_images/1848340-ab654092afc2d7ed.gif?imageMogr2/auto-orient/strip" width="280" height="498" alt="更多"/>

</h1>


#Introduction
===
用户只需要,知道自己需要哪种filter,将数据转化`FilterTabBean`,然后`addFilterItem()`,最后自己在`onPopTabSet()`回调,即可使用,简单粗暴.


##优点:

- 支持用`for()循环`全自动配置,自动记住位置,并在点击时,返回位置以及选取值.
- 支持快速,构建不同筛选样式,自由组合成一组filter的tab.
- 支持自定义filter的顺序,选择样式.
- 解决Android版本兼容(解决popwindow显示位置偏差).
- 用接口抽象出filter样式配置器loader,与功能代码解耦.
- 支持自定义配置 筛选过程`ResultLoader<T>`
- 可以自由扩展,其他类型的Filter类型.

##待完善:
- 增加其他类型的筛选样式
- 回调参数,需待调整
- view的样式可配置为可自定义
- 代码冗余还需优化.


#TO USE
===

##1.设定,筛选器类型. 将`PopTypeLoader`暴露,用于用户 筛选器类型.
===
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



##2.使用方式 
===

###2.1 Builder模式,完成筛选器的创建.
```java

        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopTypeLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem("筛选1", singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY)
                .addFilterItem("筛选2", linkFilterList.getFilter_tab(), linkFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY)
                .addFilterItem("筛选3", singleFilterList2.getFilter_tab(), singleFilterList2.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY)
                .addFilterItem("筛选4", sortFilterList.getFilter_tab(), sortFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY);

```
###2.2 `for()循环`全自动配置模式,完成筛选器的创建.

```java
  /**
             *
             * @param title 筛选标题
             * @param data 筛选数据
             * @param tag 筛选类别- 一级筛选,二级筛选,复杂筛选
             * @param type 筛选方式-单选or多选
             * @return
             */
            popTabView.addFilterItem("筛选" + i, singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type(), FilterConfig.FILTER_TYPE_SINGLE);

```

##3.配置筛选后的返回值样式`ResultLoader<T>`
===

```java


public class ResultLoaderImp implements ResultLoader<String> {


    @Override
    public String getResultParamsIds(List<FilterTabBean> selectedList) {


        StringBuilder stringIds =new StringBuilder();

        for (int i = 0; i < selectedList.size(); i++) {
            stringIds.append(selectedList.get(i).getTab_id()+",");
        }

        return  builderToString(stringIds);
    }

    @Override
    public String getResultShowValues(List<FilterTabBean> selectedList) {

        StringBuilder stringValues =new StringBuilder();

        for (int i = 0; i < selectedList.size(); i++) {
            stringValues.append(selectedList.get(i).getTab_name()+",");
        }

        return builderToString(stringValues);
    }

```


##4.成功的回调,可配置为借口传参.此处回调,可以自主修改,扩展.
===
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



##样式调整--待优化
===
1.可在各级`Adapter.ViewHolder` 中自己定义.

2.可在`xml`文件中自定义修改

3.O__O "….....好吧,还是我太懒了....以后会补充的~~



##About Me
===
[CSDN：http://blog.csdn.net/ccj659/article/](http://blog.csdn.net/ccj659/article/)

[简书：http://www.jianshu.com/u/94423b4ef5cf](http://www.jianshu.com/u/94423b4ef5cf)

[github:  https//github.com/ccj659/](https//github.com/ccj659/)


## Licence
 Copyright 2017 ccj659

 Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

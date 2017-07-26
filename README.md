#PopsTabView
===

**PopsTabView是个filter容器,他可以自动,快速,构建不同筛选样式,自由组合成一组tab.**

**目前版本`v1.3` update:7/26 18:05**
---
	
1.抽象SuperPopWindow,抽象Bean.

2.可自定义的筛选,详情见文章末尾**进阶---自定义筛选参数使用**


筛选样式 | 筛选种类 
--------|------|
单列 | 单选,多选  | 
多排 | 单选,多选  | 
双列 | 单项单选,单项多选  | 
复杂 | 单项单选,单项多选  | 

详情请参考代码,以及实例
后续筛选会不断完善补充.

[项目地址传送门 https://github.com/ccj659/PopsTabView](https://github.com/ccj659/PopsTabView)

#Show
===



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
##简单方式
###1.设定,筛选器类型. 将`PopTypeLoader`暴露,用于用户 筛选器类型.
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



###2.使用方式 
===

####2.1 Builder模式,完成筛选器的创建.
```java


    private void addMyMethod() {

        FilterGroup filterGroup1 = getMyData("筛选1", FilterConfig.TYPE_POPWINDOW_ROWS,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup2 = getMyData("筛选2", FilterConfig.TYPE_POPWINDOW_LINKED,FilterConfig.FILTER_TYPE_MUTIFY);
        FilterGroup filterGroup3 = getMyData("筛选3", FilterConfig.TYPE_POPWINDOW_SINGLE,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup4 = getMyData("筛选4", FilterConfig.TYPE_POPWINDOW_SORT,FilterConfig.FILTER_TYPE_MUTIFY);


        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopEntityLoaderImp()).setResultLoader(new ResultLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem(filterGroup1.getTab_group_name(), filterGroup1.getFilter_tab(), filterGroup1.getTab_group_type(), filterGroup1.getSingle_or_mutiply())
                .addFilterItem(filterGroup2.getTab_group_name(), filterGroup2.getFilter_tab(), filterGroup2.getTab_group_type(), filterGroup2.getSingle_or_mutiply())
                .addFilterItem(filterGroup3.getTab_group_name(), filterGroup3.getFilter_tab(), filterGroup3.getTab_group_type(), filterGroup3.getSingle_or_mutiply())
                .addFilterItem(filterGroup4.getTab_group_name(), filterGroup4.getFilter_tab(), filterGroup4.getTab_group_type(), filterGroup4.getSingle_or_mutiply());

    }

```
####2.2 `for()循环`全自动配置模式,完成筛选器的创建.

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

###3.配置筛选后的返回值样式`ResultLoader<T>`
===

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


###4.成功的回调,可配置为借口传参.此处回调,可以自主修改,扩展.
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

##进阶---自定义筛选参数使用
===

###1.建立自己的筛选bean
===
**如果数据字段和上述的有出入,或者接口不愿意配合修改成上述的字段,或者得到的筛选数据不满意,可以选择自定义筛选**


举个例子
**如果想用自己的字段作为展示字段,只需要选择继承`FilterTabBean`,然后重写方法**.设计之美,尽在其中.

```
/**
 * 筛选bean的基类, 约束子类行为
 * Created by chenchangjun on 17/7/26.
 */

public abstract class BaseFilterTabBean {


    /**
     * 必须实现,涉及到adapter显示的值.要return list显示的字段.
     * @return
     */
    public abstract String getTab_name();
    public abstract void setTab_name(String tab_name);

    /**
     * 如果有自己的子菜单,目前只有一级菜单,有二级菜单~
     *
     * @return
     */
    public abstract List<BaseFilterTabBean> getTabs();

    public abstract  void setTabs(List<BaseFilterTabBean> tabs);

}

```


####自定义自己数据bean`MyFilterTabBean`
---


```
public class MyFilterTabBean extends BaseFilterTabBean {

//省略其他get set
    /*情况1---比如,你需要如下字段*/
    protected String show_name;//展示字段
    protected String channel_name;
    protected String category_ids;
    protected String tag_ids;
    protected String mall_ids;
    protected List<BaseFilterTabBean> tabs;

    @Override
    public String getTab_name() {
        return show_name;
    }

    @Override
    public void setTab_name(String tab_name) {
        this.show_name=tab_name;
    }

    @Override
    public List<BaseFilterTabBean> getTabs() {
        return tabs;
    }

    @Override
    public void setTabs(List<BaseFilterTabBean> tabs) {
        this.tabs=tabs;
    }


    public static class MyChildFilterBean extends BaseFilterTabBean {


        /*情况1---比如,你需要如下字段*/
        protected String show_name; //展示字段
        protected String channel_name;
        protected String category_ids;
        protected String tag_ids;
        protected String mall_ids;


        @Override
        public String getTab_name() {
            return show_name;
        }

        @Override
        public void setTab_name(String tab_name) {
            this.show_name=tab_name;
        }

        @Override
        public List<BaseFilterTabBean> getTabs() {
            return null;
        }

        @Override
        public void setTabs(List<BaseFilterTabBean> tabs) {

        }

```


###2.定义自己的筛选结果bean`MyFilterParamsBean `
===


```

public class MyFilterParamsBean implements Serializable {


    protected List<ParamBean> beanList;
    
    public static class ParamBean{

        protected String category_ids;
        protected String tag_ids;
        protected String mall_ids;
	}
}

```


###3.定义自己的结果加载器`MyResultLoaderImp`
===

```
public class MyResultLoaderImp implements ResultLoader<MyFilterParamsBean> {

    @Override
    public MyFilterParamsBean getResultParamsIds(List<BaseFilterTabBean> selectedList, int filterType) {

        //给自己的参数结果集赋值
        MyFilterParamsBean stringIds = new MyFilterParamsBean();
        ArrayList<MyFilterParamsBean.ParamBean> paramBeenList = new ArrayList<>();
        for (int i = 0; i < selectedList.size(); i++) {
            MyFilterParamsBean.ParamBean paramBean = new MyFilterParamsBean.ParamBean();

            MyFilterTabBean filterTabBean = (MyFilterTabBean) selectedList.get(i);//此处需要强转
            paramBean.setCategory_ids(filterTabBean.getCategory_ids());
            paramBean.setMall_ids(filterTabBean.getMall_ids());
            paramBean.setTag_ids(filterTabBean.getTag_ids());

            paramBeenList.add(paramBean);
        }

        stringIds.setBeanList(paramBeenList);


        return stringIds;
    }

    @Override
    public String getResultShowValues(List<BaseFilterTabBean> selectedList, int filterType) {

        StringBuilder stringValues = new StringBuilder();
        for (int i = 0; i < selectedList.size(); i++) {
            stringValues.append(selectedList.get(i).getTab_name() + ",");
        }
        return PopsTabUtils.builderToString(stringValues);
    }
}

```

###4.在调用popstablview处实现 `OnPopTabSetListener<MyFilterParamsBean> `
===

```
 /**
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable  操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    //方式2
    @Override
    public void onPopTabSet(int index, String lable, MyFilterParamsBean params, String value) {
        Toast.makeText(this, "lable=" + index + "\n&value=" + value, Toast.LENGTH_SHORT).show();
        tv_content.setText("&筛选项=" + index + "\n&筛选传参=" + params.getBeanList().toString() + "\n&筛选值=" + value);
    }

```
###5.完成上述之后,直接加载数据,调用
===

```
 /**
     * 模拟数据
     * 筛选器的 数据格式 都是大同小异
     * 要点:泛型处理,集合都用父类,实体都用子类表示.
     * @return
     */
    public FilterGroup getMyData(String groupName, int groupType, int singleOrMutiply ) {

        FilterGroup filterGroup = new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(groupType);
        filterGroup.setSingle_or_mutiply(singleOrMutiply);

        List<BaseFilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            MyFilterTabBean myFilterBean = new MyFilterTabBean();
            myFilterBean.setTab_name(groupName + "_" + i);
            myFilterBean.setTag_ids("tagid" + "_" + i );
            myFilterBean.setMall_ids("mallid" + "_" + i );
            myFilterBean.setCategory_ids("Categoryid" + "_" + i);

            List<BaseFilterTabBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                MyFilterTabBean.MyChildFilterBean myChildFilterBean = new MyFilterTabBean.MyChildFilterBean();
                myChildFilterBean.setTab_name(groupName + "_" + i + "__" + j);
                myChildFilterBean.setTag_ids("tagid" + "_" + i + "__" + j);
                myChildFilterBean.setMall_ids("mallid" + "_" + i + "__" + j);
                myChildFilterBean.setCategory_ids("Categoryid" + "_" + i + "__" + j);

                BaseFilterTabBean secondFilterBean =myChildFilterBean;

                childFilterList.add(secondFilterBean);
            }
            //增加二级tab
            List<BaseFilterTabBean> childFilterList1 = childFilterList;
            myFilterBean.setTabs(childFilterList1);

            //增加一级tab
            singleFilterList.add(myFilterBean);

        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }
    
    
    private void addMyMethod() {

        FilterGroup filterGroup1 = getMyData("筛选1", FilterConfig.TYPE_POPWINDOW_ROWS,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup2 = getMyData("筛选2", FilterConfig.TYPE_POPWINDOW_LINKED,FilterConfig.FILTER_TYPE_MUTIFY);
        FilterGroup filterGroup3 = getMyData("筛选3", FilterConfig.TYPE_POPWINDOW_SINGLE,FilterConfig.FILTER_TYPE_SINGLE);
        FilterGroup filterGroup4 = getMyData("筛选4", FilterConfig.TYPE_POPWINDOW_SORT,FilterConfig.FILTER_TYPE_MUTIFY);


        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopEntityLoaderImp()).setResultLoader(new MyResultLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem(filterGroup1.getTab_group_name(), filterGroup1.getFilter_tab(), filterGroup1.getTab_group_type(), filterGroup1.getSingle_or_mutiply())
                .addFilterItem(filterGroup2.getTab_group_name(), filterGroup2.getFilter_tab(), filterGroup2.getTab_group_type(), filterGroup2.getSingle_or_mutiply())
                .addFilterItem(filterGroup3.getTab_group_name(), filterGroup3.getFilter_tab(), filterGroup3.getTab_group_type(), filterGroup3.getSingle_or_mutiply())
                .addFilterItem(filterGroup4.getTab_group_name(), filterGroup4.getFilter_tab(), filterGroup4.getTab_group_type(), filterGroup4.getSingle_or_mutiply());

    }
   
    
```


###6.结果
===

![popstab_gif_2_1.gif](http://upload-images.jianshu.io/upload_images/1848340-1a54f4f83454cb8a.gif?imageMogr2/auto-orient/strip)




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

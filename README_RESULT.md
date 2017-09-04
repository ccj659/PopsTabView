

### 自定义参数

如果筛选结果,需要其他参数,或者bean中的展示字段不是tab_name,或者得到的筛选数据不满意,可以选择自定义筛选.


####1.建立自己的筛选bean



**举个例子**


**如果想用自己的字段作为展示字段,只需要选择继承`FilterTabBean`,然后重写方法**.设计之美,尽在其中.

**首先看 顶层抽象Bean**

```
/**
 * 筛选bean的基类, 约束子类行为
 * Created by chenchangjun on 17/7/26.
 * 泛型T 泛型T  代表了 子类的类型.解决了 gosn Failed to invoke public **** with no args]
 */

public abstract class BaseFilterTabBean<T extends BaseFilterTabBean> {


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
    public abstract List<T> getTabs();

    public abstract  void setTabs(List<T> tabs);

}
```


 **自定义自己数据bean`MyFilterTabBean`**


```
public class MyFilterTabBean extends BaseFilterTabBean<MyFilterTabBean.MyChildFilterBean> {

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
    public List<MyChildFilterBean> getTabs() {
        return tabs;
    }

    @Override
    public void setTabs(List<MyChildFilterBean> tabs) {
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
        public List getTabs() {
            return null;
        }

        @Override
        public void setTabs(List tabs) {

        }

```


#### 2.定义自己的筛选结果bean`MyFilterParamsBean `



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


#### 3.定义自己的结果加载器`MyResultLoaderImp`


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

#### 4.在调用view处实现 `OnPopTabSetListener<MyFilterParamsBean> `


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
#### 5.完成上述之后,直接加载数据,调用


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


#### 6.结果


![popstab_gif_2_1.gif](http://upload-images.jianshu.io/upload_images/1848340-1a54f4f83454cb8a.gif?imageMogr2/auto-orient/strip)



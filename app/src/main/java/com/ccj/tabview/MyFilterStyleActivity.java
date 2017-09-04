package com.ccj.tabview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ccj.poptabview.PopTabView;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.bean.FilterGroup;
import com.ccj.poptabview.listener.OnPopTabSetListener;
import com.ccj.tabview.mypoptabview.myloader.MyFilterConfig;
import com.ccj.tabview.mypoptabview.myloader.MyFilterParamsBean;
import com.ccj.tabview.mypoptabview.myloader.MyFilterTabBean;
import com.ccj.tabview.mypoptabview.myloader.MyPopEntityLoaderImp;
import com.ccj.tabview.mypoptabview.myloader.MyResultLoaderImp;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义样式
 * Created by chenchangjun on 17/7/26.
 */

public class MyFilterStyleActivity extends AppCompatActivity implements OnPopTabSetListener<MyFilterParamsBean> {


    private PopTabView popTabView;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_style);
        tv_content = (TextView) findViewById(R.id.tv_content);
        popTabView = (PopTabView) findViewById(R.id.expandpop);
        addMyMethod();

    }

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



    /**
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable  操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    //方式2
    @Override
    public void onPopTabSet(int index, String lable, MyFilterParamsBean params, String value) {
        Toast.makeText(this, "lable=" + (index+1) + "\n&value=" + value, Toast.LENGTH_SHORT).show();
        tv_content.setText("&筛选项=" + (index+1) + "\n&筛选传参=" + (params==null||params.getBeanList()==null?null:params.getBeanList().toString()) + "\n&筛选值=" + value);
    }



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
        for (int i = 0; i < 8; i++) {//一级fitler
            MyFilterTabBean myFilterBean = new MyFilterTabBean();
            myFilterBean.setTab_name(groupName + "_" + i);
            myFilterBean.setTag_ids("tagid" + "_" + i );
            myFilterBean.setMall_ids("mallid" + "_" + i );
            myFilterBean.setCategory_ids("Categoryid" + "_" + i);

            List<MyFilterTabBean.MyChildFilterBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {//二级filter
                MyFilterTabBean.MyChildFilterBean myChildFilterBean = new MyFilterTabBean.MyChildFilterBean();
                myChildFilterBean.setTab_name(groupName + "_" + i + "__" + j);
                myChildFilterBean.setTag_ids("tagid" + "_" + i + "__" + j);
                myChildFilterBean.setMall_ids("mallid" + "_" + i + "__" + j);
                myChildFilterBean.setCategory_ids("Categoryid" + "_" + i + "__" + j);

                childFilterList.add(myChildFilterBean);
            }
            //增加二级tab
            myFilterBean.setTabs(childFilterList);

            //增加一级tab
            singleFilterList.add(myFilterBean);

        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }



}

package com.ccj.tabview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.PopTabView;
import com.ccj.poptabview.bean.FilterGroup;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnPopTabSetListener;
import com.ccj.poptabview.loader.PopTypeLoaderImp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnPopTabSetListener {

    private PopTabView popTabView;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_content = (TextView) findViewById(R.id.tv_content);

        popTabView = (PopTabView) findViewById(R.id.expandpop);
        FilterGroup singleFilterList1 = getData("筛选1", FilterConfig.TYPE_POPWINDOW_ROWS);

        FilterGroup linkFilterList = getData("筛选2", FilterConfig.TYPE_POPWINDOW_LINKED);
        FilterGroup singleFilterList2 = getData("筛选3", FilterConfig.TYPE_POPWINDOW_SINGLE);
        FilterGroup sortFilterList = getData("筛选4", FilterConfig.TYPE_POPWINDOW_SORT);

        //测试加载方式1~
        addMethod1(singleFilterList1, linkFilterList, singleFilterList2, sortFilterList);
        //测试加载方式2~螺旋加载
        //addMethod2(singleFilterList1);
    }

    private void addMethod2(FilterGroup singleFilterList1) {
        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopTypeLoaderImp()); //配置 {筛选类型}  方式
        for (int i = 0; i < 5; i++) {
            /**
             *
             * @param title 筛选标题
             * @param data 筛选数据
             * @param tag 筛选类别- 一级筛选,二级筛选,复杂筛选
             * @param type 筛选方式-单选or多选
             * @return
             */
            popTabView.addFilterItem("筛选" + i, singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type(), FilterConfig.FILTER_TYPE_SINGLE);
        }
    }

    private void addMethod1(FilterGroup singleFilterList1, FilterGroup linkFilterList, FilterGroup singleFilterList2, FilterGroup sortFilterList) {
        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopTypeLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem("筛选1", singleFilterList2.getFilter_tab(), singleFilterList2.getTab_group_type(), FilterConfig.FILTER_TYPE_SINGLE)
                .addFilterItem("筛选2", singleFilterList1.getFilter_tab(), singleFilterList1.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY)
                .addFilterItem("筛选3", linkFilterList.getFilter_tab(), linkFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY)
                .addFilterItem("筛选5", linkFilterList.getFilter_tab(), linkFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_SINGLE)
                .addFilterItem("筛选6", sortFilterList.getFilter_tab(), sortFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_SINGLE)
                .addFilterItem("筛选7", sortFilterList.getFilter_tab(), sortFilterList.getTab_group_type(), FilterConfig.FILTER_TYPE_MUTIFY);

    }


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


    /**
     * 模拟数据
     * 筛选器的 数据格式 都是大同小异
     *
     * @return
     */
    public FilterGroup getData(String groupName, int groupType) {

        FilterGroup filterGroup = new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(groupType);


        List<FilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            FilterTabBean singleFilterBean = new FilterTabBean();
            singleFilterBean.setTab_id("id" + "_" + i);
            singleFilterBean.setTab_name(groupName + "_" + i);

            List<FilterTabBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                FilterTabBean secondFilterBean = new FilterTabBean();
                secondFilterBean.setTab_id("id" + "_" + i + "__" + j);
                secondFilterBean.setTab_name(groupName + "_" + i + "__" + j);
                childFilterList.add(secondFilterBean);
            }
            singleFilterBean.setTabs(childFilterList);
            singleFilterList.add(singleFilterBean);
        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }


}

package com.ccj.tabview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.PopTabView;
import com.ccj.poptabview.bean.FilterGroup;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.loader.PopEntityLoaderImp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopTabView.OnPopTabSetListener {

    private PopTabView popTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        popTabView = (PopTabView) findViewById(R.id.expandpop);
        FilterGroup singleFilterList1 = getData("筛选1",FilterConfig.TYPE_POPWINDOW_SINGLE);
        FilterGroup linkFilterList = getData("筛选2",FilterConfig.TYPE_POPWINDOW_LINKED);
        FilterGroup singleFilterList2 = getData("筛选3",FilterConfig.TYPE_POPWINDOW_SINGLE);
        FilterGroup sortFilterList = getData("筛选4",FilterConfig.TYPE_POPWINDOW_SORT);

        popTabView.setOnPopTabSetListener(this)
                .setPopEntityLoader(new PopEntityLoaderImp()) //配置 {筛选类型}  方式
                .addFilterItem("筛选1", singleFilterList1.getFilter_tab(),singleFilterList1.getTab_group_type())
                .addFilterItem("筛选2", linkFilterList.getFilter_tab(), linkFilterList.getTab_group_type())
                .addFilterItem("筛选3", singleFilterList2.getFilter_tab(), singleFilterList2.getTab_group_type())
                .addFilterItem("筛选4", sortFilterList.getFilter_tab(), sortFilterList.getTab_group_type());


        for (int i = 0; i <4 ; i++) {
            //popTabView.addFilterItem()

        }
    }


    /**
     * @param index  操作的 filter的下标号 0.1.2.3
     * @param lable 操作的 filter的对应的标签title
     * @param params 选中的 参数(需要传参)
     * @param value  选中的 值
     */
    @Override
    public void onPopTabSet(int index, String lable,String params, String value) {
       // Toast.makeText(this, "tag=" + index + "&params=" + params, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "lable=" + index + "&value=" + value, Toast.LENGTH_SHORT).show();


    }


    /**
     * 模拟数据
     *  筛选器的 数据格式 都是大同小异
     * @return
     */
    public FilterGroup getData(String groupName,int groupType) {

        FilterGroup filterGroup =new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(groupType);


        List<FilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterTabBean singleFilterBean = new FilterTabBean();
            singleFilterBean.setTab_id(groupName+"_"+i +"_一级id");
            singleFilterBean.setTab_name(groupName+"_"+i +"_级title");

            List<FilterTabBean.TabsBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                FilterTabBean.TabsBean secondFilterBean = new FilterTabBean.TabsBean();
                secondFilterBean.setTab_id(groupName+"_"+i + "-" + j + "二级id");
                secondFilterBean.setTag_name(groupName+"_"+i + "-" + j + "二级title");
                childFilterList.add(secondFilterBean);
            }
            singleFilterBean.setTabs(childFilterList);
            singleFilterList.add(singleFilterBean);
        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }


}

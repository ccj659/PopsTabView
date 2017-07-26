package com.ccj.tabview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.PopTabView;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.bean.FilterGroup;
import com.ccj.poptabview.bean.FilterTabBean;
import com.ccj.poptabview.listener.OnPopTabSetListener;
import com.ccj.poptabview.loader.PopEntityLoaderImp;
import com.ccj.poptabview.loader.ResultLoaderImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 17/7/26.
 */

public class SimpleFilterActivity extends AppCompatActivity implements OnPopTabSetListener<String> {


    private PopTabView popTabView;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_container);
        tv_content = (TextView) findViewById(R.id.tv_content);
        popTabView = (PopTabView) findViewById(R.id.expandpop);
        addMyMethod();

    }

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
    public FilterGroup getMyData(String groupName, int groupType,int singleOrMutiply ) {

        FilterGroup filterGroup = new FilterGroup();

        filterGroup.setTab_group_name(groupName);
        filterGroup.setTab_group_type(groupType);
        filterGroup.setSingle_or_mutiply(singleOrMutiply);


        List<BaseFilterTabBean> singleFilterList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            FilterTabBean singleFilterBean = new FilterTabBean();
            singleFilterBean.setTab_id("id" + "_" + i);
            singleFilterBean.setTab_name(groupName + "_" + i);

            List<BaseFilterTabBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                FilterTabBean.ChildTabBean secondFilterBean = new FilterTabBean.ChildTabBean();
                secondFilterBean.setTab_id("id" + "_" + i + "__" + j);
                secondFilterBean.setTab_name(groupName + "_" + i + "__" + j);
                childFilterList.add(secondFilterBean);//泛型 父类可以容纳子类类型
            }

            singleFilterBean.setTabs(childFilterList);
            singleFilterList.add(singleFilterBean);
        }

        filterGroup.setFilter_tab(singleFilterList);
        return filterGroup;

    }

}

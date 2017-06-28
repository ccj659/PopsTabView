package com.ccj.tabview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ccj.poptabview.bean.FilterBean;
import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.PopTabView;
import com.ccj.poptabview.loader.PopTypeLoaderImpl;
import com.ccj.poptabview.bean.SingleFilterBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  PopTabView.OnPopTabSetListener {

    private PopTabView popTabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        popTabView = (PopTabView) findViewById(R.id.expandpop);


        popTabView.setOnPopTabSetListener(this)
                .setPopTypeLoader(new PopTypeLoaderImpl())
                .addFilterItem( "哈哈1", getData(), FilterConfig.LVYOU_FILTER_POSITION_FROME)
                .addFilterItem( "哈哈2", getData(), FilterConfig.LVYOU_FILTER_POSITION_TO)
                .addFilterItem( "哈哈3", getData(), FilterConfig.LVYOU_FILTER_POSITION_TYPE)
                .addFilterItem("哈哈4", getSortData(), FilterConfig.LVYOU_FILTER_POSITION_FILTER);
    }




    @Override
    public void onPopTabSet(int tag, String params, String value) {

    }


    /**
     * 模拟数据
     * @return
     */
    public List<SingleFilterBean> getData() {
        List<SingleFilterBean> singleFilterList = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            SingleFilterBean singleFilterBean = new SingleFilterBean();
            singleFilterBean.setId(i + "一级id");
            singleFilterBean.setKey(i + "一级key");
            singleFilterBean.setTitle(i + "一级title");

            List<SingleFilterBean.SecondFilterBean> childFilterList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SingleFilterBean.SecondFilterBean secondFilterBean = new SingleFilterBean.SecondFilterBean();
                secondFilterBean.setId(i + "-" + j + "二级id");
                secondFilterBean.setKey(i + "-" + j + "二级key");
                secondFilterBean.setTitle(i + "-" + j + "二级title");
                childFilterList.add(secondFilterBean);
            }
            singleFilterBean.setChildFilterList(childFilterList);
            singleFilterList.add(singleFilterBean);


        }


        return singleFilterList;

    }


    public List getSortData(){
        Gson gson =new Gson();

        FilterBean filterBean =gson.fromJson(FilterConfig.sortTestJson,FilterBean.class);

        return  filterBean.getData().getMall().getGuonei();

    }

}

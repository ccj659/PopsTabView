package com.ccj.tabview.mypoptabview.myloader;

import com.ccj.poptabview.PopsTabUtils;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.loader.ResultLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchangjun on 17/7/26.
 */

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

    @Override
    public MyFilterParamsBean getSecondResultParamsIds(List<BaseFilterTabBean> selectedList, int filterType) {
        MyFilterParamsBean stringIds = new MyFilterParamsBean();
        ArrayList<MyFilterParamsBean.ParamBean> paramBeenList = new ArrayList<>();
        for (int i = 0; i < selectedList.size(); i++) {
            MyFilterParamsBean.ParamBean paramBean = new MyFilterParamsBean.ParamBean();
            MyFilterTabBean.MyChildFilterBean filterTabBean = (MyFilterTabBean.MyChildFilterBean) selectedList.get(i);//此处需要强转
            paramBean.setCategory_ids(filterTabBean.getCategory_ids());
            paramBean.setMall_ids(filterTabBean.getMall_ids());
            paramBean.setTag_ids(filterTabBean.getTag_ids());
            paramBeenList.add(paramBean);
        }
        stringIds.setBeanList(paramBeenList);

        return stringIds;
    }

    @Override
    public String getSecondResultShowValues(List<BaseFilterTabBean> selectedList, int filterType) {
        StringBuilder stringValues = new StringBuilder();
        for (int i = 0; i < selectedList.size(); i++) {
            stringValues.append(selectedList.get(i).getTab_name() + ",");
        }
        return PopsTabUtils.builderToString(stringValues);
    }



}

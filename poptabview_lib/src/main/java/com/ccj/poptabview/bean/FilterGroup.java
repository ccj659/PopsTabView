package com.ccj.poptabview.bean;

import com.ccj.poptabview.base.BaseFilterTabBean;

import java.util.List;

/**
 * Created by chenchangjun on 17/7/3.
 * 筛选的种类,以及其中的 筛选项目
 */

public class FilterGroup {


    /**
     * tab_group_name : 筛选
     * tab_group_type : 0
     * single_or_mutiply: 1
     * filter_tab : [{"tab_name":"频道","tabs":[{"tag_name":"好价","tab_id":"22"},{"tag_name":"好文","tab_id":"23"}]},{"tab_name":"出行时间","tabs":[{"tag_name":"全部","tab_id":"27"},{"tag_name":"春节","tab_id":"28"},{"tag_name":"小长假","tab_id":"29"},{"tag_name":"国庆","tab_id":"30"},{"tag_name":"暑假","tab_id":"31"},{"tag_name":"test1","tab_id":"32"},{"tag_name":"test2","tab_id":"33"}]},{"tab_name":"出游天数","tabs":[{"tag_name":"短期（1-5天）","tab_id":"18"},{"tag_name":"中途（5-9天）","tab_id":"19"},{"tag_name":"长途（9天及以上）","tab_id":"20"},{"tag_name":"其他","tab_id":"21"},{"tag_name":"天数1","tab_id":"34"},{"tag_name":"天数2","tab_id":"35"},{"tag_name":"天数3","tab_id":"36"}]},{"tab_name":"商城","tabs":[{"tag_name":"京东","tab_id":"12"}]}]
     */

    private String tab_group_name;
    private int tab_group_type;//filter type
    private int single_or_mutiply; //筛选为单选还是多选

    private List<BaseFilterTabBean> filter_tab;


    public int getSingle_or_mutiply() {
        return single_or_mutiply;
    }

    public void setSingle_or_mutiply(int single_or_mutiply) {
        this.single_or_mutiply = single_or_mutiply;
    }

    public String getTab_group_name() {
        return tab_group_name;
    }

    public void setTab_group_name(String tab_group_name) {
        this.tab_group_name = tab_group_name;
    }

    public int getTab_group_type() {
        return tab_group_type;
    }

    public void setTab_group_type(int tab_group_type) {
        this.tab_group_type = tab_group_type;
    }

    public List<BaseFilterTabBean> getFilter_tab() {
        return filter_tab;
    }

    public void setFilter_tab(List<BaseFilterTabBean> filter_tab) {
        this.filter_tab = filter_tab;
    }

}

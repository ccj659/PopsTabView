package com.ccj.poptabview.filter.single;

import java.io.Serializable;
import java.util.List;

/**
 * 通用 筛选 实体类
 * Created by chenchangjun on 17/6/20.
 */

public class FilterTabBean implements Serializable {

    /**
     * tab_name : 热门
     * tabs : [{"tag_name":"上海","tab_id":"p5"},{"tag_name":"广州","tab_id":"p8"}]
     */

    private String tab_name;

    private String tab_id;

    private List<TabsBean> tabs;

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }

    public static class TabsBean {
        /**
         * tag_name : 上海
         * tab_id : p5
         */

        private String tag_name;
        private String tab_id;

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getTab_id() {
            return tab_id;
        }

        public void setTab_id(String tab_id) {
            this.tab_id = tab_id;
        }
    }
}

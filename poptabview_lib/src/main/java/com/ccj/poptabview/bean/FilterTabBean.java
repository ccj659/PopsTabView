package com.ccj.poptabview.bean;

import com.ccj.poptabview.base.BaseFilterTabBean;

import java.io.Serializable;
import java.util.List;

/**
 * 通用 筛选 实体类
 * 除了 tab_name,和tag_name 不能更改,其他都可以更改,进行扩展, 如果需要展示,或者进行拼接字段,只需要在ResultLoaderImp中进行修改即可
 * <p>
 * Created by chenchangjun on 17/6/20.
 */

public class FilterTabBean extends BaseFilterTabBean implements Serializable {

    /**
     * tab_name : 热门
     * tabs : [{"tag_name":"上海","tab_id":"p5"},{"tag_name":"广州","tab_id":"p8"}]
     */
    protected String tab_name;

    //字段可以自定义
    protected String tab_id;
    //二级bean
    protected List<BaseFilterTabBean> tabs;


    public static class ChildTabBean extends BaseFilterTabBean {
        /**
         * tag_name : 上海
         * tab_id : p5
         */
        protected String tag_name;

        //字段可以自定义
        protected String tab_id;


        public String getTab_id() {
            return tab_id;
        }

        public void setTab_id(String tab_id) {
            this.tab_id = tab_id;
        }

        @Override
        public String getTab_name() {
            return tag_name;
        }

        @Override
        public void setTab_name(String tab_name) {
            this.tag_name = tab_name;
        }


        @Override
        public List<BaseFilterTabBean> getTabs() {
            return null;
        }

        @Override
        public void setTabs(List<BaseFilterTabBean> tabs) {

        }
    }

    /**
     * 自定义需要重写
     ***/
    @Override
    public String getTab_name() {
        return tab_name;
    }

    /**
     * 自定义需要重写
     ***/
    @Override
    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }


    public List<BaseFilterTabBean> getTabs() {
        return tabs;
    }


    public void setTabs(List<BaseFilterTabBean> tabs) {
        this.tabs = tabs;
    }


}

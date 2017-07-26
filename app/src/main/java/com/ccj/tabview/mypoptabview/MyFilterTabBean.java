package com.ccj.tabview.mypoptabview;

import com.ccj.poptabview.base.BaseFilterTabBean;

import java.util.List;

/**
 * Created by chenchangjun on 17/7/26.
 */

public class MyFilterTabBean extends BaseFilterTabBean {


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
    public List<BaseFilterTabBean> getTabs() {
        return tabs;
    }

    @Override
    public void setTabs(List<BaseFilterTabBean> tabs) {
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
        public List<BaseFilterTabBean> getTabs() {
            return null;
        }

        @Override
        public void setTabs(List<BaseFilterTabBean> tabs) {

        }


        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getCategory_ids() {
            return category_ids;
        }

        public void setCategory_ids(String category_ids) {
            this.category_ids = category_ids;
        }

        public String getTag_ids() {
            return tag_ids;
        }

        public void setTag_ids(String tag_ids) {
            this.tag_ids = tag_ids;
        }

        public String getMall_ids() {
            return mall_ids;
        }

        public void setMall_ids(String mall_ids) {
            this.mall_ids = mall_ids;
        }


    }


    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getCategory_ids() {
        return category_ids;
    }

    public void setCategory_ids(String category_ids) {
        this.category_ids = category_ids;
    }

    public String getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(String tag_ids) {
        this.tag_ids = tag_ids;
    }

    public String getMall_ids() {
        return mall_ids;
    }

    public void setMall_ids(String mall_ids) {
        this.mall_ids = mall_ids;
    }
}

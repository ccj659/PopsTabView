package com.ccj.tabview.mypoptabview;

import java.io.Serializable;
import java.util.List;

/**
 * 筛选结果bean
 * Created by chenchangjun on 17/7/25.
 */

public class MyFilterParamsBean implements Serializable {


    protected List<ParamBean> beanList;

    protected String paramValues;


    public static class ParamBean{

        protected String category_ids;
        protected String tag_ids;
        protected String mall_ids;


        @Override
        public String toString() {
            return "\n"+"{" +
                    "category_ids='" + category_ids + '\'' +
                    ",tag_ids='" + tag_ids + '\'' +
                    ",mall_ids='" + mall_ids + '\'' +
                    '}'+"\n";
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
    public List<ParamBean> getBeanList() {
        return beanList;
    }

    @Override
    public String toString() {
        return "MyFilterParamsBean{" +
                "beanList=" + beanList +
                ", paramValues='" + paramValues + '\'' +
                '}';
    }

    public void setBeanList(List<ParamBean> beanList) {
        this.beanList = beanList;
    }

    public String getParamValues() {
        return paramValues;
    }

    public void setParamValues(String paramValues) {
        this.paramValues = paramValues;
    }
}

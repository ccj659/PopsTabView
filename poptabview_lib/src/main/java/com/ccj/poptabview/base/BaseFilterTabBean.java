package com.ccj.poptabview.base;

import java.util.List;

/**
 * 筛选bean的基类, 约束子类行为
 * Created by chenchangjun on 17/7/26.
 */

public abstract class BaseFilterTabBean {


    /**
     * 必须实现,涉及到adapter显示的值.要return list显示的字段.
     * @return
     */
    public abstract String getTab_name();
    public abstract void setTab_name(String tab_name);

    /**
     * 如果有自己的子菜单,目前只有一级菜单,有二级菜单~
     *
     * @return
     */
    public abstract List<BaseFilterTabBean> getTabs();

    public abstract  void setTabs(List<BaseFilterTabBean> tabs);

}

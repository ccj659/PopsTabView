package com.ccj.poptabview.bean;

import java.util.List;

/**
 * 通用 筛选 实体类
 * Created by chenchangjun on 17/6/20.
 */

public class SingleFilterBean {

    public String id; //作为 选中的 标识,一般为 下标号

    public String key;

    public String title;


    public boolean isCheck;//是否被选中

    public List<SecondFilterBean> childFilterList;

    @Override
    public String toString() {
        return "SingleFilterBean{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", isCheck=" + isCheck +
                ", childFilterList=" + childFilterList +
                '}';
    }

    public static class SecondFilterBean extends SingleFilterBean{

    }




    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SecondFilterBean> getChildFilterList() {
        return childFilterList;
    }

    public void setChildFilterList(List<SecondFilterBean> childFilterList) {
        this.childFilterList = childFilterList;
    }
}

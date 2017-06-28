package com.ccj.poptabview;

/**
 * Created by chenchangjun on 17/6/27.
 */

import java.io.Serializable;
import java.util.List;

public class FilterBean  {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private ChannelList channel;
        private List<CategoryMall> category;
        private Mall mall;

        public ChannelList getChannel() {
            return channel;
        }

        public List<CategoryMall> getCategory() {
            return category;
        }

        public Mall getMall() {
            return mall;
        }
    }

    public class ChannelList implements Serializable {
        private int home;
        private int youhui;
        private int faxian;
        private int haitao;
        private int news;
        private int yuanchuang;
        private int zhongce;
        private int haowu;
        private int wiki;
        private int second_hand;
        private int faxian_jingxuan;
        private int shai;
        private int coupon;
        private int user;

        public int getHome() {
            return home;
        }

        public int getYouhui() {
            return youhui;
        }

        public int getFaxian() {
            return faxian;
        }

        public int getHaitao() {
            return haitao;
        }

        public int getNews() {
            return news;
        }

        public int getYuanchuang() {
            return yuanchuang;
        }

        public int getZhongce() {
            return zhongce;
        }

        public int getHaowu() {
            return haowu;
        }

        public int getWiki() {
            return wiki;
        }

        public int getSecond_hand() {
            return second_hand;
        }

        public int getFaxian_jingxuan() {
            return faxian_jingxuan;
        }

        public int getShai() {
            return shai;
        }

        public void setShai(int shai) {
            this.shai = shai;
        }

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }

        public int getUser() {
            return user;
        }
    }

    public static class CategoryMall {
        private String id;
        private String name;
        private int count;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public class Mall {
        private List<CategoryMall> guonei;
        private List<CategoryMall> haiwai;
        private List<CategoryMall> kuajing;

        public List<CategoryMall> getGuonei() {
            return guonei;
        }

        public List<CategoryMall> getHaiwai() {
            return haiwai;
        }

        public List<CategoryMall> getKuajing() {
            return kuajing;
        }
    }

}

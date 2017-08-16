package com.ccj.poptabview;

/**
 * Update by chenchangjun on 17/4/6.
 */
public class FilterConfig {



    //通用筛选 单选,多选 判断
    public static final int FILTER_TYPE_SINGLE =1 ;
    public static final int FILTER_TYPE_MUTIFY = 2;
    //当点击了已经选中的项目时, 是否取消选中
    public static final boolean FILTER_TYPE_CAN_CANCEL = true;


    //二级筛选中二级list里面的GradLayout列数
    public static final int LINKED_SPAN_COUNT = 2;
    //rows里面的list里面的GradLayout列数
    public static final int ROWS_SPAN_COUNT = 3;




    //popwindow类型
    public  final  static int TYPE_POPWINDOW_SORT=0;//复杂筛选pop
    public  final  static int TYPE_POPWINDOW_SINGLE=1;//单选pop
    public  final  static int TYPE_POPWINDOW_LINKED=2;//二级pop
    public  final  static int TYPE_POPWINDOW_ROWS=3;//三列



}







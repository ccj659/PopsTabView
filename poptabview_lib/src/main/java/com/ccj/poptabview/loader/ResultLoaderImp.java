package com.ccj.poptabview.loader;

import com.ccj.poptabview.bean.FilterTabBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenchangjun on 17/7/6.
 */

public class ResultLoaderImp implements ResultLoader<String> {





    @Override
    public String getResultParamsIds(List<FilterTabBean> selectedList) {


        StringBuilder stringIds =new StringBuilder();

        for (int i = 0; i < selectedList.size(); i++) {
            stringIds.append(selectedList.get(i).getTab_id()+",");
        }

        return  builderToString(stringIds);
    }

    @Override
    public String getResultShowValues(List<FilterTabBean> selectedList) {

        StringBuilder stringValues =new StringBuilder();

        for (int i = 0; i < selectedList.size(); i++) {
            stringValues.append(selectedList.get(i).getTab_name()+",");
        }

        return builderToString(stringValues);
    }




    /**
     * 获取带逗号的string 例如 1,2,3,4,5,6
     * @param paramsMap
     * @return
     */
    public  String mapToString(HashMap<String, String> paramsMap ) {
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.append(entry.getValue()+",");

        }
        String paramString=params.toString();

        if (paramString.endsWith(",")){
            paramString = paramString.substring(0,paramString.length() - 1);
        }

        return paramString;
    }


    public  String builderToString(StringBuilder paramsMap ) {
        String paramString=paramsMap.toString();
        if (paramString.endsWith(",")){
            paramString = paramString.substring(0,paramString.length() - 1);
        }

        return paramString;
    }

}

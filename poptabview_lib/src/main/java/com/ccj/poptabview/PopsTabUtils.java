package com.ccj.poptabview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenchangjun on 17/7/26.
 */

public class PopsTabUtils {

    /**
     * 获取带逗号的string 例如 1,2,3,4,5,6
     *
     * @param paramsMap
     * @return
     */
    public static String mapToString(HashMap<String, String> paramsMap) {
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.append(entry.getValue() + ",");

        }
        String paramString = params.toString();

        if (paramString.endsWith(",")) {
            paramString = paramString.substring(0, paramString.length() - 1);
        }

        return paramString;
    }


    public static  String builderToString(StringBuilder paramsMap) {
        String paramString = paramsMap.toString();
        if (paramString.endsWith(",")) {
            paramString = paramString.substring(0, paramString.length() - 1);
        }

        return paramString;
    }


}

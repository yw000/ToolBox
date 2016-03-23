package com.yang.toolbox.util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by YangWei
 * on 2016/3/23.
 */
public class EmptyUtils {


    /**
     * 判断对象是否为NULL或空
     * @param object 对象
     * @return 是否为NULL或空
     */
    public static boolean isEmpty (Object object){
        boolean result = false;
        if (object == null){
            result = true;
        } else {
            if (object instanceof String){
                result = StringUtils.isBlank(String.valueOf(object));
            }else if (object instanceof Date) {
                result = ((Date) object).getTime() == 0;
            }else if (object instanceof Long){
                result = ((Long)object).longValue() == Long.MIN_VALUE;
            }else if (object instanceof Integer){
                result = ((Integer)object).intValue() == Integer.MIN_VALUE;
            }else if (object instanceof Collection){
                result = ((Collection<?>)object).size() == 0;
            }else if (object instanceof Map){
                result = ((Map<?, ?>)object).size() == 0;
            }else if (object instanceof JSONObject){
                result = !((JSONObject)object).keys().hasNext();
            }else{
                result = object.toString().equals("");
            }
        }
        return result;
    }




}

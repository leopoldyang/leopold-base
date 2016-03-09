package com.common.leopold.poi.util;


import com.common.leopold.poi.annotation.ExcelColumnName;
import com.common.leopold.poi.bean.ExcelColumnBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/12/21
 * Time:22:18
 */
public class PoiCommonUtils {
    /**
     * 获取实体类中属性的集合
     * @param clazz
     * @return
     */
    public static List<ExcelColumnBean> getColumnBeanList(Class clazz){
        if(clazz==null){
            return null;
        }
        List<ExcelColumnBean> list=new ArrayList<ExcelColumnBean>();
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            try {
                ExcelColumnName anno=field.getAnnotation(ExcelColumnName.class);
                if(anno!=null&&anno.value()!=null){
                    ExcelColumnBean bean=new ExcelColumnBean();
                    bean.setColumnName(anno.value());
                    bean.setColumnField(field);
                    bean.setGetterMethod(clazz.getMethod(getMethodName("get",field)));
                    bean.setSetterMethod(clazz.getMethod(getMethodName("set",field),field.getType()));
                    list.add(bean);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 获取类中属性的get和set方法名
     * @param prefix
     * @param field
     * @return
     */
    private static String getMethodName(String prefix,Field field){
        return prefix + field.getName().substring(0,1).toUpperCase()+
                field.getName().substring(1);
    }

    public static Object parseType(Class type,String value){
        if(type.equals(String.class)){
            return value;
        }else if(type.equals(Double.class)){
            return Double.parseDouble(value);
        }else if(type.equals(Integer.class)){
            return Integer.parseInt(value);
        }else if(type.equals(Date.class)){
            return new Date(value);
        }
        return null;
    }
}

package com.leopold.framework.poi.util;



import com.leopold.framework.TimeTools;
import com.leopold.framework.poi.annotation.ExcelColumnName;
import com.leopold.framework.poi.bean.ExcelColumnBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Excel导入与导出工具类
 *
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

    /**
     * 类型装换类，将String转换为对应的类型
     *
     * @param type
     * @param value
     * @return
     */
    public static Object parseType(Class type,String value){
        if(type.equals(String.class)){
            return value;
        }else if(type.isAssignableFrom(Double.class)){
            return Double.parseDouble(value);
        }else if(type.isAssignableFrom(Integer.class)){
            return Integer.parseInt(value);
        }else if(type.isAssignableFrom(Date.class)){
            return TimeTools.parseYYYY_MM_DD(value);
        }
        return null;
    }
}

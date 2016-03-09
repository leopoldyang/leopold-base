package com.common.leopold.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型工具类
 * @author: leopold
 * @version: 1.0
 * @CreateDate: 2015.3.19
 * @Description: 泛型工具类
 * @UpdateDate:
 *
 */
public class GenericsUtils {
	/**
	 * 获取泛型的类型
	 * @param clazz
	 * @return Class
	 */
	@SuppressWarnings("rawtypes")
	public static Class getGenericType(Class clazz){
		Type genType = clazz.getGenericSuperclass();//�õ����͸���  
		Type[] types = ((ParameterizedType) genType).getActualTypeArguments();
		if (!(types[0] instanceof Class)) {
            return Object.class;   
        } 
		return (Class) types[0];
	}
	/**
	 * 获取对象的类名称
	 * @param clazz
	 * @return 类名称
	 */
	@SuppressWarnings("rawtypes")
	public static String getGenericName(Class clazz){
		return clazz.getSimpleName();
	}
}

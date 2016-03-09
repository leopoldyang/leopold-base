package com.common.leopold.poi.annotation;

import java.lang.annotation.*;

/**
 * Excel操作注解
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/25
 * Time:0:35
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelColumnName {
    public String value() default "";
}

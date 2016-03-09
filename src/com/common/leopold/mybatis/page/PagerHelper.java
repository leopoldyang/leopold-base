package com.common.leopold.mybatis.page;

import com.common.leopold.mybatis.page.bean.Page;
import com.common.leopold.mybatis.exception.PageException;
import com.common.leopold.mybatis.page.inteceptor.PageInterceptor;
import com.common.leopold.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * mybatis分页工具类
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/26
 * Time:1:31
 */
public class PagerHelper {
    private final static Logger logger= LoggerFactory.getLogger(PagerHelper.class);

    /**
     * 执行分页并返回分页结果
     * @param pageNum 获取的页数
     * @param pageSize 每页数据条数
     * @param target 执行分页方法的代理对象
     * @param methodName 执行的方法名
     * @param param 执行方法传递的参数
     * @return 分页结果
     */
    public static Page doPage (int pageNum,int pageSize,Object target,
                                      String methodName,Object... param) throws Exception{
        if(target==null){
            throw new PageException(target+" is null");
        }
        if(StringTools.isEmpty(methodName)){
            throw new PageException(methodName+" is null");
        }
        Page page;
        PageInterceptor.startPage(pageNum, pageSize);
        Method method=target.getClass().getDeclaredMethod(methodName,param.getClass());
        method.invoke(target,param);
        page=PageInterceptor.endPage();
        return page;
    }
}

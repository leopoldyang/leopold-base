package com.common.leopold.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * mybatis对数据库操作类(dao)的基类
 * session的获取与关闭
 *
 * Created by IDEA
 * User:Leopold
 * Email:ylp_boy@126.com
 * Date:2015/11/26
 * Time:0:22
 */
public class BaseDao {
    private final static Logger logger= Logger.getLogger(BaseDao.class);
    @Autowired
    private SqlSessionFactory factory;
    private SqlSession session;
    public SqlSession getSession(){
        if (factory == null) {
            logger.error("sqlSessionFactory is null!");
            return null;
        }
        session=factory.openSession();
        return session;
    }
    public void closeSession(){
        if(session!=null){
            session.close();
        }else{
            logger.error("session is null!");
        }
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SqlSessionFactory factory) {
        this.factory = factory;
    }
}

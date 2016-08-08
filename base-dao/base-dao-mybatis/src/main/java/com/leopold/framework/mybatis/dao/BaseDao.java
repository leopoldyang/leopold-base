package com.leopold.framework.mybatis.dao;

import com.leopold.framework.mybatis.exception.DaoException;
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

    /**
     * 获取session
     * @return
     */
    public SqlSession getSession() throws DaoException {
        if (factory == null) {
            logger.error("sqlSessionFactory is null!");
            throw new DaoException("sqlSessionFactory is null");
        }
        session=factory.openSession();
        return session;
    }

    /**
     * 关闭session
     */
    public void closeSession() throws DaoException{
        if(session==null){
            logger.error("session is null!");
            throw new DaoException("session is null");
        }
        session.close();
    }

    public SqlSessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SqlSessionFactory factory) {
        this.factory = factory;
    }
}

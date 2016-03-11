package com.common.leopold.hibernate.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author:
 * @version: 1.0
 * @CreateDate: 2015.4.5
 * @Description: 数据库基本操作的封装类
 * @UpdateDate:
 *
 */
public interface BaseDao<T> {
	public void save(Object obj);//保存数据
	public void saveOrUpdate(Object obj);//保存或修改数据
	public void update(Object obj);//修改数据
	public void delete(Object obj);//删除数据
	public T get(Serializable entityId);//加载实体对象
	public Object uniqueResult(String hql, Object[] queryParams);//使用hql语句操作
	public List<T> findAll();//获取所有的实体信息的集合
	public List<T> findPage(int pageNow, int pageSize);//分页查询
	public List<T> findPage(String property, Serializable value, int pageNow, int pageSize);
	public int getCount(String property, Serializable value);
	public List<T> findBycondition(String hql, Object[] queryParams);
	public int getCount();//获取总信息数
	/**
	 * 根据实体的唯一属性查询实体
	 * @param property
	 * @param value
	 * @return
	 */
	public T findByUniqueProperty(String property, Serializable value);
	public List<T> findByUniquePropertyList(String property, Serializable value);
	/**
	 * 根据属性判断实体是否存在
	 * @param property
	 * @param value
	 * @return
	 */
	public boolean existEntity(String property, Serializable value);
}

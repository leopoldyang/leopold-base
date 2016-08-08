package com.leopold.framework.hibernate;

import com.leopold.framework.GenericsUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;


/**
 *
 * @author:
 * @version: 1.0
 * @CreateDate: 2015.4.5
 * @Description: BaseDao的实现类
 * @UpdateDate:
 *
 */
@SuppressWarnings("unchecked")
public class DaoSupport<T> implements BaseDao<T> {

	private static final Log log = LogFactory.getLog(DaoSupport.class);
	@Autowired
	private SessionFactory sessionFactory;
	// 泛型的类型
	protected Class<T> entityClass = GenericsUtils.getGenericType(this
			.getClass());

	protected Session getSession() {
		log.debug("log###############daoSupport---------get session");
		return sessionFactory.getCurrentSession();
	}

	public void save(Object obj) {
		log.debug("log###############daoSupport---------save object");
		log.debug("log###############daoSupport---------" + this.getSession());
		this.getSession().save(obj);
	}

	public void saveOrUpdate(Object obj) {
		log.debug("log###############daoSupport---------saveOrUpdate object");
		this.getSession().saveOrUpdate(obj);
	}

	public void update(Object obj) {
		log.debug("log###############daoSupport---------update object");
		this.getSession().update(obj);
	}

	public void delete(Object obj) {
		log.debug("log###############daoSupport---------delete object");
		this.getSession().delete(obj);
	}

	public T get(Serializable entityId) {
		log.debug("log###############daoSupport---------get entity");
		return (T) this.getSession().get(entityClass, entityId);
	}

	public Object uniqueResult(final String hql, final Object[] queryParams) {
		log.debug("log###############daoSupport---------uniqueResult");
		return this.setQueryParams(hql, queryParams).uniqueResult();
	}

	public int getCount() {
		log.debug("log###############daoSupport---------get count");
		String hql = "select count(*) from "
				+ GenericsUtils.getGenericName(entityClass);
		return Integer.parseInt(this.uniqueResult(hql, null).toString());
	}

	protected Query setQueryParams(final String hql, final Object[] queryParams) {
		log.debug("log###############daoSupport---------set queryParams");
		Query query = this.getSession().createQuery(hql);
		if (null != queryParams) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);
			}
		}
		return query;
	}

	public List<T> findAll() {
		log.debug("log###############daoSupport---------findAll");
		Query query = this.setQueryParams(
				"from " + GenericsUtils.getGenericName(entityClass), null);
		return query.list() == null ? null : query.list();
	}

	public List<T> findPage(int pageNow, int pageSize) {
		log.debug("log###############daoSupport---------findPage");
		Query query = this.setQueryParams(
				"from " + GenericsUtils.getGenericName(entityClass), null);
		query.setFirstResult((pageNow - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list() == null ? null : query.list();
	}

	public List<T> findBycondition(String hql, Object[] queryParams) {
		log.debug("log###############daoSupport---------findBycondition");
		Query query = this.setQueryParams(hql, queryParams);
		return query.list() == null ? null : query.list();
	}

	public T findByUniqueProperty(String property, Serializable value) {
		String hql = "from " + GenericsUtils.getGenericName(entityClass)
				+ " en where en." + property + "=?";
		Object[] queryParams = { value };
		return (T) this.uniqueResult(hql, queryParams);
	}

	public List<T> findByUniquePropertyList(String property, Serializable value) {
		String hql = "from " + GenericsUtils.getGenericName(entityClass)
				+ " en where en." + property + "=?";
		Object[] queryParams = { value };
		return this.findBycondition(hql, queryParams);
	}

	public boolean existEntity(String property, Serializable value) {
		return this.findByUniqueProperty(property, value) != null ? true
				: false;
	}

	public List<T> findPage(String property, Serializable value, int pageNow,
							int pageSize) {
		String hql = "from " + GenericsUtils.getGenericName(entityClass)
				+ " en where en." + property + "=?";
		Object[] queryParams = {value};
		Query query=this.setQueryParams(hql, queryParams);
		query.setFirstResult((pageNow - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public int getCount(String property, Serializable value) {
		String hql = "select count(*) from "
				+ GenericsUtils.getGenericName(entityClass)+ " en where en." + property + "=?";
		Object[] queryParams = {value};
		return Integer.parseInt(this.uniqueResult(hql, queryParams).toString());
	}
}
